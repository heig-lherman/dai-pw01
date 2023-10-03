package ch.lutonite.heig.dai.pw01;

import ch.lutonite.heig.dai.pw01.command.Base64Command;
import ch.lutonite.heig.dai.pw01.command.CopyCommand;
import ch.lutonite.heig.dai.pw01.command.LowercaseCommand;
import ch.lutonite.heig.dai.pw01.command.Sha3Command;
import ch.lutonite.heig.dai.pw01.command.UppercaseCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.HelpCommand;

/**
 * Main CLI class for the text file operations.
 *
 * @author Loïc Herman
 * @see CopyCommand
 * @see LowercaseCommand
 * @see UppercaseCommand
 * @see Base64Command
 * @see Sha3Command
 */
@Command(
        name = "IoProcessing",
        description = "Apply operations on files",
        version = "IoProcessing 1.0.0",
        mixinStandardHelpOptions = true,
        subcommands = {
                HelpCommand.class,
                CopyCommand.class,
                LowercaseCommand.class,
                UppercaseCommand.class,
                Base64Command.class,
                Sha3Command.class,
        }
)
public class IoProcessingCli implements Runnable {

    /**
     * Print basic usage information when the user doesn't use
     * one of the built-in subcommands.
     */
    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }

    /**
     * Main entry point for the CLI.
     *
     * @param args arguments that will be parsed by picocli
     */
    public static void main(String[] args) {
        int exitCode = new CommandLine(new IoProcessingCli()).execute(args);
        System.exit(exitCode);
    }
}
