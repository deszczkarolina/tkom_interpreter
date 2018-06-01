package tkom.parser.types;

import tkom.parser.Scope;
import tkom.parser.Value;
import tkom.parser.statements.FunctionDefinition;

import java.util.HashMap;

/**
 * Created by karolina on 28.04.18.
 */
public class Number extends Value {

    private int value;

    public Number(int value) {
        this.value = value;
    }


    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) {
    }

}
