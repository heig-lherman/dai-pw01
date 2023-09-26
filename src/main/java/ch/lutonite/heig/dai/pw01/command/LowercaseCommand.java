package ch.lutonite.heig.dai.pw01.command;

import ch.lutonite.heig.dai.pw01.io.LowercaseWriter;
import java.io.Writer;
import java.util.Locale;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Command that makes every character in a text file lowercase.
 *
 * @author Loïc Herman
 */
@Command(
        name = "lowercase",
        description = "Make every character lowercase"
)
public class LowercaseCommand extends AbstractTextOperationCommand {

    @Option(
            names = {"-l", "--locale"},
            description = "locale to use for lowercase conversion",
            defaultValue = "fr-CH"
    )
    private String locale;

    /**
     * Returns a new filtered writer that copies every character
     * as its lowercase counterpart.
     *
     * @param outputWriter the source writer
     * @return the filtered writer
     * @see LowercaseWriter
     */
    @Override
    protected Writer processOutput(Writer outputWriter) {
        return new LowercaseWriter(outputWriter, Locale.forLanguageTag(locale));
    }
}
