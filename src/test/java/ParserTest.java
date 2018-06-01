import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tkom.exceptions.ExecutionException;
import tkom.exceptions.ParserException;
import tkom.parser.Parser;
import tkom.parser.Program;
import tkom.parser.types.Rectangle;
import tkom.scanner.Scanner;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by karolina on 26.04.18.
 */
public class ParserTest {


    private Program test;

    private void init(String txt) throws Exception {
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        test = parser.parse();
    }

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void parseEmptyString() throws Exception {

        String txt = "";
        init(txt);
        expectedEx.expect(ExecutionException.class);
        test.execute();
    }

    @Test
    public void parseIntInitialization() throws Exception {
        String txt = "int main(){int a = 5; return a;}";
        init(txt);
        test.execute();
        assertEquals(5, test.getValue());
    }

    @Test
    public void parseStringInitialization() throws Exception {
        String txt = "string main(){string a = \"test\"; return a;}";
        init(txt);
        test.execute();
        assertEquals("test", test.getValue());
    }

    @Test
    public void parseBoolInitialization() throws Exception {
        String txt = "bool main(){bool a = false; return a;}";
        init(txt);
        test.execute();
        assertEquals(false, test.getValue());
    }

    @Test
    public void parseRectangleInitialization() throws Exception {

        String txt = "Rectangle main(){Rectangle a(1,1,1,1); return a;}";
        init(txt);
        test.execute();
        assertEquals(1, ((Rectangle) test.getValue()).getArea());
        assertEquals(4, ((Rectangle) test.getValue()).getPerimeter());
        assertEquals(1, ((Rectangle) test.getValue()).getX());
        assertEquals(1, ((Rectangle) test.getValue()).getY());
        assertEquals(1, ((Rectangle) test.getValue()).getWidth());
        assertEquals(1, ((Rectangle) test.getValue()).getLength());
    }

    @Test
    public void parseBooleanAssignment() throws Exception {
        String txt = "bool main(){bool test = false; test = true; return test;}";
        init(txt);
        test.execute();
        assertEquals(true, test.getValue());
    }

    @Test
    public void parseNegatedExpression() throws Exception {
        String txt = "int main(){int a = -5; return a;}";
        init(txt);
        test.execute();
        assertEquals(-5, test.getValue());
    }

    @Test
    public void parseMathExpression() throws Exception {
        String txt = "int main(){int a = 1-(-2); return a;}";
        init(txt);
        test.execute();
        assertEquals(3, test.getValue());
    }

    @Test
    public void parseIfStatement() throws Exception {
        String txt = "int main(){int c = 4; if(c < 10) then {c = c+5;} return c;}";
        init(txt);
        test.execute();
        assertEquals(9, test.getValue());
    }

    @Test
    public void parseIfElseStatement() throws Exception {
        String txt = "int main(){int c = 4; if(c > 10) then {c = c+5;}" +
                " else {c = 1;}return c;}";
        init(txt);
        test.execute();
        assertEquals(1, test.getValue());
    }

    @Test
    public void parseWhileStatement() throws Exception {
        String txt = "int main(){int c=1; int a=1; while (c<3) " +
                "{c = c + 1; a = a - 1;}" +
                "return a;}";
        init(txt);
        test.execute();
        assertEquals(-1, test.getValue());
    }

    @Test
    public void parsePrintVariable() throws Exception {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String txt = "int main(){int c = 1; print(c); return c;}";
        init(txt);
        test.execute();
        assertEquals("1", outContent.toString());
    }

    @Test
    public void parsePrintText() throws Exception {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String txt = "int main(){print(\"test text\"); return 0;}";
        init(txt);
        test.execute();
        assertEquals("test text", outContent.toString());
    }

    @Test
    public void parsePrintExpr() throws Exception {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        String txt = "int main(){int c = 1; print(2*c+6); return c;}";
        init(txt);
        test.execute();
        assertEquals("8", outContent.toString());
    }

    @Test
    public void parseRectangleFieldAccess() throws Exception {
        String txt = "int main(){Rectangle r1(0,5,4,3); Rectangle r2(1,1,1,1); " +
                "return r2.perimeter+r1.perimeter;}";
        init(txt);
        test.execute();
        assertEquals(18, test.getValue());
    }

    @Test
    public void parseRectangleFieldChange() throws Exception {
        String txt = "Rectangle main(){Rectangle r1(0,5,4,3);  " +
                "r1.width = 3; return r1;}";
        init(txt);
        test.execute();
        assertEquals(9, ((Rectangle) test.getValue()).getArea());
        assertEquals(12, ((Rectangle) test.getValue()).getPerimeter());
        assertEquals(0, ((Rectangle) test.getValue()).getX());
        assertEquals(5, ((Rectangle) test.getValue()).getY());
        assertEquals(3, ((Rectangle) test.getValue()).getWidth());
        assertEquals(3, ((Rectangle) test.getValue()).getLength());
    }

    @Test
    public void parseAddRectangles() throws Exception {
        String txt = "Rectangle main(){Rectangle r1(0,0,4,3);  " +
                "Rectangle r2(4,3,1,1); return r1+r2;}";
        init(txt);
        test.execute();
        assertEquals(20, ((Rectangle) test.getValue()).getArea());
        assertEquals(18, ((Rectangle) test.getValue()).getPerimeter());
        assertEquals(0, ((Rectangle) test.getValue()).getX());
        assertEquals(0, ((Rectangle) test.getValue()).getY());
        assertEquals(4, ((Rectangle) test.getValue()).getWidth());
        assertEquals(5, ((Rectangle) test.getValue()).getLength());
    }

    @Test
    public void parseScaleRectangles() throws Exception {
        String txt = "Rectangle main(){Rectangle r1(0,0,4,3);  " +
                "int a = 5; return a*r1;}";
        init(txt);
        test.execute();
        assertEquals(300, ((Rectangle) test.getValue()).getArea());
        assertEquals(70, ((Rectangle) test.getValue()).getPerimeter());
        assertEquals(0, ((Rectangle) test.getValue()).getX());
        assertEquals(0, ((Rectangle) test.getValue()).getY());
        assertEquals(20, ((Rectangle) test.getValue()).getWidth());
        assertEquals(15, ((Rectangle) test.getValue()).getLength());
    }

    @Test
    public void parseMultiplySeparableRectangles() throws Exception {
        String txt = "Rectangle main(){Rectangle r1(0,0,4,3);  " +
                "Rectangle r2(4,3,1,1); return r1*r2;}";
        init(txt);
        test.execute();
        assertEquals(0, ((Rectangle) test.getValue()).getArea());
        assertEquals(0, ((Rectangle) test.getValue()).getPerimeter());
        assertEquals(0, ((Rectangle) test.getValue()).getX());
        assertEquals(0, ((Rectangle) test.getValue()).getY());
        assertEquals(0, ((Rectangle) test.getValue()).getWidth());
        assertEquals(0, ((Rectangle) test.getValue()).getLength());
    }

    @Test
    public void parseMultiplyNonSeparableRectangles() throws Exception {
        String txt = "Rectangle main(){Rectangle r1(0,0,4,3);  " +
                "Rectangle r2(1,1,1,1); return r1*r2;}";
        init(txt);
        test.execute();
        assertEquals(1, ((Rectangle) test.getValue()).getArea());
        assertEquals(4, ((Rectangle) test.getValue()).getPerimeter());
        assertEquals(1, ((Rectangle) test.getValue()).getX());
        assertEquals(1, ((Rectangle) test.getValue()).getY());
        assertEquals(1, ((Rectangle) test.getValue()).getWidth());
        assertEquals(1, ((Rectangle) test.getValue()).getLength());
    }

    @Test
    public void parseCompareRectangles() throws Exception {
        String txt = "int main(){Rectangle r1(0,0,4,3);" +
                "Rectangle r2(4,3,1,1); if(r1>r2) then {return 1;} else {return 2;}}";
        init(txt);
        test.execute();
        assertEquals(1, test.getValue());
    }

    @Test
    public void parseCompareEqualRectangles() throws Exception {
        String txt = "int main(){Rectangle r1(0,0,4,3);" +
                "Rectangle r2(4,3,6,2); if(r1==r2) then {return 1;} else {return 2;}}";
        init(txt);
        test.execute();
        assertEquals(1, test.getValue());
    }

    @Test
    public void parseFunctionCall() throws Exception {
        String txt = "int add(int a, int b) {int c = a+b; return c;}" +
                " int main(){int c = 5; int d = 1; return add(c,d);}";
        init(txt);
        test.execute();
        assertEquals(6, test.getValue());
    }

    @Test
    public void parseInvalidFunctionDefinition() throws Exception {
        String txt = "int main(){return true;}";
        init(txt);
        expectedEx.expect(ExecutionException.class);
        test.execute();
    }

    @Test
    public void parseNotExistingFunctionCall() throws Exception {
        String txt = "int main(){return add(c,d);}";
        init(txt);
        expectedEx.expect(ExecutionException.class);
        test.execute();
    }

    @Test
    public void doubleReturnThrowsException() throws Exception {
        String txt = "int main(){return 5;return 8;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        expectedEx.expect(ParserException.class);
        test = parser.parse();
    }

    @Test
    public void commentsIgnore() throws Exception {
        String txt = "int main(){#comment \n return 1;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        test = parser.parse();
        test.execute();
        assertEquals(1, test.getValue());
    }

}