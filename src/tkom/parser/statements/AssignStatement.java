package tkom.parser.statements;
import tkom.parser.Scope;
import tkom.parser.Value;
import java.util.HashMap;

/**
 * Created by karolina on 03.05.18.
 */
public class AssignStatement extends Statement{

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
