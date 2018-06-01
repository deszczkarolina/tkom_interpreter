package tkom.parser.statements;


import tkom.parser.Scope;
import tkom.parser.conditions.OrCondition;

import java.util.HashMap;

/**
 * Created by karolina on 24.04.18.
 */
public class IfStatement extends Statement {

    private OrCondition condition;
    private BlockStatement falseBlock;
    private BlockStatement trueBlock;

    public BlockStatement getTrueBlock() {
        return trueBlock;
    }

    public BlockStatement getFalseBlock() {
        return falseBlock;
    }


    public IfStatement(OrCondition condition, BlockStatement trueBlock, BlockStatement falseBlock) {
        this.condition = condition;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }

    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        condition.execute(scope, functions);
        if (condition.getValue())
            trueBlock.execute(scope, functions);
        else if (falseBlock != null)
            falseBlock.execute(scope, functions);
    }
}
