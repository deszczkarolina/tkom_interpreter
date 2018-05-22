package tkom.parser.expressions;


import tkom.parser.Scope;
import tkom.parser.Value;
import tkom.parser.statements.FunctionDefinition;

import java.util.HashMap;

/**
 * Created by karolina on 26.04.18.
 */
public class Expression extends Value {

    private Value operand;
    private boolean negate;
    private Object value;


    public Expression(Value operand, boolean negate) {
        this.operand = operand;
        this.negate = negate;
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        if (!operand.execute(scope, functions))
            return false;
        value = operand.getValue();
        if (negate)
            value = (int) value * (-1);
        return true;
    }

}
