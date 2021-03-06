package tkom.parser.statements;

import tkom.parser.Scope;
import tkom.parser.Value;
import tkom.scanner.TokenType;

import java.util.HashMap;

/**
 * Created by karolina on 03.05.18.
 */
public class AssignStatement extends Statement {

    private String ident;
    private Value value;
    private TokenType field;

    public AssignStatement(String ident, Value value) {
        this.ident = ident;
        this.value = value;
        this.field = null;
    }

    public AssignStatement(String ident, Value value, TokenType field) {
        this.ident = ident;
        this.value = value;
        this.field = field;
    }

    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        value.execute(scope, functions);
        if (field != null)
            scope.changeRectangleFieldValue(ident, field, (int) value.getValue());
        else
            scope.changeVariableValue(ident, value.getValue());
    }


}
