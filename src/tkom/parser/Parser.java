package tkom.parser;

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

import java.util.*;
import java.lang.*;
import static tkom.scanner.TokenType.*;

/**
 * Created by karolina on 15.04.18.
 */
public class Parser {

    private static Vector<TokenType> types;
    private static Vector<TokenType> relOp;
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

    private boolean accept(List<TokenType> acceptableTokens) throws Exception {
        if (acceptableTokens.contains(currentToken))
              return true;
        else {
            SyntaxErrorExpectedToken(acceptableTokens);
            return false;
        }
    }

    private boolean accept(TokenType acceptableToken) throws Exception {
        if (acceptableToken == currentToken)
            return true;
      SyntaxErrorExpectedToken(acceptableToken);
      return acceptableToken == currentToken;
    }

    private void SyntaxErrorExpectedToken(List<TokenType> expectedTokens) {

        System.out.print("error: expected token: ");
        for (TokenType t : expectedTokens)
            System.out.print(t.toString());
        System.out.print("recieved token: " + currentToken);
         System.out.print(" at line " + scn .getTokenPos().toString());
    }
    private void SyntaxErrorExpectedToken(TokenType expectedToken) {

        System.out.print("error: expected token: " + expectedToken.toString());
        System.out.print("recieved token: " + currentToken);
        System.out.print(" at line " + scn .getTokenPos().toString());
    }


    public Program parse() throws Exception {
        FunctionDefinition tmp;
        Vector<FunctionDefinition> functions = new Vector<>();
        while((tmp = parseFunction()) != null)
            functions.add(tmp);

        accept(EOF);
        return new Program(functions);
    }

    private FunctionDefinition parseFunction() throws Exception {
        if (!accept(types))
            return null;
        nextToken();
        if (!accept(ident))
            return null;
        nextToken();
        String name = scn.getIdent();
        if (!accept(lbracket))
            return null;
        nextToken();

        LinkedHashMap<String, TokenType> parameters = new LinkedHashMap<>();
        TokenType type;
        if (accept(types)) {
            type = currentToken;
            nextToken();
            if (!accept(ident))
                return null;
            parameters.put(scn.getIdent(),type);
            nextToken();
            while(accept(coma)){
                nextToken();
                if (!accept(types))
                    return null;
                type = currentToken;
                nextToken();
                if (!accept(ident))
                    return null;
                parameters.put(scn.getIdent(),type);
                nextToken();
            }
        }
        if (!accept(rbracket))
            return null;
        nextToken();
        VarDeclarationStatement.BlockStatement blockStatement;
        if ((blockStatement = parseBlockStatement()) == null)
            return null;

        return new FunctionDefinition(name, parameters, blockStatement);
    }

    private VarDeclarationStatement.BlockStatement parseBlockStatement() throws Exception {
        if(!accept(lcurlybracket))
            return null;
        nextToken();
        Vector<Statement> statements = new Vector<>();
        Statement tmp ;
         while ((tmp =  parseIfStatement()) != null ||
                (tmp = parseWhileStatement()) != null ||
                (tmp = parseReturnStatement()) != null ||
                (tmp = parseBlockStatement()) != null ||
                (tmp = parseAssignStatement()) != null ||
                (tmp = parsePrintStatement()) != null ||
                (tmp = parseRectangleDeclaration()) != null ||
                (tmp = parseVarDeclarationStatement()) != null){
                     statements.add(tmp);
        }
        if (!accept(rcurlybracket))
            return null;
        nextToken();
        return new VarDeclarationStatement.BlockStatement(statements);
    }

    private RectangleDeclarationStatement parseRectangleDeclaration() throws Exception {
        String ident;
        int x,y,width,length;
        if (!accept(Rectangle))
            return null;
        nextToken();
        if (!accept(TokenType.ident))
            return null;
        ident = scn.getIdent();
        nextToken();
        if(!accept(lbracket))
            return null;
        nextToken();
        if(!accept(intconst))
            return null;
        x = scn.getConstint();
        nextToken();
        if(!accept(coma))
            return null;
        nextToken();
        if(!accept(intconst))
            return null;
        y = scn.getConstint();
        nextToken();
        if(!accept(coma))
            return null;
        nextToken();
        if(!accept(intconst))
            return null;
        width = scn.getConstint();
        nextToken();
        if(!accept(coma))
            return null;
        nextToken();
        if(!accept(intconst))
            return null;
        length = scn.getConstint();
        nextToken();
        if(!accept(rbracket))
            return null;
        nextToken();
        if(!accept(semicolon))
            return null;
        nextToken();
        return new RectangleDeclarationStatement(new Rectangle(x,y,width,length),ident);
    }
    private VarDeclarationStatement parseVarDeclarationStatement() throws Exception {
        String type, name;
        Value initialization;
        if(!accept(types))
            return null;
        type = scn.getIdent();
        nextToken();
        if (!accept(ident))
            return null;
        name = scn.getIdent();
        nextToken();
        if(accept(semicolon)) {
            nextToken();
            return new VarDeclarationStatement(type, name);
        }
        if (!accept(assignop))
            return null;
        nextToken();

        if((initialization = parseAddExpression()) == null &&
            (initialization = parseText()) == null &&
            (initialization = parseLogicalValue()) == null) {
            return null;
        }
        if (!accept(semicolon))
            return null;
        nextToken();
        return new VarDeclarationStatement(type,name,initialization);
    }
    private ReturnStatement.AssignStatement parseAssignStatement() throws Exception {
        Value rhs;
        String ident;
        if(!accept(TokenType.ident))
            return null;
        ident = scn.getIdent();
        nextToken();
        if(!accept(assignop))
            return null;
        nextToken();
        if((rhs = parseAddExpression()) == null )
            if((rhs = parseLogicalValue()) == null)
                return null;
        if(!accept(semicolon))
            return null;
        nextToken();
        return new ReturnStatement.AssignStatement(ident,rhs);
    }
    private PrintStatement parsePrintStatement() throws Exception {
        Value expr;
        if(!accept(Print))
            return null;
        nextToken();
        if(!accept(lbracket))
            return null;
        nextToken();
        if(currentToken == textconst){
            expr = new Text(scn.getIdent());
            nextToken();
        }
        else if ((expr = parseAddExpression()) == null)
            return null;


        if(!accept(rbracket))
            return null;
        nextToken();
        if(!accept(semicolon))
            return null;
        nextToken();
        return new PrintStatement(expr);
    }
    private FunctionCallStatement parseFunctionCallStatement(String name) throws Exception {
        Vector<AddExpression> arguments = new Vector<>();
        AddExpression tmp;
        if(!accept(lbracket))
            return null;
        nextToken();

        if ((tmp = parseAddExpression()) != null) {
            arguments.add(tmp);

            while (currentToken != rbracket && accept(coma)) {
                nextToken();
                if ((tmp = parseAddExpression()) == null)
                    return null;
                arguments.add(tmp);
            }
        }
        if(!accept(rbracket))
            return null;
        nextToken();
        if(!accept(semicolon))
            return null;

        return new FunctionCallStatement(name, arguments);
    }
    private ReturnStatement parseReturnStatement() throws Exception {
        if(!accept(Return))
            return null;
        nextToken();
        Value expression;

        if((expression = parseAddExpression()) == null)
            if((expression = parseLogicalValue()) == null)
                return null;
        if(!accept(semicolon))
            return null;
        nextToken();
        return new ReturnStatement(expression);
    }
    private WhileStatement parseWhileStatement() throws Exception {
        OrCondition condition;
        VarDeclarationStatement.BlockStatement block;
        if(!accept(While))
            return null;
        nextToken();
        if(!accept(lbracket))
            return null;
        nextToken();
        if((condition = parseOrCondition()) == null)
            return null;
        if(!accept(rbracket))
            return null;
        nextToken();
        if((block = parseBlockStatement()) == null)
            return null;

        return  new WhileStatement(condition, block);
    }
    private IfStatement parseIfStatement() throws Exception {
        OrCondition condition;
        VarDeclarationStatement.BlockStatement trueBlock;
        VarDeclarationStatement.BlockStatement falseBlock;

        if(currentToken != If)
            return null;
        nextToken();
        if (!accept(lbracket))
            return null;
        nextToken();
        if ((condition = parseOrCondition()) == null)
            return null;
        if(!accept(rbracket))
            return null;
        nextToken();
        accept(Then);
        nextToken();
        if((trueBlock = parseBlockStatement()) == null)
            return null;
        if(currentToken == Else){
            nextToken();
            if((falseBlock = parseBlockStatement()) == null)
                return null;
        }
        else
            falseBlock = null;

        return new IfStatement(condition,trueBlock,falseBlock);

    }

    private Text parseText() throws Exception {
        if(!accept(textconst))
            return null;
        nextToken();
        return new Text(scn.getIdent());
    }
    private Number parseNumber() throws Exception {
        if(!accept(intconst))
            return null;
        nextToken();
        return new Number(scn.getConstint());
    }
    private Bool parseLogicalValue() throws Exception {
        boolean value;
        if(currentToken != True && currentToken != False)
            return null;
        value = currentToken == True;
        nextToken();
        return new Bool(value);
    }

    private Expression parseExpression() throws Exception {
        Value operand;
        Variable tmp;

        boolean negate = false;
        if(currentToken == subop) {
            negate = true;
            nextToken();
        }
        if(currentToken  == lbracket){
            nextToken();
            if ((operand = parseAddExpression()) == null)
                return null;
            if(!accept(rbracket))
                return null;
            nextToken();
        }
        else if(accept(ident)) {
            String ident = scn.getIdent();
            if ((operand = parseFunctionCallStatement(ident)) == null) {
                tmp = new Variable(scn.getIdent());
                operand = tmp;
            }
            nextToken();
            return new Expression(operand, negate);
        }
        else if((operand = parseNumber()) == null)
            return null;
        return new Expression(operand,negate);
    }
    private AddExpression parseAddExpression() throws Exception {

        Vector<MulExpression> operands = new Vector<>();
        Vector<TokenType> operators = new Vector<>();
        MulExpression tmp;

        if((tmp = parseMulExpression()) == null)
            return null;

        operands.add(tmp);
        while(accept(addop) || accept(subop)){
            operators.add(currentToken);
            nextToken();
            if((tmp = parseMulExpression()) == null)
                return null;
            operands.add(tmp);
          //  nextToken();
        }

        return new AddExpression(operands,operators);
    }
    private MulExpression parseMulExpression() throws Exception {

        Vector<TokenType> operators = new Vector<>();
        Vector<Expression> operands = new Vector();
        Expression tmp;
        if((tmp = parseExpression()) == null)
            return null;
        operands.add(tmp);
        while(accept(mulop) || accept(divop)){
            operators.add(currentToken);
            nextToken();
            if((tmp = parseExpression()) == null)
                return null;
            operands.add(tmp);

        }

        return new MulExpression(operands,operators);
    }

    private Condition parseCondition() throws Exception {
        AddExpression lexpr;
        AddExpression rexpr;
        TokenType op;

        if ((lexpr = parseAddExpression()) == null)
            return null;
        if(!accept(relOp))
            return null;
        op = currentToken;
        nextToken();
        if ((rexpr = parseAddExpression()) == null)
            return null;
         return new Condition(lexpr,rexpr,op);
    }
    private ComCondition parseComCondition() throws Exception {
        boolean negate = false;
        Logical operand;
        if(currentToken == notop){
            negate = true;
            nextToken();
        }
        if(currentToken == lbracket){
            nextToken();
            if ((operand = parseOrCondition()) == null)
                return null;
            if (accept(rbracket))
                return null;
        }
        else if ((operand = parseCondition()) == null)
            return null;
        return new ComCondition(operand,negate);
    }
    private AndCondition parseAndCondition() throws Exception {
        Vector<ComCondition> operands = new Vector<>();
        ComCondition tmp;
        if ((tmp = parseComCondition()) == null)
            return null;
        operands.add(tmp);
        while (accept(andop)){
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
        while (accept(orop)){
            nextToken();
            if ((tmp = parseAndCondition()) == null)
                return null;
            operands.add(tmp);
        }
        return new OrCondition(operands);
    }
}
