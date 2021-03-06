package tkom.parser.statements;

import tkom.parser.Scope;
import tkom.parser.Value;

import java.util.HashMap;

/**
 * Created by karolina on 24.04.18.
 */
public class ReturnStatement extends Statement {

    private Value expression;

    public ReturnStatement(Value expression) {
        this.expression = expression;
    }

    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        expression.execute(scope, functions);
        Object value;
        value = expression.getValue();
        if (scope.getFunCall() != null)
            scope.getFunCall().setValue(value);
    }

}
