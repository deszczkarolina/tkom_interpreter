package tkom.test;

import org.junit.Test;
import tkom.source.TextPos;

import static org.junit.Assert.assertEquals;

/**
 * Created by karolina on 13.04.18.
 */
public class TextPosTest {

    @Test
    public void TextPosInitialization() {
        TextPos pos = new TextPos();
        assertEquals(0, pos.charIx);
        assertEquals(1, pos.lineIx);
    }

    @Test
    public void TextPosToString() {
        TextPos pos = new TextPos();
        assertEquals("line: 1 character: 0", pos.toString());

    }

}