package tkom.parser.statements;

import tkom.exceptions.ExecutionException;
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

    private void validateReturnedType(TokenType t) throws ExecutionException {
        switch (t) {
            case Int:
                if (!(value instanceof Integer))
                    throw new ExecutionException("function " + name + " returns type " + t
                            + "found type " + value.getClass());
                break;
            case String:
                if (!(value instanceof String))
                    throw new ExecutionException("function " + name + " returns type " + t
                            + "found type " + value.getClass());
                break;
            case Bool:
                if (!(value instanceof Boolean))
                    throw new ExecutionException("function " + name + " returns type " + t
                            + "found type " + value.getClass());
                break;
            case Rectangle:
                if (!(value instanceof Rectangle))
                    throw new ExecutionException("function " + name + " returns type " + t
                            + "found type " + value.getClass());
                break;
        }
    }

    private TokenType getExpressionType(Object value, String name) throws ExecutionException {
        TokenType t;
        if (value instanceof Integer)
            t = TokenType.Int;
        else if (value instanceof String)
            t = TokenType.String;
        else if (value instanceof Boolean)
            t = TokenType.Bool;
        else if (value instanceof Rectangle)
            t = TokenType.Rectangle;
        else throw new ExecutionException("invalid argument type in function " + name);
        return t;
    }

    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        FunctionDefinition function = functions.get(name);
        if (function == null)
            throw new ExecutionException("function " + name + " wasn't defined");

        if (function.getParameters().size() != arguments.size())
            throw new ExecutionException("invalid number of arguments - function " + name +
                    " has " + function.getParameters().size() + " arguments");

        Iterator<Map.Entry<String, TokenType>> iterator = function.getParameters().entrySet().iterator();
        Scope newScope = new Scope(this);
        for (AddExpression arg : arguments) {
            arg.execute(scope, functions);
            HashMap.Entry it = iterator.next();
            TokenType t = getExpressionType(arg.getValue(), name);
            if (t != it.getValue())
                throw new ExecutionException("calling function " + name + ": invalid argument type, found "
                        + t + " required " + it.getValue());
            newScope.addVariable(t, (String) it.getKey(), arg.getValue());
        }

        TokenType expected = function.getFunctionType();
        function.getBlock().execute(newScope, functions);
        validateReturnedType(expected);
    }
}