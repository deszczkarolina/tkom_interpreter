package tkom.test;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import tkom.scanner.Scanner;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static tkom.scanner.TokenType.*;
import static tkom.scanner.TokenType.String;

import java.lang.String;

/**
 * Created by karolina on 15.04.18.
 */
public class ScannerTest {

    @Test
    public void emptyString() throws Exception {

        String txt = "";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        assertEquals(EOF, scn.nextToken());
    }

    @Test
    public void whitespacesOnly() throws Exception {
        String txt = "\n \n";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        assertEquals(EOF, scn.nextToken());
    }

    @Test
    public void keywords() throws Exception {
        String txt = "if then else while return int string bool " +
                "Rectangle print r.x r.y r.width r.length r.area r.perimeter";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        assertEquals(If, scn.nextToken());
        assertEquals(Then, scn.nextToken());
        assertEquals(Else, scn.nextToken());
        assertEquals(While, scn.nextToken());
        assertEquals(Return, scn.nextToken());
        assertEquals(Int, scn.nextToken());
        assertEquals(String, scn.nextToken());
        assertEquals(Bool, scn.nextToken());
        assertEquals(Rectangle, scn.nextToken());
        assertEquals(Print, scn.nextToken());
        assertEquals(Rect_x, scn.nextToken());
        assertEquals(Rect_y, scn.nextToken());
        assertEquals(Rect_width, scn.nextToken());
        assertEquals(Rect_length, scn.nextToken());
        assertEquals(Rect_area, scn.nextToken());
        assertEquals(Rect_permimeter, scn.nextToken());
    }

    @Test
    public void ident() throws Exception {
        String txt = "abc line3";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        assertEquals(ident, scn.nextToken());
        assertEquals(ident, scn.nextToken());
        assertEquals(EOF, scn.nextToken());
    }

    @Test
    public void textConst() throws Exception {
        String txt = " \"abc\" ";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        assertEquals(textconst, scn.nextToken());
        assertEquals("abc", scn.getIdent());
        assertEquals(EOF, scn.nextToken());
    }

    @Test
    public void numbers() throws Exception {
        String txt = "124 0";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        assertEquals(intconst, scn.nextToken());
        assertEquals(intconst, scn.nextToken());
        assertEquals(EOF, scn.nextToken());
    }

    @Test
    public void mathOperators() throws Exception {
        String txt = "+ - * / = ";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        assertEquals(addop, scn.nextToken());
        assertEquals(subop, scn.nextToken());
        assertEquals(mulop, scn.nextToken());
        assertEquals(divop, scn.nextToken());
        assertEquals(assignop, scn.nextToken());
        assertEquals(EOF, scn.nextToken());
    }

    @Test
    public void logOperators() throws Exception {
        String txt = "== != && || ! < <= > >= ";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        assertEquals(eqop, scn.nextToken());
        assertEquals(neqop, scn.nextToken());
        assertEquals(andop, scn.nextToken());
        assertEquals(orop, scn.nextToken());
        assertEquals(notop, scn.nextToken());
        assertEquals(ltop, scn.nextToken());
        assertEquals(leop, scn.nextToken());
        assertEquals(gtop, scn.nextToken());
        assertEquals(geop, scn.nextToken());
        assertEquals(EOF, scn.nextToken());
    }

    @Test
    public void specialCharacter() throws Exception {
        String txt = "{ } ( ) . , ;";
        InputStream in = IOUtils.toInputStream(txt, "UTF-8");
        Scanner scn = new Scanner(in);
        assertEquals(lcurlybracket, scn.nextToken());
        assertEquals(rcurlybracket, scn.nextToken());
        assertEquals(lbracket, scn.nextToken());
        assertEquals(rbracket, scn.nextToken());
        assertEquals(dot, scn.nextToken());
        assertEquals(coma, scn.nextToken());
        assertEquals(semicolon, scn.nextToken());
        assertEquals(EOF, scn.nextToken());
    }
}