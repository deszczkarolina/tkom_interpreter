package tkom.parser.statements;

import tkom.parser.Scope;

import java.util.HashMap;
import java.util.Vector;

/**
 * Created by karolina on 23.04.18.
 */
public class BlockStatement extends Statement {

    public Vector<Statement> getStatements() {
        return statements;
    }

    private Vector<Statement> statements;

    public BlockStatement(Vector<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {

        for (Statement instruction : statements) {
            if (instruction instanceof ReturnStatement) {
                instruction.execute(scope, functions);
                break;
            }
            instruction.execute(scope, functions);
        }
    }


}