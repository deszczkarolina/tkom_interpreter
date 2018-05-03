package tkom.parser.statements;

import tkom.parser.Node;
import tkom.parser.Scope;

import java.util.HashMap;

/**
 * Created by karolina on 28.04.18.
 */
public abstract class NotRootNode extends Node {
    public abstract boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception;
}
