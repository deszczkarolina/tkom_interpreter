package tkom.scanner;

/**
 * Created by karolina on 10.04.18.
 */
public enum TokenType {
    ident,
    textconst,
    intconst,

    If,
    Then,
    Else,
    While,
    Return,
    Int,
    String,
    Bool,
    True,
    False,
    Rectangle,
    Print,
    RectX,
    RectY,
    RectWidth,
    RectLength,
    RectArea,
    RectPermimeter,

    notop,
    andop,
    orop,

    assignop,
    addop,
    subop,
    mulop,
    divop,

    neqop,
    eqop,
    ltop,
    leop,
    gtop,
    geop,

    lbracket,
    rbracket,
    lcurlybracket,
    rcurlybracket,

    coma,
    dot,
    semicolon,
    EOF,
    TOKEN_MAX,
}
