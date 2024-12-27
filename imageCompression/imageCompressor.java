import java.io.*;

public class imageCompressor {
    public static File compressImage(File image) {
        imageHandler handler = new imageHandler(image);

        int[][] img = handler.imageToArray();
        int x = handler.getWidth();
        int y = handler.getHeight();

        hashMap<Integer, Integer> frequencies = new hashMap<>(x * y);
        minHeap queue;
        HuffmanTree tree = new HuffmanTree();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                frequencies.insert(img[i][j]);
            }
        }

        queue = frequencies.makeMinHeap();
        tree.buildTree(queue);

        hashMap<Integer, String> codes = tree.encoding();

        encoder ec = new encoder();
        String encodedData = ec.encodePixels(img, codes);
        byte[] header;
        try {
            header = handler.getHeader();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try  {
            DataOutputStream oos = new DataOutputStream(new FileOutputStream("exampleencoded.huff"));

            oos.writeInt(header.length);
            oos.write(header);

            oos.writeInt(codes.getTotalNodes());
            for (var entry : codes.getIterable()) {
                oos.writeInt(entry.getKey()); 
                StringBuilder code = new StringBuilder(entry.getValue());
                oos.writeByte((byte) code.length());
                int codeSize = ((int) Math.ceil(code.length()/8.0)) * 8;
                while(code.length() < codeSize) {
                    code.append("0");
                }
                for(int i = 0; i < code.length(); i += 8) {
                    int start = i;
                    int end = i + 8;
                    String subCode = code.substring(start, end);
                    oos.writeByte((byte) Integer.parseInt(subCode , 2));
                }
            }

            int length = encodedData.length();
            oos.writeInt(length);
            StringBuilder data = new StringBuilder(encodedData);
            while (data.length() < (((int) Math.ceil((double) length/8)) * 8)) {
                data.append('0');
            }
            encodedData = data.toString();
            byte[] bytes = new byte[encodedData.length()/8];
            for(int i = 0; i < encodedData.length() / 8; i++) {
                int start = i*8;
                int end = start + 8;
                bytes[i] = (byte) Integer.parseInt(encodedData.substring(start, end) , 2);
            }
            oos.write(bytes);
            oos.writeInt(x);
            oos.writeInt(y);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    public static void main(String[] args) {
        File file = new File("test1.bmp");
        File cmp = compressImage(file);
        if (cmp != null) {
            System.out.println("Compressed file size: " + cmp.length() + " bytes");
        }
    }
}
