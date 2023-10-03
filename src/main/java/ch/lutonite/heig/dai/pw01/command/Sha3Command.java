package ch.lutonite.heig.dai.pw01.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import org.apache.commons.codec.digest.DigestUtils;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help.Visibility;
import picocli.CommandLine.Option;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.READ;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

/**
 * Command that hashes a file as sha3-512.
 *
 * @author Loïc Herman
 * @implNote This command takes a input stream that will be read through buffers, updating
 *         the hash as the data is read. This is done to avoid loading the whole file in memory.
 *         The hash is then written as a hexadecimal string to the output file.
 */
@Command(
        name = "sha3",
        description = "Hash a file as sha3-512"
)
public class Sha3Command extends AbstractOperationCommand<InputStream, Writer> {

    @Option(
            names = {"--output-encoding"},
            description = "output file encoding",
            defaultValue = "UTF-8",
            showDefaultValue = Visibility.ALWAYS
    )
    private Charset outputEncoding;

    @Override
    protected final InputStream openInput() throws IOException {
        return Files.newInputStream(inputFilePath, READ);
    }

    @Override
    protected final Writer openOutput() throws IOException {
        return Files.newBufferedWriter(
                outputFilePath,
                outputEncoding,
                CREATE, TRUNCATE_EXISTING, WRITE
        );
    }

    /**
     * Hashes the input stream as sha3-512 and writes the result to the output stream.
     *
     * @param inputStream  the input stream
     * @param outputStream the output stream
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void process(
            InputStream inputStream,
            Writer outputStream
    ) throws IOException {
        outputStream.write(DigestUtils.sha3_512Hex(inputStream));
    }
}
