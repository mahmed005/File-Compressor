import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class imageDecompressor {

    public static void decompressImage(File compressedFile, String outputFilePath) {
        try  {
            FileInputStream ois = new FileInputStream("exampleencoded.huff");
            int headerLength = readInt(ois);
            byte[] header = new byte[headerLength];
            ois.read(header);

            int totalNodes = readInt(ois);
            hashMap<String, Integer> codes = new hashMap<String , Integer>();
            for (int i = 0; i < totalNodes; i++) {
                int pixelValue = readInt(ois);
                int codeLength = ois.read();
                int bytes = (int) Math.ceil(codeLength / 8.0);
                byte[] codeBytes = new byte[bytes];
                ois.read(codeBytes);
                StringBuilder codeBuilder = new StringBuilder();
                for (byte b : codeBytes) {
                    codeBuilder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
                }

                String huffmanCode = codeBuilder.substring(0, codeLength);
                codes.insert(huffmanCode, pixelValue);
            }

            int bytes = readInt(ois);
            int numEncodedBytes = (int) Math.ceil(bytes / 8.0);
            byte[] encodedDataBytes = new byte[numEncodedBytes];
            ois.read(encodedDataBytes);
            int width = readInt(ois);
            int height = readInt(ois);
            int[][] imageArray = new int[width][height];

            StringBuilder encodedDataBuilder = new StringBuilder();
            for (byte b : encodedDataBytes) {
                encodedDataBuilder.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
            }

            String encodedData = encodedDataBuilder.substring(0, bytes);
            HuffmanTree tree = new HuffmanTree();
            tree.buildTree(codes);
            tree.decode(encodedData, imageArray , height);

            BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    outputImage.setRGB(i, j, imageArray[i][j]);
                }
            }
            ImageIO.write(outputImage, "bmp", new File(outputFilePath));

            System.out.println("Image decompressed successfully to: " + outputFilePath);

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

    public static void main(String[] args) {
        File compressedFile = new File("exampleencoded.huff");
        String outputFilePath = "decompressed_image.bmp";
        decompressImage(compressedFile, outputFilePath);
    }
}
