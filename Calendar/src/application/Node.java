package application;

public class Node<T>{
    public T data;     // Entry in bag
    public Node next;  // Link to next node

    public Node(T dataPortion) {
        this(dataPortion, null);
    }

    Node(T dataPortion, Node nextNode) {
        data = dataPortion;
        next = nextNode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node getNextNode() {
        return next;
    }

    public void setNextNode(Node next) {
        this.next = next;
    }
}