package tkom.parser.statements;

import tkom.parser.Scope;
import tkom.parser.types.Rectangle;

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

    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        scope.addVariable(ident, rect);
        return true;
    }


}
