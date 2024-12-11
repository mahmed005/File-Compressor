class huffmanNode{

    private ItemNode<Integer , Integer> item;
    private huffmanNode left;
    private huffmanNode right;
    private boolean isData;

    public huffmanNode(ItemNode<Integer , Integer> item){
        this.item=item;
        left=null;
        right=null;
        isData = true;
    }

    public huffmanNode(huffmanNode left, huffmanNode right) {
        this.item = new ItemNode(0);
        this.item.setValue(left.getItem().getValue() + right.getItem().getValue());
        this.left = left;
        this.right = right;
        isData = false;
    }

    public ItemNode<Integer , Integer> getItem() {
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
    private int size;
    
    public huffmanTree(){
        root = null;
        size = 0;
    }

    public void buildTree(minHeap priorityQueue) {
        size = priorityQueue.getSize();
        while(!priorityQueue.isEmpty()) {
            if(priorityQueue.getSize()>1){
                huffmanNode left=priorityQueue.extractMin();
                huffmanNode right=priorityQueue.extractMin();
                huffmanNode parent=new huffmanNode(left,right);
                priorityQueue.add(parent);
            }
            else{
                root=priorityQueue.extractMin();
            }
        }
    }

    public void encoding(){
        HashMap<Integer , String> codes = new HashMap<Integer , String>(size/2);
        if (root.getLeft() ==null && root.getRight()==null){
            codes.insert(root.getItem().getKey(), "1");
            return;
        }
        
    }
}