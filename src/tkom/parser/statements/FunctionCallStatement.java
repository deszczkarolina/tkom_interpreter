package tkom.parser.statements;

import tkom.parser.expressions.AddExpression;
import tkom.parser.Scope;
import tkom.parser.Value;
import tkom.scanner.TokenType;

import java.util.*;

/**
 * Created by karolina on 24.04.18.
 */
public class FunctionCallStatement extends Value {

    private String name;
    private Vector<AddExpression> arguments;
    private Object value;

    public String getName() {
        return name;
    }
    public Vector<AddExpression> getArguments() {
        return arguments;
    }
    public FunctionCallStatement(FunctionCallStatement other) {
        this.name = other.getName();
        this.arguments = other.getArguments();
        this.value = other.getValue();
    }
    public void setValue(Object value) {
        this.value = value;
    }



    public FunctionCallStatement(String name, Vector<AddExpression> arguments) {
        this.name = name;
        this.arguments = arguments;
    }



    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        FunctionDefinition function = functions.get(name);
        if (function == null)
           throw new Exception("function wasn't defined");

        if(function.getParameters().size() != arguments.size())
            throw new Exception("invalid number of arguments");

        Iterator<Map.Entry<String, TokenType>> iterator = function.getParameters().entrySet().iterator();
        Scope newScope = new Scope(this);
        for (AddExpression arg: arguments){
            if(!arg.execute(scope,functions))
                return false;

            HashMap.Entry it = iterator.next();
            if(!(newScope.addVariable((String)it.getKey(), arg.getValue())));
        }

        return function.getBlock().execute(newScope, functions);
    }

    @Override
    public Type getType() {
        return Type.FunctionCallStatement;
    }
}



