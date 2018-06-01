package tkom.parser.statements;

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
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {

        toShow.execute(scope, functions);
        System.out.print(toShow.getValue());
    }
}
