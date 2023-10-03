package ch.lutonite.heig.dai.pw01.command;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Option;

/**
 * Abstract class for any operations based on files.
 *
 * @author Loïc Herman
 * @see AbstractBinaryOperationCommand
 * @see AbstractTextOperationCommand
 */
abstract class AbstractOperationCommand<I extends Closeable, O extends Closeable>
        implements Callable<Integer> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Option(
            names = {"-i", "--input-file"},
            description = "input file path",
            required = true
    )
    protected Path inputFilePath;

    @Option(
            names = {"-o", "--output-file"},
            description = "output file path",
            required = true
    )
    protected Path outputFilePath;

    /**
     * Opens the input stream for the operation.
     *
     * @return the input stream
     * @throws IOException if an I/O error occurs
     */
    protected abstract I openInput() throws IOException;

    /**
     * Opens the output stream for the operation.
     *
     * @return the output stream
     * @throws IOException if an I/O error occurs
     */
    protected abstract O openOutput() throws IOException;

    /**
     * Handles the actual operation on the input and output streams.
     *
     * @param inputStream  the input stream
     * @param outputStream the output stream
     * @throws IOException if an I/O error occurs
     */
    protected abstract void process(I inputStream, O outputStream) throws IOException;

    /**
     * Main entry point for any of the subcommands implementing this class.
     *
     * @return the program exit code. 0 if the operation was successful, 1 otherwise.
     */
    @Override
    public final Integer call() {
        log.info("Starting operation on file {} to {}", inputFilePath, outputFilePath);
        var start = System.currentTimeMillis();
        try (var is = openInput(); var os = openOutput()) {
            // it is assumed that the inner process will be buffered
            process(is, os);
            var end = System.currentTimeMillis();
            log.info("Operation completed in {} ms", end - start);
            return 0;
        } catch (IOException ioException) {
            log.error(
                    "Could not complete the operation due to an I/O exception: {} - {}",
                    ioException.getClass().getSimpleName(),
                    ioException.getMessage()
            );

            // we should resolve the stacktrace only if the debug level is enabled
            if (log.isDebugEnabled()) {
                log.debug("Stacktrace:", ioException);
            }

            return 1;
        }
    }
}
