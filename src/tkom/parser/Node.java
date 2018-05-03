package tkom.parser;

/**
 * Created by karolina on 28.04.18.
 */
public abstract class Node {

    public enum Type{
        AssignStatement,
        FunctionCallStatement,
        IfStatement,
        VarDeclarationStatement,
        RectangleDeclarationStatement,
        Program,
        ReturnStatement,
        BlockStatement,
        WhileStatement,
        PrintStatement,
        OrCondition,
        AndCondition,
        Condition,
        ComCondition,
        AddExpression,
        MulExpression,
        Expression,
        Number,
        Rectangle,
        Text,
        LogicalValue,
        FunctionDefinition,
        Identifier,
    }

    public abstract Type getType();
}
