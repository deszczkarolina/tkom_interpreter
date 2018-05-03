package tkom.parser;

import tkom.parser.statements.FunctionDefinition;

import java.util.HashMap;

/**
 * Created by karolina on 27.04.18.
 */
public class Variable extends Value {

    private String name;
    private Object value;

    public Variable(String ident) {
        this.name = ident;
    }


    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        value = scope.getVariableValue(name);
        return true;
    }

    @Override
    public Type getType() {
        return Type.Identifier;
    }
}

