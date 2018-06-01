package tkom.parser.statements;

import tkom.parser.Scope;
import tkom.parser.Value;
import tkom.scanner.TokenType;

import java.util.HashMap;

/**
 * Created by karolina on 26.04.18.
 */
public class VarDeclarationStatement extends Statement {

    private TokenType type;
    private String ident;
    private Value initializer;

    public VarDeclarationStatement(TokenType type, String ident) {
        this.type = type;
        this.ident = ident;
    }

    public VarDeclarationStatement(TokenType type, String ident, Value init) {
       this.ident = ident;
        this.initializer = init;
        this.type = type;
    }

    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        if (initializer != null) {
            initializer.execute(scope, functions);
            scope.addVariable(type, ident, initializer.getValue());
        } else
            scope.addVariable(type, ident, null);
    }
}
