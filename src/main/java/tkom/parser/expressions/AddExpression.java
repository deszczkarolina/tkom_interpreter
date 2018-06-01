package tkom.parser.expressions;

import tkom.parser.Scope;
import tkom.parser.Value;
import tkom.parser.statements.FunctionDefinition;
import tkom.scanner.TokenType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

/**
 * Created by karolina on 27.04.18.
 */
public class AddExpression extends Value {

    private Vector<MulExpression> operands;
    private Vector<TokenType> operators;
    private Object value;

    public AddExpression(Vector<MulExpression> operands, Vector<TokenType> operators) {
        this.operands = operands;
        this.operators = operators;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        Iterator<MulExpression> operandsIt = operands.iterator();
        Iterator<TokenType> operatorsIt = operators.iterator();
        MulExpression tmp;
        TokenType op;
        tmp = operandsIt.next();
        tmp.execute(scope, functions);
        value = tmp.getValue();
        while (operandsIt.hasNext()) {
            tmp = operandsIt.next();
            tmp.execute(scope, functions);
            op = operatorsIt.next();
            if (op == TokenType.addop)
                value = add(value, tmp.getValue());
            else
                value = subtract(value, tmp.getValue());
        }
    }
}
