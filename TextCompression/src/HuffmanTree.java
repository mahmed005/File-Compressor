class HuffmanTree {
    private HuffmanNode root;
    private TextHashmap<Integer, String> codes;
    private int size;

    public HuffmanTree() {
        root = null;
        size = 0;
    }

    public void buildTree(TextminHeap priorityQueue) {
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

    public TextHashmap<Integer, String> encoding() {
        codes = new TextHashmap<>(size);
        if (root.getLeft() == null && root.getRight() == null) {
            codes.insert(root.getItem().getKey(), "0");
            return codes;
        }
        traverseAndEncode(root, "");
        return codes;
    }

    private void traverseAndEncode(HuffmanNode root, String code) {
        if (root == null) return;
        if (root.getIsData()) {
            codes.insert(root.getItem().getKey(), code);
        }
        traverseAndEncode(root.getLeft(), code + "0");
        traverseAndEncode(root.getRight(), code + "1");
    }
}