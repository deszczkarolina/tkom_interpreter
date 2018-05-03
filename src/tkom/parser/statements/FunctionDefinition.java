package tkom.parser.statements;


import tkom.parser.Node;
import tkom.parser.Scope;
import tkom.scanner.TokenType;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**System.out.print("error: expected token: ");
 * Created by karolina on 23.04.18.
 */
public class FunctionDefinition extends Node {

    private String name;
    private LinkedHashMap<String,TokenType> parameters;
    private BlockStatement block;

    public String getName() {
        return name;
    }
    public BlockStatement getBlock() {
        return block;
    }

   public FunctionDefinition(String name, LinkedHashMap<String,TokenType> parameters, BlockStatement block) {
        this.name = name;
        this.parameters = parameters;
        this.block = block;
    }

    public LinkedHashMap<String,TokenType> getParameters() {
        return parameters;
    }


    public boolean execute (HashMap<String, FunctionDefinition> functions) throws Exception {
       Scope scope = new Scope();
        return this.block.execute(scope,functions);
    }

    @Override
    public Type getType() {
        return Type.FunctionDefinition;
    }
}
