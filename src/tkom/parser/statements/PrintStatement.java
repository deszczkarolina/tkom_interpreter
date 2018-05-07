package tkom.parser.statements;

import tkom.parser.Node;
import tkom.parser.Scope;
import tkom.parser.Value;

import java.util.HashMap;

/**
 * Created by karolina on 24.04.18.
 */
public class PrintStatement extends Statement {

    private Value toShow;


    public PrintStatement(Value toShow) {
        this.toShow = toShow;
    }

    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {

        if (!toShow.execute(scope, functions))
            return false;
        System.out.println(toShow.getValue());
        return true;
    }

    @Override
    public Node.Type getType() {
        return Node.Type.PrintStatement;
    }
}
