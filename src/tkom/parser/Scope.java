package tkom.parser;

import tkom.exceptions.ExecutionException;
import tkom.parser.statements.FunctionCallStatement;
import tkom.parser.types.Rectangle;
import tkom.scanner.TokenType;

import java.util.HashMap;

/**
 * Created by karolina on 28.04.18.
 */
public class Scope {

    private HashMap<String, Var> variables;
    private FunctionCallStatement funCall;

    public Scope(FunctionCallStatement funCall) {
        variables = new HashMap<>();
        this.funCall = funCall;
    }

    private class Var {
        private Object value;
        private TokenType type;

        public Var(Object value, TokenType type) {
            this.value = value;
            this.type = type;
        }


    }

    public Scope(){
        variables = new HashMap<>();
    }

    private TokenType getValueType(Object value, String var) throws ExecutionException {
        TokenType t;
        if (value instanceof Integer)
            t = TokenType.Int;
        else if (value instanceof String)
            t = TokenType.String;
        else if (value instanceof Boolean)
            t = TokenType.Bool;
        else if (value instanceof Rectangle)
            t = TokenType.Rectangle;
        else throw new ExecutionException("variable " + var + " of invalid type");
        return t;
    }
    public Object getVariableValue(String name) throws Exception {
        if (!variables.containsKey(name))
            throw new ExecutionException("variable " + name + " doesn't exist");
        return variables.get(name).value;
    }

    public int getRectangleFieldValue(String name, TokenType field) throws Exception {
        if (!variables.containsKey(name))
            throw new ExecutionException("variable " + name + " doesn't exist");
        if (variables.get(name).type == TokenType.Rectangle)
            return ((Rectangle) variables.get(name).value).getField(field);
        else throw new ExecutionException(field + "is not a Rectangle field");
    }

    public boolean addVariable(TokenType type, String name, Object value) throws Exception {
        if (variables.containsKey(name))
            throw new ExecutionException("variable " + name + " already exists");
        if (getValueType(value, name) != type)
            throw new ExecutionException("variable " + name + " should be of type " + type + " found type: " + getValueType(value, name));
        variables.put(name, new Var(value, type));
        return true;
    }

    public void changeVariableValue(String name, Object value) throws Exception {
        if (!variables.containsKey(name))
            throw new ExecutionException("variable " + name + " doesn't exist");
        else {
            if (getValueType(value, name) != variables.get(name).type)
                throw new ExecutionException("variable " + name + " should be of type " + variables.get(name).type +
                        " found type: " + getValueType(value, name));
            TokenType t = variables.get(name).type;
            variables.put(name, new Var(value, t));
        }
    }

    public void changeRectangleFieldValue(String name, TokenType field, int value) throws Exception {
        if (!variables.containsKey(name))
            throw new ExecutionException("variable " + name + " doesn't exist");
        if (variables.get(name).type == TokenType.Rectangle) {
            Rectangle tmp = new Rectangle((Rectangle) variables.get(name).value);
            TokenType t = variables.get(name).type;
         tmp.setField(field, value);
            variables.put(name, new Var(tmp, t));
         }
    }
    public FunctionCallStatement getFunCall() {
        return funCall;
    }
}
