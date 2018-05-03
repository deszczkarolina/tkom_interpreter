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
    Rect_x,
    Rect_y,
    Rect_width,
    Rect_length,
    Rect_area,
    Rect_permimeter,

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
