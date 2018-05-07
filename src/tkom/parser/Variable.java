package tkom.parser;

import tkom.parser.statements.FunctionDefinition;
import tkom.parser.types.Rectangle;
import tkom.scanner.TokenType;

import java.util.HashMap;

/**
 * Created by karolina on 27.04.18.
 */
public class Variable extends Value {

    private String name;
    private Object value;
    private TokenType field;

    public Variable(String ident) {
        this.name = ident;
    }

    public Variable(String name, TokenType field) {
        this.name = name;
        this.field = field;
    }

    @Override
    public Object getValue() {
        if (value instanceof Rectangle && field != null)
            return ((Rectangle) value).getField(field);
        return value;
    }

    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        if (value instanceof Rectangle && field != null)
            value = scope.getRectangleFieldValue(name, field);
        else
            value = scope.getVariableValue(name);
        return true;
    }

    @Override
    public Type getType() {
        return Type.Identifier;
    }
}

