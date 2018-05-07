package tkom.parser.types;

import tkom.parser.Scope;
import tkom.parser.Value;
import tkom.parser.statements.FunctionDefinition;

import java.util.HashMap;

/**
 * Created by karolina on 28.04.18.
 */
public class Text extends Value {

    private String text;


    public Text(String text) {
        this.text = text;
    }

    @Override
    public String getValue() {
        return text;
    }

    @Override
    public boolean execute(Scope scope, HashMap<String, FunctionDefinition> functions) {
        return true;
    }

    @Override
    public Type getType() {
        return Type.Text;
    }
}
