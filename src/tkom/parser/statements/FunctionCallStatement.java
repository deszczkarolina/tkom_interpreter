package tkom.parser.statements;

import tkom.parser.Scope;
import tkom.parser.Value;
import tkom.parser.expressions.AddExpression;
import tkom.parser.types.Rectangle;
import tkom.scanner.TokenType;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Created by karolina on 24.04.18.
 */
public class FunctionCallStatement extends Value {

    private String name;
    private Vector<AddExpression> arguments;
    private Object value;

    public FunctionCallStatement(String name, Vector<AddExpression> arguments) {
        this.name = name;
        this.arguments = arguments;
    }

    @Override
    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        FunctionDefinition function = functions.get(name);

        if (function == null)
            throw new Exception("function " + name + " wasn't defined");

        if (function.getParameters().size() != arguments.size())
            throw new Exception("invalid number of arguments");

        TokenType t = function.getFunctionType();
        Iterator<Map.Entry<String, TokenType>> iterator = function.getParameters().entrySet().iterator();
        Scope newScope = new Scope(this);
        for (AddExpression arg : arguments) {
            if (!arg.execute(scope, functions))
                return false;

            HashMap.Entry it = iterator.next();
            if (!(newScope.addVariable((String) it.getKey(), arg.getValue()))) ;
        }

        boolean result = function.getBlock().execute(newScope, functions);

        switch(t){
            case Int: if(!(value instanceof Integer))
                throw new Exception("function " + name + " returns " + t);
                break;
            case String: if(!(value instanceof String))
                throw new Exception("function " + name + " returns " + t);
                break;
            case Bool: if(!(value instanceof Boolean))
                throw new Exception("function " + name + " returns " + t);
                break;
            case Rectangle: if(!(value instanceof Rectangle))
                throw new Exception("function " + name + " returns " + t);
                break;
        }
      return result;
    }
}



