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
public class MulExpression extends Value {

    private Vector<Expression> operands;
    private Vector<TokenType> operators;
    private Object value;

    public MulExpression(Vector<Expression> operands, Vector<TokenType> operators) {
        this.operands = operands;
        this.operators = operators;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        Iterator<Expression> operandsIt = operands.iterator();
        Iterator<TokenType> operatorsIt = operators.iterator();
        Expression tmp;
        TokenType op;
        tmp = operandsIt.next();
        if (!tmp.execute(scope, functions))
            return false;
        value = tmp.getValue();
        while (operandsIt.hasNext()) {
            tmp = operandsIt.next();
            if (!tmp.execute(scope, functions))
                return false;
            op = operatorsIt.next();
            if (op == TokenType.mulop)
                value = multiply(value, tmp.getValue());
            else
                value = divide(value, tmp.getValue());
        }
        return true;
    }
}
