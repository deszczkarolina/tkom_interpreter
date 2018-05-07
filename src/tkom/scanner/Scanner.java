package tkom.scanner;

import tkom.exceptions.IdentTooLongException;
import tkom.exceptions.IntTooBigException;
import tkom.exceptions.InvalidToken;
import tkom.exceptions.StringTooLongException;
import tkom.source.Source;
import tkom.source.TextPos;

import java.io.IOException;
import java.io.InputStream;
import java.lang.String;
import java.util.HashMap;

import static java.lang.Character.*;
import static tkom.scanner.TokenType.*;
import static tkom.scanner.TokenType.String;
import static tkom.source.Source.NEWLINE;

/**
 * Created by karolina on 10.04.18.
 */

public class Scanner {

    public final static HashMap<String, TokenType> symbolTable;
    public final static HashMap<Character, TokenType> operators;
    public final static int MAXIDLEN = 20;
    public final static int MAXTXTLEN = 40;

    static {
        symbolTable = new HashMap<>();
        symbolTable.put("if", If);
        symbolTable.put("then", Then);
        symbolTable.put("else", Else);
        symbolTable.put("while", While);
        symbolTable.put("return", Return);
        symbolTable.put("int", Int);
        symbolTable.put("string", String);
        symbolTable.put("bool", Bool);
        symbolTable.put("true", True);
        symbolTable.put("false", False);
        symbolTable.put("Rectangle", Rectangle);
        symbolTable.put("print", Print);
        symbolTable.put("x", RectX);
        symbolTable.put("y", RectY);
        symbolTable.put("width", RectWidth);
        symbolTable.put("length", RectLength);
        symbolTable.put("area", RectArea);
        symbolTable.put("perimeter", RectPermimeter);

        operators = new HashMap<>();

        operators.put('+', addop);
        operators.put('-', subop);
        operators.put('*', mulop);
        operators.put('/', divop);

        operators.put('.', dot);
        operators.put(',', coma);
        operators.put(';', semicolon);
        operators.put('(', lbracket);
        operators.put(')', rbracket);
        operators.put('{', lcurlybracket);
        operators.put('}', rcurlybracket);
    }

    private Source src;
    private int currentChar;
    private TextPos tokenPos;
    private int constint;
    private StringBuilder id;

    public Scanner(InputStream istream) throws IOException {
        src = new Source(istream);
        currentChar = src.nextChar();
    }

    private void nextChar() throws IOException {
        currentChar = src.nextChar();
    }

    private boolean skipWhiteSpace() throws IOException {
        do {
            if (currentChar == Source.EOF) return false;
            while (isWhitespace(currentChar)) //skip whitespaces
                nextChar();
            if (currentChar == '#') {
                do nextChar();
                while (currentChar != NEWLINE);
                nextChar();
            }
        } while (isWhitespace(currentChar) || currentChar == '#' || currentChar == Source.EOF);
        return true;
    }

    private TokenType identOrKeywordToken() throws IOException, IdentTooLongException, InvalidToken {
        id = new StringBuilder();
        do {
            if (id.length() <= MAXIDLEN)
                id.append((char) currentChar);
            else throw new IdentTooLongException(tokenPos);
            nextChar();
        } while (isLetterOrDigit(currentChar));
        if (symbolTable.containsKey(id.toString()))
            return symbolTable.get(id.toString());
        else return ident;
    }

    private TokenType operatorToken() throws IOException, StringTooLongException, InvalidToken {
        switch (currentChar) {
            case '"':
                return textToken();
            case '!':
                nextChar();
                if (currentChar == '=') {
                    nextChar();
                    return neqop;
                } else return notop;
            case '&':
                nextChar();
                if (currentChar == '&') {
                    nextChar();
                    return andop;
                } else throw new InvalidToken(tokenPos);
            case '|':
                nextChar();
                if (currentChar == '|') {
                    nextChar();
                    return orop;
                } else throw new InvalidToken(tokenPos);
            case '=':
                nextChar();
                if (currentChar == '=') {
                    nextChar();
                    return eqop;
                } else return assignop;
            case '<':
                nextChar();
                if (currentChar == '=') {
                    nextChar();
                    return leop;
                } else return ltop;
            case '>':
                nextChar();
                if (currentChar == '=') {
                    nextChar();
                    return geop;
                } else return gtop;

            default:
                if (operators.containsKey((char) currentChar)) {
                    int tmp = currentChar;
                    nextChar();
                    return operators.get((char) tmp);
                } else throw new InvalidToken(tokenPos);
        }
    }

    private TokenType intConstToken() throws IntTooBigException, IOException {
        long c = 0;
        boolean isTooBig = false;
        do {
            c = c * 10 + (currentChar - '0');
            if (c > Integer.MAX_VALUE) isTooBig = true;
            nextChar();
        } while (isDigit(currentChar));

        if (isTooBig) throw new IntTooBigException(tokenPos);
        constint = Math.toIntExact(c);
        return intconst;
    }

    private TokenType textToken() throws IOException, StringTooLongException {
        id = new StringBuilder();
        nextChar();
        do {
            if (id.length() <= MAXTXTLEN)
                id.append((char) currentChar);
            else throw new StringTooLongException(tokenPos);
            nextChar();
        } while (currentChar != '"');
        nextChar();
        return textconst;
    }

    public TokenType nextToken() throws IdentTooLongException, InvalidToken, IOException, IntTooBigException, StringTooLongException {

        if (!skipWhiteSpace())
            return EOF;

        tokenPos = src.getPos();
        if (isLetter(currentChar)) {
            return identOrKeywordToken();
        }
        if (isDigit(currentChar))
            return intConstToken();

        return operatorToken();
    }

    public int getConstint() {
        return constint;
    }

    public String getIdent() {
        return id.toString();
    }

    public TextPos getTokenPos() {
        return tokenPos;
    }

}



