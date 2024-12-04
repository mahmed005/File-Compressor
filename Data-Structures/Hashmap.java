class ItemNode {
    private int key;
    private int frequency;

    public ItemNode(int key) {
        this.key = key;
        frequency = 1;
    }

    public int getKey() {
        return key;
    }
    
    public void increaseFrequency() {
        frequency++;
    }

    public int getFrequency() {
        return frequency;
    }
}

class LinkedList{
    public Node head;
    public int count;

    public LinkedList() {
        head = null;
        count = 0;
    }
}


class Node{
    private ItemNode item;
    public Node next;

    public Node(int key) {
        item = new ItemNode(key);
        next = null;
    }

    public int getKey() {
        return item.getKey();
    }

    public ItemNode getItem() {
        return item;
    }

    public void increaseFrequency() {
        item.increaseFrequency();
    }
};

class Hashmap{
    private int size;
    private LinkedList array[];

    public Hashmap() {
        array = new LinkedList[1000];
        size = 1000;
        for(int i = 0; i < size; i++) {
            array[i] = new LinkedList();
        }
    }

    public Hashmap(int size) {
        this.size = size;
        array = new LinkedList[size];
        for(int i = 0; i < size; i++) {
            array[i] = new LinkedList();
        }
    }

    public void insert(int key) {
        int index = key % size;
        Node p = search(key , index);
        if(p == null) {
            Node t = new Node(key);
            array[index].head = t;
            array[index].count++;
        } else {
            if(p.getKey() == key) {
                p.increaseFrequency();
            }
            else {
                Node t = new Node(key);
                p.next = t;
                array[index].count++;
            }
        }
    }

    private Node search(int key, int index) {
        Node p = array[index].head;
        while(p != null && p.next != null) {
            if(p.getKey() == key)
                break;
            p = p.next;
        }
        return p;
    }

    private int getTotalNodes() {
        int count = 0;
        for(int i = 0; i < size; i++) {
            count += array[i].count;
        }
        return count;
    }

    public minHeap makeMinHeap() {
        int count = getTotalNodes();
        minHeap heap = new minHeap(count);
        for(int i = 0; i < size; i++) {
            Node p = array[i].head;
            while(p != null) {
                ItemNode item = p.getItem();
                heap.add(item);
                p = p.next;
            }
        }
        return heap;
    }
};
