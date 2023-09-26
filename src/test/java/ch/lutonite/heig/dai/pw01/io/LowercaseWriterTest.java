package ch.lutonite.heig.dai.pw01.io;

import java.io.CharArrayWriter;
import java.io.Writer;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LowercaseWriterTest {

    private Writer output;
    private LowercaseWriter rootLowercase;

    @BeforeEach
    void setup() {
        output = new CharArrayWriter();
        rootLowercase = new LowercaseWriter(output, Locale.ROOT);
    }

    @Test
    void writing_rootLocale() throws Exception {
        rootLowercase.write("Hello World!", 0, 12);
        rootLowercase.flush();

        assertThat(output).extracting(Writer::toString).isEqualTo("hello world!");
    }

    @Test
    void writing_shouldHandleLocaleCharacters() throws Exception {
        rootLowercase.write('Ü');
        rootLowercase.flush();

        assertThat(output).extracting(Writer::toString).isEqualTo("ü");
    }
}
