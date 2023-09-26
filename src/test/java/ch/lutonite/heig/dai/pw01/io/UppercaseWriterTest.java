package ch.lutonite.heig.dai.pw01.io;

import java.io.CharArrayWriter;
import java.io.Writer;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UppercaseWriterTest {

    private Writer output;
    private UppercaseWriter rootUppercase;

    @BeforeEach
    void setup() {
        output = new CharArrayWriter();
        rootUppercase = new UppercaseWriter(output, Locale.ROOT);
    }

    @Test
    void writing_rootLocale() throws Exception {
        rootUppercase.write("Hello World!", 0, 12);
        rootUppercase.flush();

        assertThat(output).extracting(Writer::toString).isEqualTo("HELLO WORLD!");
    }

    @Test
    void writing_shouldHandleLocaleCharacters() throws Exception {
        rootUppercase.write('ü');
        rootUppercase.flush();

        assertThat(output).extracting(Writer::toString).isEqualTo("Ü");
    }
}
