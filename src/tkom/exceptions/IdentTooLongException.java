package tkom.exceptions;

import tkom.source.TextPos;

/**
 * Created by karolina on 13.04.18.
 */
public class IdentTooLongException extends Exception {
    TextPos pos;

    public IdentTooLongException(TextPos pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return ("Error while scanning at position: " + pos);
    }
}

