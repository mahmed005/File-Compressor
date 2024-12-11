public class huffmanTree {
    private huffmanNode root;
    private HashMap<Integer , String> codes;
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

    public HashMap<Integer,String> encoding(){
        codes = new HashMap<Integer , String>(size/2);
        if(root.getLeft() ==null && root.getRight()==null){
            codes.insert(root.getItem().getKey(), "0");
            return codes;
        }
        
        traverseAndEncode(root, "");
        return codes;
    }


    public void traverseAndEncode(huffmanNode root,String code){

        if(root==null)
            return;

        if(root.getIsData()) {
            codes.insert(root.getItem().getKey(), code);
        }

        traverseAndEncode(root.getLeft(), code+"0");
        traverseAndEncode(root.getRight(), code+"1");
    }
}