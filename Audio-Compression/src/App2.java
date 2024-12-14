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

            hashMap<String, Integer> map = new hashMap<String, Integer>(numberOfCodes / 2);
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
            tree.decode(encodedData , samples);

            FileOutputStream outFile = new FileOutputStream("decompressed.wav");
            outFile.write(header);

            for(int sample : samples) {
                outFile.write(sample & 0xFF);
                outFile.write((sample >> 8) & 0xFF);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int readInt(FileInputStream file) throws IOException {
        int result = (file.read() << 24) | (file.read() << 16) | (file.read() << 8) | (file.read());
        return result;
    }
}
