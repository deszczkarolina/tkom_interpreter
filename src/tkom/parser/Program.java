package tkom.parser;

import tkom.parser.expressions.AddExpression;
import tkom.parser.statements.FunctionCallStatement;
import tkom.parser.statements.FunctionDefinition;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by karolina on 23.04.18.
 */
public class Program extends Node {

    private Vector<FunctionDefinition> functions;
    private Object value;


    public Program(Vector<FunctionDefinition> functions) {
        this.functions = functions;
        this.value = 1;
    }

    public Object getValue() {
        return value;
    }

    public boolean execute() throws Exception {

        HashMap<String, FunctionDefinition> definedFunctions = new HashMap<>();
        for (FunctionDefinition it : functions) {
            definedFunctions.put(it.getName(), it);
        }
        if (!definedFunctions.containsKey("main"))
            throw new Exception("main not found");


        Vector<AddExpression> arguments = new Vector<>();
        Scope scope = new Scope();
        FunctionCallStatement main = new FunctionCallStatement("main", arguments);
        if (!main.execute(scope, definedFunctions))
            return false;
        value = main.getValue();
        return true;
    }

    @Override
    public Type getType() {
        return Type.Program;
    }
}

