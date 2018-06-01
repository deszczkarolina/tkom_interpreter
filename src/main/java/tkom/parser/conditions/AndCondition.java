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
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        for (ComCondition it : operands) {
            it.execute(scope, functions);
            if (!it.getValue()) {
                value = false;
                return;
            }
        }
        value = true;
    }
}
