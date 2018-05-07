package tkom.parser;

import tkom.parser.types.Rectangle;

/**
 * Created by karolina on 27.04.18.
 */
public abstract class Logical extends NotRootNode {

    public abstract boolean getValue();

    protected boolean isEqual (Object a, Object b){
        if (a instanceof Rectangle && b instanceof Rectangle) {
            if (((Rectangle) a).compareRectangles((Rectangle) b) == 0)
                return true;
            else
                return false;
        }
        else
            return ((Value)a).getValue() ==((Value)b).getValue();
    }

    protected boolean isLessThen(Object a, Object b) throws Exception {
        if (a instanceof Integer && b instanceof Integer)
            return (int) a < (int) b;
        if (a instanceof Rectangle && b instanceof Rectangle)
            return ((Rectangle) a).compareRectangles((Rectangle) b) < 0;

        else throw new Exception("cannot compare variables of types" + a.getClass() + ", " + b.getClass());
    }

    protected boolean isLessOrEqualThen(Object a, Object b) throws Exception {
        if (a instanceof Integer && b instanceof Integer)
            return (int) a <= (int) b;
        if (a instanceof Rectangle && b instanceof Rectangle)
            return ((Rectangle) a).compareRectangles((Rectangle) b) <= 0;

        else throw new Exception("cannot compare variables of types" + a.getClass() + ", " + b.getClass());
    }
}
