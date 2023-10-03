package ch.lutonite.heig.dai.pw01.command;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import picocli.CommandLine.Help.Visibility;
import picocli.CommandLine.Option;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * Abstract class that manages parsing the provided arguments and options for every
 * input-to-output text file operation that is handled through the CLI.
 *
 * @author Loïc Herman
 */
abstract class AbstractTextOperationCommand extends AbstractOperationCommand<Reader, Writer> {

    @Option(
            names = {"--input-encoding"},
            description = "input file encoding",
            defaultValue = "UTF-8",
            showDefaultValue = Visibility.ALWAYS
    )
    private Charset inputEncoding;

    @Option(
            names = {"--output-encoding"},
            description = "output file encoding",
            defaultValue = "UTF-8",
            showDefaultValue = Visibility.ALWAYS
    )
    private Charset outputEncoding;

    /**
     * Allows to apply a filter on the default output writer for additional processing.
     *
     * @param outputWriter the default output writer
     * @return a writer (with optionally a filter applied)
     */
    protected abstract Writer processOutput(Writer outputWriter);

    /**
     * Opens the input reader for the operation.
     *
     * @return the input reader
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected Reader openInput() throws IOException {
        return Files.newBufferedReader(inputFilePath, inputEncoding);
    }

    /**
     * Opens the output writer for the operation, applying the filter.
     *
     * @return the output writer
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected Writer openOutput() throws IOException {
        return processOutput(Files.newBufferedWriter(
                outputFilePath,
                outputEncoding,
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
    protected void process(
            Reader inputStream,
            Writer outputStream
    ) throws IOException {
        inputStream.transferTo(outputStream);
    }
}
