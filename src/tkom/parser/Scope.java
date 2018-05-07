package tkom.parser;

import tkom.parser.statements.FunctionCallStatement;
import tkom.parser.types.Rectangle;
import tkom.scanner.TokenType;

import java.util.HashMap;

/**
 * Created by karolina on 28.04.18.
 */
public class Scope {

    private HashMap<String, Object> variables;
    private FunctionCallStatement funCall;

    public Scope(FunctionCallStatement funCall) {
        variables = new HashMap<>();
        this.funCall = funCall;
    }

    public Scope() {
        variables = new HashMap<>();
    }

    public Object getVariableValue(String name) throws Exception {
        if (!variables.containsKey(name))
            throw new Exception("variable doesn't exist");
        return variables.get(name);
    }

    public int getRectangleFieldValue(String name, TokenType field) throws Exception {
        if (!variables.containsKey(name))
            throw new Exception("variable doesn't exist");
        if (variables.get(name) instanceof Rectangle)
            return ((Rectangle) variables.get(name)).getField(field);
        return -1;
    }

    public boolean addVariable(String name, Object value) throws Exception {
        if (variables.containsKey(name))
            throw new Exception("variable already exist");
        variables.put(name, value);
        return true;
    }

    public void changeVariableValue(String name, Object value) throws Exception {
        if (!variables.containsKey(name))
            throw new Exception("variable doesn't exist");
        else
            variables.put(name, value);
    }

    public Object changeRectangleFieldValue(String name, TokenType field, int value) throws Exception {
        if (!variables.containsKey(name))
            throw new Exception("variable doesn't exist");
        if (variables.get(name) instanceof Rectangle)
            ((Rectangle) variables.get(name)).setField(field, value);
        return null;
    }


    public FunctionCallStatement getFunCall() {
        return funCall;
    }
}
