package tkom.parser.statements;

import tkom.parser.Node;
import tkom.parser.Scope;
import tkom.parser.Value;

import java.util.HashMap;

/**
 * Created by karolina on 26.04.18.
 */
public class VarDeclarationStatement extends Statement {

   private String ident;
    private Value initializer;

    public VarDeclarationStatement(String ident) {
      this.ident = ident;
    }

    public VarDeclarationStatement(String ident, Value init) {
       this.ident = ident;
        this.initializer = init;
    }

    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        if (initializer != null) {
            if (!initializer.execute(scope, functions))
                return false;
            scope.addVariable(ident, initializer.getValue());
        } else scope.addVariable(ident, null);
        return true;
    }

    @Override
    public Node.Type getType() {
        return Node.Type.VarDeclarationStatement;
    }

}
