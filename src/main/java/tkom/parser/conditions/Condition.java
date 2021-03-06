package tkom.parser.conditions;

import tkom.parser.Logical;
import tkom.parser.Scope;
import tkom.parser.expressions.AddExpression;
import tkom.parser.statements.FunctionDefinition;
import tkom.scanner.TokenType;

import java.util.HashMap;

/**
 * Created by karolina on 27.04.18.
 */
public class Condition extends Logical {

    private AddExpression leftOperand;
    private AddExpression rightOperand;
    private TokenType logOp;
    private boolean value;

    public Condition(AddExpression leftOperand, AddExpression rightOperand, TokenType logOp) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.logOp = logOp;

    }

    @Override
    public boolean getValue() {
        return value;
    }

    @Override
    public void execute(Scope scope, HashMap<String, FunctionDefinition> functions) throws Exception {
        leftOperand.execute(scope, functions);
        rightOperand.execute(scope, functions);

        switch (logOp) {
            case eqop:
                value = isEqual(leftOperand.getValue(), rightOperand.getValue());
                break;
            case neqop:
                value = !isEqual(leftOperand.getValue(), rightOperand.getValue());
                break;
            case ltop:
                value = isLessThen(leftOperand.getValue(), rightOperand.getValue());
                break;
            case leop:
                value = isLessOrEqualThen(leftOperand.getValue(), rightOperand.getValue());
                break;
            case gtop:
                value = !isLessOrEqualThen(leftOperand.getValue(), rightOperand.getValue());
                break;
            case geop:
                value = !isLessThen(leftOperand.getValue(), rightOperand.getValue());
                break;
            default:
                break;
        }
    }

}
