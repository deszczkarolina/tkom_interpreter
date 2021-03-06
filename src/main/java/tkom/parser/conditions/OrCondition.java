package tkom.parser.conditions;

import tkom.parser.Logical;
import tkom.parser.Scope;
import tkom.parser.statements.FunctionDefinition;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by karolina on 27.04.18.
 */
public class OrCondition extends Logical {

    private boolean value;
    private Vector<AndCondition> operands;

    public OrCondition(Vector<AndCondition> operands) {
        this.operands = operands;
    }


    @Override
    public boolean getValue() {
        return value;
    }

    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        for (AndCondition it : operands) {
            it.execute(scope, functions);
            if (it.getValue()) {
                value = true;
                return;
            }
        }
        value = false;
    }

}
