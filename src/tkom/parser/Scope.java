package tkom.parser;

import tkom.parser.statements.FunctionCallStatement;

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

    public Scope(){variables = new HashMap<>();}

    public Object getVariableValue(String name) throws Exception {
        if (!variables.containsKey(name))
           throw new Exception("variable doesn't exist");
        return variables.get(name);
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

    public FunctionCallStatement getFunCall() {
        return funCall;
    }
}
