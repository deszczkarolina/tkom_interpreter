package tkom.exceptions;

import tkom.scanner.TokenType;
import tkom.source.TextPos;

import java.util.Vector;

/**
 * Created by karolina on 25.05.18.
 */
public class ParserException extends Exception {

    private TokenType currentToken;
    private Vector<TokenType> expected;
    private TextPos pos;
    private String msg;

    public ParserException(TokenType currentToken, TokenType expected, TextPos pos) {
        this.currentToken = currentToken;
        this.expected = new Vector<>();
        this.expected.add(expected);
        this.pos = pos;
        this.msg = ("unexpected token " + currentToken +
                " expected tokens: " + expected.toString() +
                " at position " + pos.toString());
    }

    public ParserException(String msg) {
        this.msg = msg;
    }

    public ParserException(TokenType currentToken, Vector<TokenType> expected, TextPos pos) {
        this.currentToken = currentToken;
        this.expected = new Vector<>();
        for (TokenType token : expected) {
            this.expected.add(token);
        }
        this.pos = pos;
        this.msg = ("unexpected token " + currentToken +
                " expected tokens: " + expected.toString().replace("[", "").replace("]", "")
                + " at position " + pos.toString());
    }

    @Override
    public String toString() {
        return msg;
    }
}
