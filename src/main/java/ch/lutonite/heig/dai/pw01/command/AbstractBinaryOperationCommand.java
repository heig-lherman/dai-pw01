package ch.lutonite.heig.dai.pw01.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
abstract class AbstractBinaryOperationCommand
        extends AbstractOperationCommand<InputStream, OutputStream> {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * Allows to apply a filter on the default output stream for additional processing.
     *
     * @param outputStream the default output stream
     * @return an output stream (with optionally a filter applied)
     */
    protected abstract OutputStream processOutput(OutputStream outputStream);

    /**
     * Opens the input stream for the operation.
     *
     * @return the input stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected final InputStream openInput() throws IOException {
        return Files.newInputStream(
                inputFilePath,
                READ
        );
    }

    /**
     * Opens the output stream for the operation, applying the filter.
     *
     * @return the output stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected final OutputStream openOutput() throws IOException {
        return processOutput(Files.newOutputStream(
                outputFilePath,
                CREATE, TRUNCATE_EXISTING, WRITE
        ));
    }

    /**
     * Copies the input stream to the output stream.
     *
     * @param inputStream  the input stream
     * @param outputStream the output stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected final void process(
            InputStream inputStream,
            OutputStream outputStream
    ) throws IOException {
        inputStream.transferTo(outputStream);
    }
}
