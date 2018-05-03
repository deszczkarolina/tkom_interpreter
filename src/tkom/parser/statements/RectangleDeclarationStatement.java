package tkom.parser.statements;

import tkom.parser.Node;
import tkom.parser.types.Rectangle;
import tkom.parser.Scope;

import java.util.HashMap;

/**
 * Created by karolina on 03.05.18.
 */
public class RectangleDeclarationStatement extends Statement {

    private Rectangle rect;
    private String ident;

    public RectangleDeclarationStatement(Rectangle rect, String ident) {
        this.rect = rect;
        this.ident = ident;
    }

    public String getIdent() {
        return ident;
    }
    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        scope.addVariable(ident,rect);
        return true;
    }

    @Override
    public Node.Type getType() {
        return Node.Type.RectangleDeclarationStatement;
    }

}
