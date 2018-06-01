package tkom.parser.types;

import tkom.parser.Scope;
import tkom.parser.Value;
import tkom.parser.statements.FunctionDefinition;

import java.util.HashMap;

/**
 * Created by karolina on 28.04.18.
 */
public class Bool extends Value {

    private boolean value;

    public Bool(boolean value) {
        this.value = value;
    }

    @Override
    public Boolean getValue() {
        return value;
    }

    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
    }
}
