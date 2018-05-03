package tkom.parser;

import tkom.parser.types.Rectangle;

/**
 * Created by karolina on 27.04.18.
 */
public abstract class Logical extends NotRootNode {

    public abstract boolean getValue();

    public boolean isLessThen(Object a , Object b) throws Exception {
        if (a instanceof Integer && b instanceof Integer)
            return (int) a < (int)b;
        if (a instanceof Rectangle && b instanceof Rectangle)
            if (((Rectangle)a).compareRectangles((Rectangle)b) < 0)
                return true;
             else return false;

        else throw new Exception("cannot compare variables of types" + a.getClass() + ", " + b.getClass());
    }

    public boolean isLessOrEqualThen(Object a , Object b) throws Exception {
        if (a instanceof Integer && b instanceof Integer)
            return (int) a <= (int)b;
        if (a instanceof Rectangle && b instanceof Rectangle)
            if (((Rectangle)a).compareRectangles((Rectangle)b) > 0)
                return false;
            else return true;

        else throw new Exception("cannot compare variables of types" + a.getClass() + ", " + b.getClass());
    }
}
