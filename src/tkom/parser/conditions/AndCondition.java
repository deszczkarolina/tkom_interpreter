package tkom.parser.conditions;

import tkom.parser.Logical;
import tkom.parser.Scope;
import tkom.parser.statements.FunctionDefinition;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by karolina on 27.04.18.
 */
public class AndCondition extends Logical {

    private Vector<ComCondition> operands;
    private boolean value;

    public AndCondition(Vector<ComCondition> operands) {
        this.operands = operands;
    }


    @Override
    public boolean getValue() {
        return value;
    }

    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        for (ComCondition it : operands) {
            if (!it.execute(scope, functions))
                return false;
            if (!it.getValue()) {
                value = false;
                return true;
            }
        }
        value = true;
        return true;
    }

    @Override
    public Type getType() {
        return Type.AndCondition;
    }
}
