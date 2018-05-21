package tkom.parser.statements;

import tkom.parser.Node;
import tkom.parser.Scope;
import tkom.parser.conditions.OrCondition;

import java.util.HashMap;

/**
 * Created by karolina on 24.04.18.
 */
public class IfStatement extends Statement {

    private OrCondition condition;
    private BlockStatement trueBlock;
    private BlockStatement falseBlock;

    public IfStatement(OrCondition condition, BlockStatement trueBlock, BlockStatement falseBlock) {
        this.condition = condition;
        this.trueBlock = trueBlock;
        this.falseBlock = falseBlock;
    }

    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        if (!condition.execute(scope, functions))
            return false;
        if (condition.getValue()) {
            if (!trueBlock.execute(scope, functions))
                return false;
        } else {
            if (!falseBlock.execute(scope, functions))
                return false;
        }
        return true;
    }

    @Override
    public Node.Type getType() {
        return Node.Type.IfStatement;
    }
}
