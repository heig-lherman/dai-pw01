package ch.lutonite.heig.dai.pw01.command;

import java.io.OutputStream;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.BaseNCodecOutputStream;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Command that encodes a file as base64.
 *
 * @author Loïc Herman
 */
@Command(
        name = "base64",
        description = "Encode or decode a file as base64"
)
public class Base64Command extends AbstractBinaryOperationCommand {

    @Option(
            names = {"-e", "--encode"},
            description = "whether to decode the input file"
    )
    private boolean doEncode;

    @Option(
            names = {"--url-safe"},
            description = "whether to use the URL-safe encoding",
            defaultValue = "false"
    )
    private boolean useUrlSafe;

    /**
     * Transform the input file as base64 encoded data in the output file.
     *
     * @param outputStream the output file stream
     * @return the filtered output file stream, which transforms the data as base64
     */
    @Override
    protected OutputStream processOutput(OutputStream outputStream) {
        return new BaseNCodecOutputStream(outputStream, new Base64(useUrlSafe), doEncode);
    }
}
