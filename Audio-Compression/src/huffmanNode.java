public class huffmanNode {
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
    
        public boolean getIsData(){
            return isData;
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
