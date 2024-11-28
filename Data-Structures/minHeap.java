public class minHeap{

    private int capacity;
    private int size;
    private int [] heap;

    public minHeap(int n){
        heap = new int[n];
        capacity=n;
        size=0;
    }

    private void swap(int index1,int index2){
        int temp=heap[index1];
        heap[index1]=heap[index2];
        heap[index2]=temp;
    }

    public void add(int value){
        if(size==capacity) throw new IllegalStateException("The Heap is full");
        heap[size]=value;
        size++;
        heapifyUp();
    }

    private void heapifyUp(){
        int index=size-1;
        while(hasParent(index) && heap[getParentIndex(index)]>heap[index]){
            swap(index, getParentIndex(index));
            index=getParentIndex(index);
        }
    }

    public int extractMin(){
        if(size==0) throw new IllegalStateException("The Heap is empty");
        int min=heap[0];
        heap[0]=heap[size-1];
        size--;
        heapifyDown();
        return min;
    }

    private void heapifyDown(){
        int index=0; 
        while(hasLeftChild(index)){ 
            int smallerChildIndex=getLeftIndex(index);
            if(hasRightChild(index) && heap[getRightIndex(index)]<heap[smallerChildIndex]){
                smallerChildIndex=getRightIndex(index);
            }

            if(heap[index]<=heap[smallerChildIndex])
                break;
            else{
                swap(index, smallerChildIndex);
                index=smallerChildIndex;
            }
        }
    }

    private int getMin(){
        if(size==0) throw new IllegalAccessError("The Heap is empty");
        return heap[0];
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

    public static void main(String[] args) {
        minHeap minHeap = new minHeap(10);
    
        minHeap.add(10);
        minHeap.add(5);
        minHeap.add(3);
        minHeap.add(2);
    
        System.out.println("Min element: " + minHeap.getMin()); // Should print 2
        System.out.println("Extracted Min: " + minHeap.extractMin()); // Should print 2
        System.out.println("Extracted Min: " + minHeap.extractMin()); // Should print 3
    }
    

}