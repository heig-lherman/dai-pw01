package ch.lutonite.heig.dai.pw01.command;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import picocli.CommandLine.Option;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * Abstract class that manages parsing the provided arguments and options for every
 * input-to-output file operation that is handled through the CLI.
 *
 * @author Loïc Herman
 */
abstract class AbstractBinaryOperationCommand implements Callable<Integer> {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Option(
            names = {"-i", "--input-file"},
            description = "input file path"
    )
    private Path inputFilePath;

    @Option(
            names = {"-o", "--output-file"},
            description = "output file path"
    )
    private Path outputFilePath;

    /**
     * Allows to apply a filter on the default output stream for additional processing.
     *
     * @param outputStream the default output stream
     * @return an output stream (with optionally a filter applied)
     */
    protected abstract OutputStream processOutput(OutputStream outputStream);

    /**
     * Main entry point for any of the subcommands implementing this class.
     *
     * @return the program exit code. 0 if the operation was successful, 1 otherwise.
     */
    @Override
    public final Integer call() {
        log.info("Starting operation on file {} to {}", inputFilePath, outputFilePath);
        var start = System.currentTimeMillis();
        try (
                // note: streams below are not buffered, since transferTo implicitly
                // creates a buffer to copy the data
                var reader = Files.newInputStream(
                        inputFilePath,
                        READ
                );
                var writer = Files.newOutputStream(
                        outputFilePath,
                        CREATE, WRITE, TRUNCATE_EXISTING
                );
        ) {
            reader.transferTo(processOutput(writer));
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
