package tkom.parser.statements;

import tkom.parser.Node;
import tkom.parser.Scope;
import tkom.parser.Value;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by karolina on 26.04.18.
 */
public class VarDeclarationStatement extends Statement {

    private String type;
    private String ident;
    private Value initializer;

    public VarDeclarationStatement(String type, String ident){
        this.type = type;
        this.ident = ident;
    }

    public VarDeclarationStatement(String type, String ident, Value init) {
        this.type = type;
        this.ident = ident;
        this.initializer = init;
    }

    public String getVarType() {
        return type;
    }

    public String getIdent() {
        return ident;
    }

    public Value getInitializer() {
        return initializer;
    }


    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
       if (initializer != null) {
            if (!initializer.execute(scope, functions))
                return false;
            scope.addVariable(ident, initializer.getValue());
        }
        else scope.addVariable(ident,null);
        return true;
    }

    @Override
    public Node.Type getType() {
        return Node.Type.VarDeclarationStatement;
    }

}
