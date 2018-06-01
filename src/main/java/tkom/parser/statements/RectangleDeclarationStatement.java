package tkom.parser.statements;

import tkom.parser.Scope;
import tkom.parser.expressions.AddExpression;
import tkom.parser.types.Rectangle;
import tkom.scanner.TokenType;

import java.util.HashMap;

/**
 * Created by karolina on 03.05.18.
 */
public class RectangleDeclarationStatement extends Statement {

    private Rectangle rect;
    private String ident;
    private AddExpression init;

    public RectangleDeclarationStatement(Rectangle rect, String ident) {
        this.rect = rect;
        this.ident = ident;
        init = null;
    }

    public RectangleDeclarationStatement(Rectangle rect, String ident, AddExpression init) {
        this.rect = rect;
        this.ident = ident;
        init = init;
    }


    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        scope.addVariable(TokenType.Rectangle, ident, rect);
    }


}
