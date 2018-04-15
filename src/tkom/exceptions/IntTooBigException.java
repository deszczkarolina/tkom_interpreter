package tkom.exceptions;

import tkom.source.TextPos;

/**
 * Created by karolina on 13.04.18.
 */
public class IntTooBigException extends Exception {
    TextPos pos;

    public IntTooBigException(TextPos pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return ("Error while scanning at position: " + pos);
    }
}