package tkom.parser.statements;

import tkom.parser.Node;
import tkom.parser.conditions.OrCondition;
import tkom.parser.Scope;

import java.util.HashMap;

/**
 * Created by karolina on 24.04.18.
 */
public class WhileStatement extends Statement {

    private VarDeclarationStatement.BlockStatement block;
    private OrCondition condition;

   public WhileStatement(OrCondition condition, VarDeclarationStatement.BlockStatement block) {
        this.block = block;
        this.condition = condition;
    }


    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        if(!condition.execute(scope, functions))
            return false;
        while (condition.getValue()){
            if(!block.execute(scope, functions))
                return false;
            if(!condition.execute(scope, functions))
                return false;
        }
        return true;
    }

    @Override
    public Node.Type getType() {
        return Node.Type.WhileStatement;
    }
}
