package tkom.source;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by karolina on 10.04.18.
 */
public class Source {

    public static final int EOF = -1;
    public static final int NEWLINE = 10;

    private TextPos pos;
    private InputStream istream;

    public Source(InputStream istream) {
        pos = new TextPos();
        this.istream = istream;
    }

    public int nextChar() throws IOException {
        int c;
        if ((c = istream.read()) != EOF) {
            if (c == NEWLINE) {
                pos.charIx = 0;
                pos.lineIx++;
            } else pos.charIx++;
            return c;
        } else istream.close();
        return EOF;
    }

    public TextPos getPos() {
        return pos;
    }
}