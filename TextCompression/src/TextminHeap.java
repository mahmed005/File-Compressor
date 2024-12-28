public class TextminHeap{
    private int capacity;
    private int size;
    private HuffmanNode [] heap;

    public TextminHeap(int n){
        heap = new HuffmanNode[n];
        capacity=n;
        size=0;
    }

    private void swap(int index1,int index2){
        HuffmanNode temp=heap[index1];
        heap[index1]=heap[index2];
        heap[index2]=temp;
    }

    public void add(HuffmanNode n){
        if(size==capacity) throw new IllegalStateException("The Heap is full");
        heap[size]=n;
        size++;
        heapifyUp();
    }

    private void heapifyUp(){
        int index=size-1;
        while(hasParent(index) && heap[getParentIndex(index)].getItem().getValue()>heap[index].getItem().getValue()){
            swap(index, getParentIndex(index));
            index=getParentIndex(index);
        }
    }

    public HuffmanNode extractMin(){
        if(size==0) throw new IllegalStateException("The Heap is empty");
        HuffmanNode min=heap[0];
        heap[0]=heap[size-1];
        size--;
        heapifyDown();
        return min;
    }

    private void heapifyDown(){
        int index=0; 
        while(hasLeftChild(index)){ 
            int smallerChildIndex=getLeftIndex(index);
            if(hasRightChild(index) && heap[getRightIndex(index)].getItem().getValue()<heap[smallerChildIndex].getItem().getValue()){
                smallerChildIndex=getRightIndex(index);
            }

            if(heap[index].getItem().getValue()<=heap[smallerChildIndex].getItem().getValue())
                break;
            else{
                swap(index, smallerChildIndex);
                index=smallerChildIndex;
            }
        }
    }

    public boolean isEmpty(){
        return size==0;
    }

    private HuffmanNode getMin(){
        if(size==0) throw new IllegalAccessError("The Heap is empty");
        return heap[0];
    }

    public int getSize(){
        return size;
    }

    private boolean hasParent(int index){
        return index>0;
    }

    private boolean hasLeftChild(int index){
        return getLeftIndex(index)<size;
    }

    private boolean hasRightChild(int index){
        return getRightIndex(index)<size;
    }

    private int getParentIndex(int key){
        return (key-1)/2;
    }

    private int getLeftIndex(int index) {
        return 2*index+1;
    }

    private int getRightIndex(int index) {
        return 2*index+2;
    }
}