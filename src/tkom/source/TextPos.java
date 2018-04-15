package tkom.source;

/**
 * Created by karolina on 10.04.18.
 */

public class TextPos {

    public int lineIx;
    public int charIx;

    public TextPos() {
        lineIx = 1;
        charIx = 0;
    }

    public TextPos(int lineIx, int charIx) {
        this.lineIx = lineIx;
        this.charIx = charIx;
    }

    @Override
    public String toString() {
        return ("line: " + lineIx + " character: " + charIx);
    }
}