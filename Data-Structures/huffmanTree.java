class huffmanNode{

    private ItemNode item;
    private huffmanNode left;
    private huffmanNode right;
    private boolean isData;

    public huffmanNode(ItemNode item){
        this.item=item;
        left=null;
        right=null;
        isData = true;
    }

    public huffmanNode(huffmanNode left, huffmanNode right) {
        this.item = new ItemNode(0);
        this.left = left;
        this.right = right;
        isData = false;
    }

    public ItemNode getItem() {
        return item;
    }

    public huffmanNode getLeft() {
        return left;
    }

    public huffmanNode getRight() {
        return right;
    }
}



public class huffmanTree {
    private huffmanNode root;
    
    public huffmanTree(){
        root = null;
    }

    public void buildTree(minHeap priorityQueue) {
        while(!priorityQueue.isEmpty()) {
            
        }
    }
}

