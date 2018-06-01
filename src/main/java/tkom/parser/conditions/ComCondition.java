package tkom.parser.conditions;

import tkom.parser.Logical;
import tkom.parser.Scope;
import tkom.parser.statements.FunctionDefinition;

import java.util.HashMap;

/**
 * Created by karolina on 27.04.18.
 */
public class ComCondition extends Logical {

    private Logical operand;
    private boolean negate;
    private boolean value;

    public ComCondition(Logical operand, boolean negate) {
        this.operand = operand;
        this.negate = negate;
    }

    @Override
    public boolean getValue() {
        return value;
    }

    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        operand.execute(scope, functions);
        value = operand.getValue();
        if (negate)
            value = !value;
    }

}
