package tkom.test;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import tkom.parser.Parser;
import tkom.parser.Program;
import tkom.parser.types.Rectangle;
import tkom.scanner.Scanner;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by karolina on 26.04.18.
 */
public class ParserTest {


    public void parseEmptyString() throws Exception {

        String txt = "";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        try {
            test.execute();
        } catch (Exception e) {
            assertEquals("main not found", e.getMessage());
        }
    }

    @Test
    public void parseIntInitialization() throws Exception {
        String txt = "int main(){int a = 5; return a;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(5, test.getValue());
    }

    @Test
    public void parseStringInitialization() throws Exception {
        String txt = "string main(){string a = \"test\"; return a;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals("test", test.getValue());
    }

    @Test
    public void parseBoolInitialization() throws Exception {
        String txt = "bool main(){bool a = false; return a;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(false, test.getValue());
    }

    @Test
    public void parseRectangleInitialization() throws Exception {

        String txt = "Rectangle main(){Rectangle a(1,1,1,1); return a;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
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
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program program = parser.parse();
        program.execute();
        assertEquals(true, program.getValue());
    }

    @Test
    public void parseNegatedExpression() throws Exception {
        String txt = "int main(){int a = -5; return a;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program program = parser.parse();
        program.execute();
        assertEquals(-5, program.getValue());
    }

    @Test
    public void parseMathExpression() throws Exception {
        String txt = "int main(){int a = 2+4*(3-1); return a;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program program = parser.parse();
        program.execute();
        assertEquals(10, program.getValue());
    }

    @Test
    public void parseIfStatement() throws Exception {
        String txt = "int main(){int c = 4; if(c < 10) then {c = c+5;} return c;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(9, test.getValue());
    }

    @Test
    public void parseIfElseStatement() throws Exception {
        String txt = "int main(){int c = 4; if(c > 10) then {c = c+5;} else {c = 1;}return c;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(1, test.getValue());
    }

    @Test
    public void parseWhileStatement() throws Exception {
        String txt = "int main(){int c=1; int a=1; while (c<3) {c=c+1; a = a-1;} return a;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(-1, test.getValue());
    }

    @Test
    public void parsePrint() throws Exception {
        String txt = "int main(){int c = 1; print(c); return c;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
    }

    @Test
    public void parsePrintTxt() throws Exception {
        String txt = "int main(){int c = 1; print(\"test\"); return c;}";

        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
    }

    @Test
    public void parseRectangle() throws Exception {
        String txt = "int main(){Rectangle r1(0,5,4,3); Rectangle r2(1,1,1,1); print(r1.area); " +
                "return r2.perimeter+r1.perimeter;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(18, test.getValue());
    }

    @Test
    public void parseFunctionCall() throws Exception {
        String txt = "int add(int a, int b) {int c = a+b; return c;}" +
                " int main(){int c = 5; int d = 1; return add(c,d);}";

        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(6, test.getValue());
    }


}