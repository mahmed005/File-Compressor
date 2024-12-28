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
        item = new ItemNode<>(key);
        next = null;
    }

    public Node(K key, V value) {
        item = new ItemNode<>(key, value);
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
}

public class TextHashmap<K, V> {
    private int size;
    private LinkedList<K, V>[] array;

    public TextHashmap() {
        this(1000); // Default size
    }

    public TextHashmap(int size) {
        this.size = size;
        array = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            array[i] = new LinkedList<>();
        }
    }

    public void insert(K key) {
        int index = Math.abs(key.hashCode() % size);
        Node<K, V> p = search(key, index);
        if (p == null) {
            Node<K, V> t = new Node<>(key);
            t.next = array[index].head;
            array[index].head = t;
            array[index].count++;
        } else {
            p.increaseValue();
        }
    }
    public void insert(K key, V value) {
        int index = Math.abs(key.hashCode() % size); // Calculate the hash index
        Node<K, V> existingNode = search(key, index);

        if (existingNode == null) {
            // Key does not exist, create a new node and add it
            Node<K, V> newNode = new Node<>(key, value);
            newNode.next = array[index].head;
            array[index].head = newNode;
            array[index].count++;
        } else {
            // Key exists, update its value
            existingNode.getItem().setValue(value);
        }
    }

    private Node<K, V> search(K key, int index) {
        Node<K, V> p = array[index].head;
        while (p != null) {
            if (p.getKey().equals(key)) {
                return p;
            }
            p = p.next;
        }
        return null;
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
        Node<K, V> p = array[index].head;
        while (p != null) {
            if (p.getKey().equals(key)) {
                return p.getItem().getValue();
            }
            p = p.next;
        }
        return null;
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            Node<K, V> p = array[i].head;
            while (p != null) {
                K key = p.getKey();
                V value = p.getItem().getValue();
                System.out.println("Key: " + key + " -> Value: " + value);
                p = p.next;
            }
        }
    }

    public TextminHeap makeMinHeap() {
        int count = getTotalNodes();
        TextminHeap heap = new TextminHeap(count);
        for (int i = 0; i < size; i++) {
            Node<K, V> p = array[i].head;
            while (p != null) {
                // Create a new ItemNode with Integer types
                ItemNode<Integer, Integer> newItem = new ItemNode<>((Integer)p.getItem().getKey(), (Integer)p.getItem().getValue());
                HuffmanNode node = new HuffmanNode(newItem);
                heap.add(node);
                p = p.next;
            }
        }
        return heap;
    }

    public Iterable<ItemNode<K, V>> getIterable() {
        List<ItemNode<K, V>> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            Node<K, V> p = array[i].head;
            while (p != null) {
                list.add(p.getItem());
                p = p.next;
            }
        }
        return list;
    }
}