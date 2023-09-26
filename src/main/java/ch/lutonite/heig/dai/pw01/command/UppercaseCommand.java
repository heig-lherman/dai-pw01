package ch.lutonite.heig.dai.pw01.command;

import ch.lutonite.heig.dai.pw01.io.UppercaseWriter;
import java.io.Writer;
import java.util.Locale;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Command that makes every character in a text file uppercase.
 *
 * @author Loïc Herman
 */
@Command(
        name = "uppercase",
        description = "Make every character uppercase"
)
public class UppercaseCommand extends AbstractTextOperationCommand {

    @Option(
            names = {"-l", "--locale"},
            description = "locale to use for uppercase conversion",
            defaultValue = "fr-CH"
    )
    private String locale;

    /**
     * Returns a new filtered writer that copies every character
     * as its uppercase counterpart.
     *
     * @param outputWriter the source writer
     * @return the filtered writer
     * @see UppercaseWriter
     */
    @Override
    protected Writer processOutput(Writer outputWriter) {
        return new UppercaseWriter(outputWriter, Locale.forLanguageTag(locale));
    }
}
