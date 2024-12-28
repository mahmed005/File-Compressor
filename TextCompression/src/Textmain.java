import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.nio.file.*;

public class Textmain {
    public static void main(String[] args) {
        try {
            // Hardcoded file path
            String inputPath = "test1.txt";    // Replace with your desired file path

            // Validate file exists
            File inputFile = new File(inputPath);
            if (!inputFile.exists()) {
                System.out.println("Error: File does not exist!");
                return;
            }

            // Define output file paths
            String outputPath = inputPath + ".compressed";
            String encodingPath = inputPath + ".encoding";

            // Read the input file
            String text = new String(Files.readAllBytes(Paths.get(inputPath)));

            // Create frequency map using your implementation
            TextHashmap<Integer, Integer> frequencyMap = new TextHashmap<>(256);
            for (char c : text.toCharArray()) {
                frequencyMap.insert((int) c);
            }

            System.out.println("\nCharacter Frequencies:");
            frequencyMap.display();

            // Create min-heap using the makeMinHeap method from your TextHashmap
            TextminHeap heap = frequencyMap.makeMinHeap();

            // Build Huffman tree
            HuffmanTree tree = new HuffmanTree();
            tree.buildTree(heap);
            TextHashmap<Integer, String> encoding = tree.encoding();

            System.out.println("\nHuffman Encodings:");
            encoding.display();

            // Encode text
            TextEncoder encoder = new TextEncoder();
            String encodedText = encoder.encodeText(text, encoding);

            // Write compressed data
            BitOutputStream out = new BitOutputStream(new FileOutputStream(outputPath));
            for (char bit : encodedText.toCharArray()) {
                out.writeBit(bit == '1');
            }
            out.close();

            // Save encoding map
            try (PrintWriter writer = new PrintWriter(new FileWriter(encodingPath))) {
                for (ItemNode<Integer, String> item : encoding.getIterable()) {
                    writer.println(item.getKey() + ":" + item.getValue());
                }
            }

            // Print statistics
            long originalSize = Files.size(Paths.get(inputPath));
            long compressedSize = Files.size(Paths.get(outputPath));
            double compressionRatio = (1 - ((double)compressedSize / originalSize)) * 100;

            System.out.println("\nCompression Complete!");
            System.out.println("Original file: " + inputPath);
            System.out.println("Compressed file: " + outputPath);
            System.out.println("Original size: " + originalSize + " bytes");
            System.out.println("Compressed size: " + compressedSize + " bytes");
            System.out.printf("Compression ratio: %.2f%%\n", compressionRatio);

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}

class BitOutputStream {
    private FileOutputStream out;
    private int buffer;
    private int bitCount;

    public BitOutputStream(FileOutputStream out) {
        this.out = out;
        buffer = 0;
        bitCount = 0;
    }

    public void writeBit(boolean bit) throws IOException {
        buffer = (buffer << 1) | (bit ? 1 : 0);
        bitCount++;

        if (bitCount == 8) {
            out.write(buffer);
            bitCount = 0;
            buffer = 0;
        }
    }

    public void close() throws IOException {
        if (bitCount > 0) {
            buffer = buffer << (8 - bitCount);
            out.write(buffer);
        }
        out.close();
    }
}