class Node<T>{
    public T key;
    public int frequency;
    public Node<T> next;
};

class Hashmap{
    private Node<T> A[1000];

    public Hashmap() {
        for(int i = 0; i < 1000; i++) {
            A = new Node[1000];
            A[i] = NULL;
        }
    }

    public void insert(T key) {
        int index = key%1000;
        if(!A[index]) {
            A[index] = new Node<T>;
            A[index].key = key;
            A[index].frequency = 1;
            A[index].next = NULL;
        } else {
            Node *p = A[index];
            Node *q = NULL;
            while(p && p->data < key) {
                q = p;
                p = p->next;
            }
            if(!q) {
                t->next = p;
                A[index] = t;
            } else {
                q->next = t;
                t->next = p;
            }
        }
    }

    Node *Search(int key) {
        int index = key%10;
        Node *p = A[index];
        while(p && p->data < key){
            p = p->next;
        }
        if(p) {
            if(p->data == key) {
                return p;
            }
            return NULL;
        } 
        return NULL;
    }
};
