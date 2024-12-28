import java.io.*;
import java.util.*;
import java.nio.file.*;

public class TextDecompression {
    public static void main(String[] args) {
        try {
            // Hardcoded file paths
            String compressedPath = "test1.txt.compressed";
            String encodingPath = "test1.txt.encoding";
            String outputPath = "test1_decompressed.txt";

            // Read the encoding map
            TextHashmap<Integer, String> encodingMap = new TextHashmap<>(256);
            TextHashmap<String, Integer> reverseMap = new TextHashmap<>(256);

            // Read and parse the encoding file
            List<String> encodingLines = Files.readAllLines(Paths.get(encodingPath));
            for (String line : encodingLines) {
                String[] parts = line.split(":");
                Integer character = Integer.parseInt(parts[0]);
                String code = parts[1];
                encodingMap.insert(character, code);
                reverseMap.insert(code, character);
            }

            // Read the compressed file bit by bit
            BitInputStream in = new BitInputStream(new FileInputStream(compressedPath));
            StringBuilder currentCode = new StringBuilder();
            StringBuilder decodedText = new StringBuilder();

            int bit;
            while ((bit = in.readBit()) != -1) {
                currentCode.append(bit == 1 ? '1' : '0');

                // Check if current code matches any encoding
                Integer character = reverseMap.get(currentCode.toString());
                if (character != null) {
                    decodedText.append((char)(int)character);
                    currentCode = new StringBuilder();
                }
            }
            in.close();

            // Write the decompressed text to file
            Files.write(Paths.get(outputPath), decodedText.toString().getBytes());

            // Print statistics
            long compressedSize = Files.size(Paths.get(compressedPath));
            long decompressedSize = Files.size(Paths.get(outputPath));

            System.out.println("\nDecompression Complete!");
            System.out.println("Compressed file: " + compressedPath);
            System.out.println("Decompressed file: " + outputPath);
            System.out.println("Compressed size: " + compressedSize + " bytes");
            System.out.println("Decompressed size: " + decompressedSize + " bytes");

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

class BitInputStream {
    private FileInputStream in;
    private int buffer;
    private int bitsRemaining;

    public BitInputStream(FileInputStream in) {
        this.in = in;
        buffer = 0;
        bitsRemaining = 0;
    }

    public int readBit() throws IOException {
        if (bitsRemaining == 0) {
            buffer = in.read();
            if (buffer == -1) {
                return -1;
            }
            bitsRemaining = 8;
        }
        bitsRemaining--;
        return (buffer >> bitsRemaining) & 1;
    }

    public void close() throws IOException {
        in.close();
    }
}