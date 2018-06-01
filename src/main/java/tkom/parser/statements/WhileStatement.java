package tkom.parser.statements;

import tkom.parser.Scope;
import tkom.parser.conditions.OrCondition;

import java.util.HashMap;

/**
 * Created by karolina on 24.04.18.
 */
public class WhileStatement extends Statement {

    public BlockStatement getBlock() {
        return block;
    }

    private BlockStatement block;
    private OrCondition condition;

    public WhileStatement(OrCondition condition, BlockStatement block) {
        this.block = block;
        this.condition = condition;
    }


    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        condition.execute(scope, functions);
        while (condition.getValue()) {
            block.execute(scope, functions);
            condition.execute(scope, functions);
        }
    }
}
