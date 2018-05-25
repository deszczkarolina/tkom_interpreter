package tkom.parser;

import tkom.exceptions.ParserException;
import tkom.parser.conditions.AndCondition;
import tkom.parser.conditions.ComCondition;
import tkom.parser.conditions.Condition;
import tkom.parser.conditions.OrCondition;
import tkom.parser.expressions.AddExpression;
import tkom.parser.expressions.Expression;
import tkom.parser.expressions.MulExpression;
import tkom.parser.statements.*;
import tkom.parser.types.Bool;
import tkom.parser.types.Number;
import tkom.parser.types.Rectangle;
import tkom.parser.types.Text;
import tkom.scanner.Scanner;
import tkom.scanner.TokenType;

import java.lang.String;
import java.util.LinkedHashMap;
import java.util.Vector;

import static tkom.scanner.TokenType.*;
import static tkom.scanner.TokenType.String;

/**
 * Created by karolina on 15.04.18.
 */
public class Parser {

    private static Vector<TokenType> types;
    private static Vector<TokenType> relOp;
    private static Vector<TokenType> rectangleFields;

    static {
        types = new Vector<>();
        types.add(Int);
        types.add(String);
        types.add(Bool);
        types.add(Rectangle);

        relOp = new Vector<>();
        relOp.add(eqop);
        relOp.add(neqop);
        relOp.add(ltop);
        relOp.add(leop);
        relOp.add(geop);
        relOp.add(gtop);

        rectangleFields = new Vector<>();
        rectangleFields.add(RectArea);
        rectangleFields.add(RectPermimeter);
        rectangleFields.add(RectLength);
        rectangleFields.add(RectWidth);
        rectangleFields.add(RectX);
        rectangleFields.add(RectY);
    }

    private TokenType currentToken;
    private Scanner scn;

    public Parser(Scanner scanner) throws Exception {
        this.scn = scanner;
        nextToken();
    }

    private void nextToken() throws Exception {
        currentToken = scn.nextToken();
    }

    private void accept(Vector<TokenType> acceptableTokens) throws Exception {
        if (!acceptableTokens.contains(currentToken))
            throw new ParserException(currentToken, acceptableTokens, scn.getTokenPos());
    }

    private void accept(TokenType acceptableToken) throws Exception {
        if (acceptableToken != currentToken)
            throw new ParserException(currentToken, acceptableToken, scn.getTokenPos());
    }



    public Program parse() throws Exception {
        FunctionDefinition tmp;
        Vector<FunctionDefinition> functions = new Vector<>();
        while (currentToken != EOF && (tmp = parseFunction()) != null)
            functions.add(tmp);
        accept(EOF);
        return new Program(functions);
    }

    private FunctionDefinition parseFunction() throws Exception {
        accept(types);
        TokenType t = currentToken;
        nextToken();
        accept(ident);
        nextToken();
        String name = scn.getIdent();
        accept(lbracket);
        nextToken();

        LinkedHashMap<String, TokenType> parameters = new LinkedHashMap<>();
        TokenType type;
        if (types.contains(currentToken)) {
            type = currentToken;
            nextToken();
            accept(ident);
            parameters.put(scn.getIdent(), type);
            nextToken();
            while (currentToken == coma) {
                nextToken();
                accept(types);
                type = currentToken;
                nextToken();
                accept(ident);
                parameters.put(scn.getIdent(), type);
                nextToken();
            }
        }
        accept(rbracket);
        nextToken();
        BlockStatement blockStatement;
        if ((blockStatement = parseBlockStatement()) == null)
            return null;

        return new FunctionDefinition(t, name, parameters, blockStatement);
    }

    private BlockStatement parseBlockStatement() throws Exception {
        if (currentToken != lcurlybracket)
            return null;
        nextToken();
        Vector<Statement> statements = new Vector<>();
        Statement tmp;
        while ((tmp = parseIfStatement()) != null ||
                (tmp = parseWhileStatement()) != null ||
                (tmp = parseReturnStatement()) != null ||
                (tmp = parseBlockStatement()) != null ||
                (tmp = parseAssignStatement()) != null ||
                (tmp = parsePrintStatement()) != null ||
                (tmp = parseRectangleDeclaration()) != null ||
                (tmp = parseVarDeclarationStatement()) != null) {
            statements.add(tmp);
        }
        accept(rcurlybracket);
        nextToken();
        return new BlockStatement(statements);
    }

    private RectangleDeclarationStatement parseRectangleDeclaration() throws Exception {
        String ident;
        int x, y, width, length;
        if (currentToken != Rectangle)
            return null;
        nextToken();
        accept(TokenType.ident);
        ident = scn.getIdent();
        nextToken();
        accept(lbracket);
        nextToken();
        accept(intconst);
        x = scn.getConstint();
        nextToken();
        accept(coma);
        nextToken();
        accept(intconst);
        y = scn.getConstint();
        nextToken();
        accept(coma);
        nextToken();
        accept(intconst);
        width = scn.getConstint();
        nextToken();
        accept(coma);
        nextToken();
        accept(intconst);
        length = scn.getConstint();
        nextToken();
        accept(rbracket);
        nextToken();
        accept(semicolon);
        nextToken();
        return new RectangleDeclarationStatement(new Rectangle(x, y, width, length), ident);
    }

    private VarDeclarationStatement parseVarDeclarationStatement() throws Exception {
        String name;
        Value initialization;
        if (!types.contains(currentToken))
            return null;
        nextToken();
        accept(ident);
        name = scn.getIdent();
        nextToken();
        if (currentToken == semicolon) {
            nextToken();
            return new VarDeclarationStatement(name);
        }
        accept(assignop);
        nextToken();

        if ((initialization = parseAddExpression()) == null &&
                (initialization = parseText()) == null &&
                (initialization = parseLogicalValue()) == null) {
            return null;
        }
        accept(semicolon);
        nextToken();
        return new VarDeclarationStatement(name, initialization);
    }

    private AssignStatement parseAssignStatement() throws Exception {
        Value rhs;
        String ident;
        TokenType field = null;
        if (currentToken != TokenType.ident)
            return null;
        ident = scn.getIdent();
        nextToken();

        if (currentToken == dot) {
            nextToken();
            accept(rectangleFields);
            field = currentToken;
            nextToken();
        }

        accept(assignop);
        nextToken();
        if ((rhs = parseAddExpression()) == null)
            if ((rhs = parseLogicalValue()) == null)
                return null;
        accept(semicolon);
        nextToken();
        if (field != null)
            return new AssignStatement(ident, rhs, field);
        return new AssignStatement(ident, rhs);
    }

    private PrintStatement parsePrintStatement() throws Exception {
        Value expr;
        if (currentToken != Print)
            return null;
        nextToken();
        accept(lbracket);
        nextToken();
        if (currentToken == textconst) {
            expr = new Text(scn.getIdent());
            nextToken();
        } else if ((expr = parseAddExpression()) == null)
            return null;

        accept(rbracket);
        nextToken();
        accept(semicolon);
        nextToken();
        return new PrintStatement(expr);
    }

    private FunctionCallStatement parseFunctionCallStatement(String name) throws Exception {
        Vector<AddExpression> arguments = new Vector<>();
        AddExpression tmp;
        if (currentToken != lbracket)
            return null;
        nextToken();

        if ((tmp = parseAddExpression()) != null) {
            arguments.add(tmp);

            while (currentToken != rbracket && currentToken == coma) {
                nextToken();
                if ((tmp = parseAddExpression()) == null)
                    return null;
                arguments.add(tmp);
            }
        }
        accept(rbracket);
        nextToken();
        accept(semicolon);
        return new FunctionCallStatement(name, arguments);
    }

    private ReturnStatement parseReturnStatement() throws Exception {
        if (currentToken != Return)
            return null;
        nextToken();
        Value expression;

        if ((expression = parseAddExpression()) == null)
           if ((expression = parseText()) == null)
               if ((expression = parseLogicalValue()) == null)
                       return null;
        accept(semicolon);
        nextToken();
        return new ReturnStatement(expression);
    }

    private WhileStatement parseWhileStatement() throws Exception {
        OrCondition condition;
        BlockStatement block;
        if (currentToken != While)
            return null;
        nextToken();
        accept(lbracket);
        nextToken();
        if ((condition = parseOrCondition()) == null)
            return null;
        accept(rbracket);
        nextToken();
        if ((block = parseBlockStatement()) == null)
            return null;

        return new WhileStatement(condition, block);
    }

    private IfStatement parseIfStatement() throws Exception {
        OrCondition condition;
        BlockStatement trueBlock;
        BlockStatement falseBlock;
        if (currentToken != If)
            return null;
        nextToken();
        accept(lbracket);
        nextToken();
        if ((condition = parseOrCondition()) == null)
            return null;
        accept(rbracket);
        nextToken();
        accept(Then);
        nextToken();
        if ((trueBlock = parseBlockStatement()) == null)
            return null;
        if (currentToken == Else) {
            nextToken();
            if ((falseBlock = parseBlockStatement()) == null)
                return null;
        } else
            falseBlock = null;

        return new IfStatement(condition, trueBlock, falseBlock);
    }

    private Text parseText() throws Exception {
        if (currentToken != textconst)
            return null;
        nextToken();
        return new Text(scn.getIdent());
    }

    private Number parseNumber() throws Exception {
        if (currentToken != intconst)
            return null;
        nextToken();
        return new Number(scn.getConstint());
    }

    private Bool parseLogicalValue() throws Exception {
        boolean value;
        if (currentToken != True && currentToken != False)
            return null;
        if (currentToken == True)
            value = true;
        else
            value = false;
        nextToken();
        return new Bool(value);
    }

    private Expression parseExpression() throws Exception {
        Value operand;
        Variable tmp;

        boolean negate = false;
        if (currentToken == subop) {
            negate = true;
            nextToken();
        }
        if (currentToken == lbracket) {
            nextToken();
            if ((operand = parseAddExpression()) == null)
                return null;
            accept(rbracket);
            nextToken();
        } else if (currentToken == ident) {
            String ident = scn.getIdent();
            nextToken();
            if (currentToken == dot) {
                nextToken();
                accept(rectangleFields);
                operand = new Variable(ident, currentToken);
                nextToken();
                return new Expression(operand, negate);
            }
            if ((operand = parseFunctionCallStatement(ident)) == null) {
                tmp = new Variable(scn.getIdent());
                operand = tmp;
            }
            return new Expression(operand, negate);
        } else if ((operand = parseNumber()) == null)
            return null;
        return new Expression(operand, negate);
    }

    private AddExpression parseAddExpression() throws Exception {

        Vector<MulExpression> operands = new Vector<>();
        Vector<TokenType> operators = new Vector<>();
        MulExpression tmp;

        if ((tmp = parseMulExpression()) == null)
            return null;

        operands.add(tmp);
        while (currentToken == addop || currentToken == subop) {
            operators.add(currentToken);
            nextToken();
            if ((tmp = parseMulExpression()) == null)
                return null;
            operands.add(tmp);
        }

        return new AddExpression(operands, operators);
    }

    private MulExpression parseMulExpression() throws Exception {

        Vector<TokenType> operators = new Vector<>();
        Vector<Expression> operands = new Vector();
        Expression tmp;
        if ((tmp = parseExpression()) == null)
            return null;
        operands.add(tmp);
        while (currentToken == mulop || currentToken == divop) {
            operators.add(currentToken);
            nextToken();
            if ((tmp = parseExpression()) == null)
                return null;
            operands.add(tmp);
        }
        return new MulExpression(operands, operators);
    }

    private Condition parseCondition() throws Exception {
        AddExpression lexpr;
        AddExpression rexpr;
        TokenType op;

        if ((lexpr = parseAddExpression()) == null)
            return null;
        accept(relOp);
        op = currentToken;
        nextToken();
        if ((rexpr = parseAddExpression()) == null)
            return null;
        return new Condition(lexpr, rexpr, op);
    }

    private ComCondition parseComCondition() throws Exception {
        boolean negate = false;
        Logical operand;
        if (currentToken == notop) {
            negate = true;
            nextToken();
        }
        if (currentToken == lbracket) {
            nextToken();
            if ((operand = parseOrCondition()) == null)
                return null;
            accept(rbracket);
        } else if ((operand = parseCondition()) == null)
            return null;
        return new ComCondition(operand, negate);
    }

    private AndCondition parseAndCondition() throws Exception {
        Vector<ComCondition> operands = new Vector<>();
        ComCondition tmp;
        if ((tmp = parseComCondition()) == null)
            return null;
        operands.add(tmp);
        while (currentToken == andop) {
            nextToken();
            if ((tmp = parseComCondition()) == null)
                return null;
            operands.add(tmp);
        }
        return new AndCondition(operands);
    }

    private OrCondition parseOrCondition() throws Exception {
        Vector<AndCondition> operands = new Vector<>();
        AndCondition tmp;
        if ((tmp = parseAndCondition()) == null)
            return null;
        operands.add(tmp);
        while (currentToken == orop) {
            nextToken();
            if ((tmp = parseAndCondition()) == null)
                return null;
            operands.add(tmp);
        }
        return new OrCondition(operands);
    }
}
