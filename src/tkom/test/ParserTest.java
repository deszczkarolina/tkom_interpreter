package tkom.test;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import tkom.parser.*;
import tkom.parser.types.Number;
import tkom.parser.conditions.Condition;
import tkom.parser.expressions.AddExpression;
import tkom.parser.expressions.Expression;
import tkom.parser.statements.VarDeclarationStatement;
import tkom.parser.types.Text;
import tkom.scanner.Scanner;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by karolina on 26.04.18.
 */
public class ParserTest {


    @Test
    public void parseNumber() throws Exception {

        String txt = "123";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);

    }

    @Test
    public void parseText() throws Exception {

        String txt = "\" text \"";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);

    }

    @Test
    public void parseLogicalValue() throws Exception {

        String txt = "true";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);

    }

    @Test
    public void parseIntDeclarationStatement() throws Exception {
        String txt = "int a;";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);

    }

    @Test
    public void parseStringDeclarationWithInitialization() throws Exception {
        String txt = "string test = \"abc\";";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);

    }
    @Test
    public void parseBooleanDeclarationStatementWithInitialization() throws Exception {
        String txt = "bool test = false;";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);

    }
    @Test
    public void parseBooleanAssignment() throws Exception {
        String txt = "bool main(){bool test = false; test = true; return test;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program program = parser.parse();
        program.execute();
        assertEquals(true,program.getValue());
    }

    @Test
    public void parseSimplestExpression() throws Exception {
        String txt = "14";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);

    }
    @Test
    public void parseNegatedExpression() throws Exception {
        String txt = "-14";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);

    }



    @Test
    public void parseSimpleCondition() throws Exception {
        String txt = "5 < 6";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);

    }



    public void parsBlockStatement() throws Exception {
        String txt = "{int a; a = 5+3;}";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
    }

    @Test
    public void parseExpressionValue() throws Exception {
        String txt = "a+b";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
    }

    @Test
    public void parseExecuteExpression() throws Exception {
        String txt = "int main(){int c = 8*2; return c;}";

        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(16, test.getValue());
    }
    @Test
    public void parseIfConditionExpression() throws Exception {
        String txt = "int main(){int c = 4; if(1!= 4) then {c = c+5;} return c;}";

        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(9, test.getValue());
    }


    @Test
    public void parseWhile() throws Exception {
        String txt = "int main(){int c = 1; while (c < 3) {c = c*(1+2);} return c;}";

        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(3, test.getValue());
    }
    @Test
    public void parsePrint() throws Exception {
        String txt = "int main(){int c = 1; print(c); return c;}";

        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(1, test.getValue());
    }
    @Test
    public void parsePrintTxt() throws Exception {
        String txt = "int main(){int c = 1; print(\"mama\"); return c;}";

        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(1, test.getValue());
    }
    @Test
    public void parseRectangle() throws Exception {
        String txt = "Rectangle main(){Rectangle r1(0,5,4,3); Rectangle r2(1,1,1,1); print(r1.area); return r2;}";

        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        Parser parser = new Parser(scn);
        Program test = parser.parse();
        test.execute();
        assertEquals(1, test.getValue());
    }

}