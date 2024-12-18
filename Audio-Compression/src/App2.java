import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class App2 {
    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream("exampleencoded.huff");
            byte[] header = new byte[44];
            file.read(header);

            int numberOfCodes = readInt(file);

            hashMap<String, Integer> map = new hashMap<String, Integer>(numberOfCodes);
            for (int i = 0; i < numberOfCodes; i++) {
                int sample = (file.read() << 8) | (file.read() & 0xFF);
                if (sample > 32767) {
                    sample -= 65536;
                }
                int codeLength = file.read();
                int bytes = (int) Math.ceil(codeLength / 8.0);
                byte[] codeBytes = new byte[bytes];
                file.read(codeBytes);
                StringBuilder codeBuilder = new StringBuilder();
                for (byte b : codeBytes) {
                    codeBuilder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
                }

                String huffmanCode = codeBuilder.substring(0, codeLength);
                map.insert(huffmanCode, sample);
            }
            int bytes = readInt(file);
            int numEncodedBytes = (int) Math.ceil(bytes / 8.0);
            byte[] encodedDataBytes = new byte[numEncodedBytes];
            file.read(encodedDataBytes);
            int totalSamples = readInt(file);
            int[] samples = new int[totalSamples];

            StringBuilder encodedDataBuilder = new StringBuilder();
            for (byte b : encodedDataBytes) {
                encodedDataBuilder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }

            String encodedData = encodedDataBuilder.substring(0, bytes);
            HuffmanTree tree = new HuffmanTree();
            tree.buildTree(map);
            tree.decode(encodedData, samples);

            FileOutputStream outFile = new FileOutputStream("decompressed.wav");
            outFile.write(header);

            int sampleSize = (header[35] | header[34]) / 8;

            for (int sample : samples) {
                if(sampleSize == 1) {
                    outFile.write((byte) sample);
                } else {
                    outFile.write(sample & 0xFF);
                    outFile.write(sample >> 8);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int readInt(FileInputStream file) throws IOException {
        int b1 = file.read();
        int b2 = file.read();
        int b3 = file.read();
        int b4 = file.read();

        if ((b1 | b2 | b3 | b4) < 0) {
            throw new EOFException("Unexpected end of file");
        }

        return ((b1 << 24)) |
                ((b2 & 0xFF) << 16) |
                ((b3 & 0xFF) << 8) |
                (b4 & 0xFF);
    }

}
