package tkom.parser.statements;

import tkom.parser.Scope;
import tkom.scanner.TokenType;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by karolina on 23.04.18.
 */
public class FunctionDefinition {

    private TokenType type;
    private String name;
    private LinkedHashMap<String, TokenType> parameters;
    private BlockStatement block;

    public FunctionDefinition(TokenType t, String name, LinkedHashMap<String, TokenType> parameters, BlockStatement block) {
        this.type = t;
        this.name = name;
        this.parameters = parameters;
        this.block = block;
    }

    public String getName() {
        return name;
    }

    public BlockStatement getBlock() {
        return block;
    }

    public TokenType getFunctionType(){
        return type;
    }
    public LinkedHashMap<String, TokenType> getParameters() {
        return parameters;
    }


    public void execute(HashMap<String, FunctionDefinition> functions) throws Exception {
        Scope scope = new Scope();
        block.execute(scope, functions);
    }
}
