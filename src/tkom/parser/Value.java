package tkom.parser;


import tkom.parser.types.Rectangle;

/**
 * Created by karolina on 27.04.18.
 */
public abstract class Value extends NotRootNode {

    public abstract Object getValue();

    protected Object add(Object a, Object b) throws Exception {
        if (a instanceof Integer && b instanceof Integer)
            return (int) a + (int) b;
        if (a instanceof Rectangle && b instanceof Rectangle)
            return ((Rectangle) a).add((Rectangle) b);
        else throw new Exception("cannot add variables of types" + a.getClass() + ", " + b.getClass());

    }

    protected Object subtract(Object a, Object b) throws Exception {
        if (a instanceof Integer && b instanceof Integer)
            return (int) a - (int) b;

        else throw new Exception("cannot subtract variables of types" + a.getClass() + ", " + b.getClass());
    }

    protected Object multiply(Object a, Object b) throws Exception {
        if (a instanceof Integer && b instanceof Integer)
            return (int) a * (int) b;

        if (a instanceof Rectangle && b instanceof Rectangle)
            return ((Rectangle) a).intersect((Rectangle) b);

        if (a instanceof Rectangle && b instanceof Integer)
            return ((Rectangle) a).scale((int) b);

        if (a instanceof Integer && b instanceof Rectangle)
            return ((Rectangle) b).scale((int) a);
        else throw new Exception("cannot multiply variables of types" + a.getClass() + ", " + b.getClass());
    }

    protected Object divide(Object a, Object b) throws Exception {
        if (a instanceof Integer && b instanceof Integer)
            return (int) a / (int) b;
        else throw new Exception("cannot divide variables of types" + a.getClass() + ", " + b.getClass());
    }
}
