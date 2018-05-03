package tkom.parser.statements;

import tkom.parser.Node;
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
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        if(!expression.execute(scope, functions))
            return false;
        Object value;
        value = expression.getValue();
        if (scope.getFunCall() != null)
            scope.getFunCall().setValue(value);
        return true;
    }

    @Override
    public Node.Type getType() {
        return Node.Type.ReturnStatement;
    }

    /**
     * Created by karolina on 24.04.18.
     */
    public static class AssignStatement extends Statement {

        private String ident;
        private Value value;



        public AssignStatement(String ident, Value value) {
            this.ident = ident;
            this.value = value;
        }

        @Override
        public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
            if (!value.execute(scope, functions))
                return false;
            scope.changeVariableValue(ident, value.getValue());
            return true;
        }

        @Override
        public Type getType() {
            return Type.AssignStatement;
        }
    }
}
