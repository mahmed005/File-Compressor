import java.util.ArrayList;
import java.util.List;

class ItemNode<K, V> {

    private K key;
    private V value;

    public ItemNode(K key) {
        this.key = key;
        value = (V) Integer.valueOf(1);
    }

    public ItemNode(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void increaseValue() {
        value = (V) (Integer) (((Integer) value) + 1);
    }

    public V getValue() {
        return value;
    }
}

class LinkedList<K, V> {
    public Node<K, V> head;
    public int count;

    public LinkedList() {
        head = null;
        count = 0;
    }
}

class Node<K, V> {
    private ItemNode<K, V> item;
    public Node<K, V> next;

    public Node(K key) {
        item = new ItemNode(key);
        next = null;
    }

    public Node(K key, V value) {
        item = new ItemNode(key, value);
        next = null;
    }

    public K getKey() {
        return item.getKey();
    }

    public ItemNode<K, V> getItem() {
        return item;
    }

    public void increaseValue() {
        item.increaseValue();
    }
};

public class hashMap<K, V> {
    private int size;
    private LinkedList<K, V> array[];

    public hashMap() {
        array = new LinkedList[1000];
        size = 1000;
        for (int i = 0; i < size; i++) {
            array[i] = new LinkedList();
        }
    }

    public hashMap(int size) {
        this.size = size;
        array = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            array[i] = new LinkedList();
        }
    }

    public void insert(K key) {
        int index = Math.abs(key.hashCode() % size);
        Node<K, V> p = search(key, index);
        if (p == null) {
            Node<K, V> t = new Node(key);
            array[index].head = t;
            array[index].count++;
        } else {
            if (p.getKey().equals(key)) {
                p.increaseValue();
            } else {
                Node<K, V> t = new Node(key);
                p.next = t;
                array[index].count++;
            }
        }
    }

    public void insert(K key , V value) {
        int index = Math.abs(key.hashCode() % size);
        Node<K , V> temp = new Node(key , value);
        Node<K , V> p = array[index].head;
        while(p != null && p.next != null) {
            p = p.next;
        }
        if(p == null) {
            array[index].head = temp;
        } else {
            p.next = temp;
        }
        array[index].count++;
    }

    private Node<K, V> search(K key, int index) {
        Node<K, V> p = array[index].head;
        while (p != null && p.next != null) {
            if (p.getKey().equals(key))
                break;
            p = p.next;
        }
        return p;
    }

    public int getTotalNodes() {
        int count = 0;
        for (int i = 0; i < size; i++) {
            count += array[i].count;
        }
        return count;
    }

    public V get(K key) {
        int index = Math.abs(key.hashCode() % size);
        Node<K , V> p = array[index].head;
        while(p != null) {
            if(p.getKey().equals(key))
            return p.getItem().getValue();
            p = p.next;
        }
        return null;
    }

    public void display() {
        for(int i =0 ; i < size-1; i++) {
            Node<K, V> p = array[i].head;
            while(p != null) {
                System.out.println(p.getItem().getKey() + " Mapping: ->  " + p.getItem().getValue());
                p = p.next;
            }
        }
    }

    public minHeap makeMinHeap() {
        int count = getTotalNodes();
        minHeap heap = new minHeap(count);
        for (int i = 0; i < size; i++) {
            Node<K , V> p = array[i].head;
            while (p != null) {
                ItemNode<Integer , Integer> item = (ItemNode<Integer , Integer>) p.getItem();
                HuffmanNode node = new HuffmanNode(item);
                heap.add(node);
                p = p.next;
            }
        }
        return heap;
    }

    public Iterable<ItemNode<K , V>> getIterable() {
        List<ItemNode<K , V>> list = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            Node<K , V> p = array[i].head;
            while(p != null) {
                list.add(p.getItem());
                p = p.next;
            }
        }
        return list;
    }
};
