package tkom.exceptions;

import tkom.source.TextPos;

/**
 * Created by karolina on 15.04.18.
 */
public class StringTooLongException extends Exception {
    TextPos pos;

    public StringTooLongException(TextPos pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return ("Error while scanning at position: " + pos);
    }
}