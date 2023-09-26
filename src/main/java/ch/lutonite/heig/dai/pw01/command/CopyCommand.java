package ch.lutonite.heig.dai.pw01.command;

import java.io.OutputStream;
import picocli.CommandLine.Command;

/**
 * Command that copies a text file to another.
 *
 * @author Loïc Herman
 */
@Command(
        name = "copy",
        description = "Copy a file to another"
)
public class CopyCommand extends AbstractBinaryOperationCommand {

    /**
     * Copy the input file to the output file.
     * This is essentially a no-op, but it is useful for testing the CLI.
     *
     * @param outputStream the output file stream
     * @return the filtered output file stream, in this case a no-op
     */
    @Override
    protected OutputStream processOutput(OutputStream outputStream) {
        return outputStream;
    }
}
