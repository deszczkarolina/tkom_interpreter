package tkom.parser;

import tkom.parser.statements.FunctionDefinition;

import java.util.HashMap;

/**
 * Created by karolina on 28.04.18.
 */
public abstract class Node {
    public abstract boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception;
}
