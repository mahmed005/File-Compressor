 public class MinHeap {
    private int size;
    private int length;
    private int[] heap;
    MinHeap(int size){
        this.size=size;
        length=0;
        heap=new int[size];
    }
    public boolean isEmpty(){
        return length==0;
    }
    public boolean isFull(){
        return length>=size;
    }
    public void insert(int val){
        if(isFull()) throw new IllegalStateException("The heap is full");
        int i=++length;
        heap[i]=val;
        int temp=heap[i];
        if (i > 1 && temp<heap[i/2]) {
            heap[i]=heap[i/2];
            i/=2;
        }
        heap[i]=temp;
    }
     private int getMin(){
         if(size==0) throw new IllegalAccessError("The Heap is empty");
         return heap[1];
     }
    public int extractMin(){
        int x,i,j;
        x=heap[1];
        heap[1]=heap[length--];
        i=1;
        j=i*2;
        while(j<=length){
            if(heap[j]<heap[j+1])j++;
            if(heap[j]>heap[i]){
                swap(i,j);
                i=j;
                j=i*2;
            }else
                break;
        }
        return x;
    }
   public void display(){
        for(int i=1;i<=length;i++){
            System.out.print(heap[i]+ " ");
        }
    }
     private void swap(int index1,int index2){
         int temp=heap[index1];
         heap[index1]=heap[index2];
         heap[index2]=temp;
     }


     public static void main(String[] args) {
        MinHeap minHeap=new MinHeap(10);
        minHeap.insert(10);
        minHeap.insert(8);
        minHeap.insert(2);
        minHeap.insert(20);
        minHeap.insert(11);
        minHeap.insert(9);
        minHeap.insert(7);
        minHeap.display();
        System.out.println(" "+ minHeap.extractMin());
        minHeap.display();
     }
}