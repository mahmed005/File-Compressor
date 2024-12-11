public class App {
    public static void main(String[] args) throws Exception {
        Sampler sampler = new Sampler();
        int[] samples = sampler.covertToSamples();
        HashMap<Integer , Integer> map = new HashMap<Integer , Integer>(1000);
        for(int i = 0; i < samples.length; i++) {
            map.insert(samples[i]);
        }
        minHeap queue = map.makeMinHeap();
        huffmanTree tree = new huffmanTree();
        tree.buildTree(queue);
        HashMap<Integer , String> encoding = tree.encoding();
        encoding.display();
    }
}
