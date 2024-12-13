import java.io.DataOutputStream;
import java.io.FileOutputStream;

public class App {
    public static void main(String[] args) throws Exception {
        Sampler sampler = new Sampler();
        int[] samples = sampler.covertToSamples();
        HashMap<Integer , Integer> map = new HashMap<Integer , Integer>(samples.length);
        for(int i = 0; i < samples.length; i++) {
            map.insert(samples[i]);
        }
        minHeap queue = map.makeMinHeap();
        huffmanTree tree = new huffmanTree();
        tree.buildTree(queue);
        HashMap<Integer , String> encoding = tree.encoding();
        Encoder encoder = new Encoder();
        byte[] header = sampler.getHeader();
        String encodedData = encoder.encodeSamples(samples, encoding);
        try {
            DataOutputStream outFile = new DataOutputStream(new FileOutputStream("exampleencoded.huff"));
            outFile.write(header);
            int numberOfNodes = encoding.getTotalNodes();
            outFile.writeInt(numberOfNodes);
            byte[] bytes;
            for(ItemNode<Integer , String> item : encoding.getIterable()) {
                bytes = new byte[2];
                bytes[0] = (byte) (item.getKey() >> 8);
                bytes[1] = (byte) (item.getKey() & 0xFF);
                outFile.write(bytes);
                StringBuilder code = new StringBuilder(item.getValue());
                int codeSize = (int) Math.ceil((double) code.length() / 8);
                outFile.write((byte) code.length());
                while(code.length() < codeSize * 8) {
                    code.append('0');
                }
                bytes = new byte[codeSize];
                for(int i = 0; i < codeSize; i++) {
                    int start = i*8;
                    int end = start + 8;
                    String byteString = code.substring(start , end);
                    bytes[i] = (byte) Integer.parseInt(byteString , 2);
                }
                outFile.write(bytes);
            }
            int length = encodedData.length();
            outFile.writeInt(length);
            StringBuilder data = new StringBuilder(encodedData);
            while (data.length() < (((int) Math.ceil((double) length/8)) * 8)) {
                data.append('0');
            }
            encodedData = data.toString();
            bytes = new byte[encodedData.length()/8];
            for(int i = 0; i < encodedData.length() / 8; i++) {
                int start = i*8;
                int end = start + 8;
                bytes[i] = (byte) Integer.parseInt(encodedData.substring(start, end) , 2);
            }
            outFile.write(bytes);
        } catch (Exception e) {
            throw new Exception("An error occured");
        }
    }
}
