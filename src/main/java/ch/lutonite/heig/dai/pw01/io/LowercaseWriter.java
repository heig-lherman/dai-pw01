package ch.lutonite.heig.dai.pw01.io;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Locale;

/**
 * A writer that converts all characters to lowercase.
 */
public class LowercaseWriter extends FilterWriter {

    private final Locale locale;

    /**
     * Create a new lowercase writer.
     *
     * @param out    the writer to wrap
     * @param locale the locale to use for char conversion
     */
    public LowercaseWriter(Writer out, Locale locale) {
        super(out);
        this.locale = locale;
    }

    /**
     * Convert the character to lowercase and write it.
     * {@inheritDoc}
     */
    @Override
    public void write(int c) throws IOException {
        super.write(String.valueOf((char) c).toLowerCase(locale));
    }

    /**
     * Convert the characters to lowercase and write them.
     * {@inheritDoc}
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        super.write(String.valueOf(cbuf).toLowerCase(locale), off, len);
    }

    /**
     * Convert the character sequence to lowercase and write them.
     * {@inheritDoc}
     */
    @Override
    public void write(String str, int off, int len) throws IOException {
        super.write(str.toLowerCase(locale), off, len);
    }
}
