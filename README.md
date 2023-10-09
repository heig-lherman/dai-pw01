# Practical Work 1 - IoProcessing CLI

This is a CLI that allows you to apply some operations on text and binary files.

## Dependencies

### commons-codec

To simplify the processing of encoding files as base64, we are using the `commons-codec` utility
library from Apache which provides a simple API to encode and decode data in various formats.

## Build the JAR

To build the JAR, run the following command from the root of the repository:

```shell
./mvnw clean package
```

## Running

To run the JAR, run the following command from the root of the repository:

```shell
java -jar target/pw-cli-ioprocessing-1.0.0-SNAPSHOT.jar <command>
```

## Usage

The CLI methods are documented in the form of usage messages, given using the `--help` flag.

```shell
java -jar target/pw-cli-ioprocessing-1.0.0-SNAPSHOT.jar --help
```

### Commands

The CLI supports the following commands:

- `copy`: Copies a binary file to another.
- `lowercase`: Transforms a text file, making it all lowercase.
- `uppercase`: Transforms a text file, making it all uppercase.
- `base64`: Encodes and decodes any file to and from the base64 format.
- `sha3`: Computes the SHA3 hash of a file and prints it in the output file.

### Testing

For testing, a couple of files are given in `src/test/resources/test-files`.

For example, the copy operation can be done that way (from the current directory):

```shell
java -jar target/pw-cli-ioprocessing-1.0.0-SNAPSHOT.jar copy \
    -i src/test/resources/test-files/utf8.txt \
    -o target/copy.txt
```

Which outputs the following:

```shell
❯ java -jar target/pw-cli-ioprocessing-1.0.0-SNAPSHOT.jar copy -i src/test/resources/test-files/uppercase-long.txt -o target/copy.txt
17:11:22.553 [main] INFO ch.lutonite.heig.dai.pw01.command.CopyCommand -- Starting operation on file src/test/resources/test-files/utf8.txt to target/copy.txt
17:11:22.578 [main] INFO ch.lutonite.heig.dai.pw01.command.CopyCommand -- Operation completed in 23 ms
```

And, as expected, the contents of `target/copy.txt` are identical to `src/test/resources/test-files/uppercase-long.txt`.

#### Encoding

For text operations (lowercase and uppercase, for example), two more arguments are available (but optional).

- `--input-encoding` allows to define the input encoding
- `--output-encoding` allows to define the output encoding

> **Warning**
> If there is a mismatch between the actual file encoding and the given input/output encoding, the CLI
> will return an error message. This CLI does not handle conversion between character sets, unless they 
> are strictly compatible (e.g `US-ASCII` is a subset of `UTF-8`).
