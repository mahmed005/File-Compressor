public class HuffmanTree {
    private HuffmanNode root;
    private hashMap<Integer, String> codes;
    private int size;

    public HuffmanTree() {
        root = null;
        size = 0;
    }

    public void buildTree(minHeap priorityQueue) {
        size = priorityQueue.getSize();
        while (!priorityQueue.isEmpty()) {
            if (priorityQueue.getSize() > 1) {
                HuffmanNode left = priorityQueue.extractMin();
                HuffmanNode right = priorityQueue.extractMin();
                HuffmanNode parent = new HuffmanNode(left, right);
                priorityQueue.add(parent);
            } else {
                root = priorityQueue.extractMin();
            }
        }
    }

    public hashMap<Integer, String> encoding() {
        codes = new hashMap<Integer, String>(size / 2);
        if (root.getLeft() == null && root.getRight() == null) {
            codes.insert(root.getItem().getKey(), "0");
            return codes;
        }

        traverseAndEncode(root, "");
        return codes;
    }

    private void traverseAndEncode(HuffmanNode root, String code) {

        if (root == null)
            return;

        if (root.getIsData()) {
            codes.insert(root.getItem().getKey(), code);
        }

        traverseAndEncode(root.getLeft(), code + "0");
        traverseAndEncode(root.getRight(), code + "1");
    }

    public void buildTree(hashMap<String, Integer> map) {
        root = new HuffmanNode(null, null);

        for (ItemNode<String, Integer> item : map.getIterable()) {
            String code = item.getKey();
            int mapping = item.getValue();
            insertInTree(code, mapping);
        }
    }

    private void insertInTree(String code, int mapping) {
        HuffmanNode p = root;
        int current = 0;
        int end = code.length();
        while (current < end) {
            if (code.charAt(current) == '0') {
                if (p.getLeft() == null) {
                    if (current == end - 1) {
                        p.setLeft(new HuffmanNode(new ItemNode<Integer, Integer>(mapping)));
                    } else {
                        p.setLeft(new HuffmanNode(null, null));
                    }
                }
                p = p.getLeft();
            } else {
                if (p.getRight() == null) {
                    if (current == end - 1) {
                        p.setRight(new HuffmanNode(new ItemNode<Integer, Integer>(mapping)));
                    } else {
                        p.setRight(new HuffmanNode(null, null));
                    }
                }
                p = p.getRight();
            }
            current++;
        }
    }

    public void decode(String encodedData , int[] filledArray) {
        int index = 0;
        HuffmanNode p = root;
        for (int i = 0; i < encodedData.length(); i++) {
            if (encodedData.charAt(i) == '0') {
                p = p.getLeft();
            } else {
                p = p.getRight();
            }
            if (p.getIsData() == true){
                filledArray[index++] = p.getItem().getKey();
                p = root;
            }
        }
    }
}