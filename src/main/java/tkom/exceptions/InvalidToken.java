package tkom.exceptions;

import tkom.source.TextPos;

/**
 * Created by karolina on 15.04.18.
 */
public class InvalidToken extends Exception {

    private TextPos pos;

    public InvalidToken(TextPos pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return ("Token not recognized at " + pos);
    }
}
