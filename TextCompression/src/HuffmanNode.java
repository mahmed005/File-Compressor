class HuffmanNode {
    private ItemNode<Integer, Integer> item;
    private HuffmanNode left;
    private HuffmanNode right;
    private boolean isData;

    public HuffmanNode(ItemNode<Integer, Integer> item) {
        this.item = item;
        left = null;
        right = null;
        isData = true;
    }

    public HuffmanNode(HuffmanNode left, HuffmanNode right) {
        this.item = new ItemNode<>(0);
        if (left != null && right != null) {
            this.item.setValue(left.getItem().getValue() + right.getItem().getValue());
        }
        this.left = left;
        this.right = right;
        isData = false;
    }

    public boolean getIsData() {
        return isData;
    }

    public ItemNode<Integer, Integer> getItem() {
        return item;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }
}
