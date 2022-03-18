package Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // construct a Linked List class
    private class Node{
        Item value;
        Node next;
        Node pre;

        public Node(){
            value = null;
            next = null;
            pre = null;
        }

        public Node(Item item, Node pre, Node next){
            this.value = item;
            this.next = next;
            this.pre = pre;
        }
    }

    // elements of the deque
    private int size;
    private Node nodeptr;
    // construct an empty deque
    public Deque(){
        nodeptr = new Node();
        nodeptr.pre =  nodeptr;
        nodeptr.next = nodeptr;
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void addFirst(Item item){
        if (item == null) throw new IllegalArgumentException("Cannot add null to the deque!");
        Node newNode = new Node(item, null, nodeptr.pre);
        nodeptr.pre.pre = newNode;
        nodeptr.pre = newNode;
        if (size == 0) nodeptr.next = newNode;
        size++;
    }

    public void addLast(Item item){
        if (item == null) throw new IllegalArgumentException("Cannot add null to the deque!");
        Node newNode = new Node(item, nodeptr.next, null);
        nodeptr.next.next = newNode;
        nodeptr.next = newNode;
        if (size == 0) nodeptr.next = newNode;
        size++;
    }

    // remove and return the first item from the front
    public Item removeFirst(){
        if (this.isEmpty()) throw new NoSuchElementException("Cannot drop any element anymore!");
        if (size == 1) {
            Item result = nodeptr.pre.value;
            nodeptr = new Node();

            size--;
            return result;
        }
        Item result = nodeptr.pre.value;
        nodeptr.pre = nodeptr.pre.next;
        nodeptr.pre.pre = null;
        size--;

        return result;
    }

    public Item removeLast(){
        if (this.isEmpty()) throw new NoSuchElementException("Cannot drop any element anymore!");
        if (size == 1) {
            Item result = nodeptr.next.value;
            nodeptr = new Node();
            size--;
            return result;
        }
        Item result = nodeptr.next.value;
        nodeptr.next = nodeptr.next.pre;
        nodeptr.next.next = null;
        size--;

        return result;
    }

    private class dequeIterator implements Iterator<Item> {
        private Node ptr;
        private int remains;
        public dequeIterator(){
            ptr = nodeptr.pre;
            remains = size;
        }

        @Override
        public Item next(){
            if (remains == 0) throw new NoSuchElementException("Iterator failed");
            Item result = ptr.value;
            ptr = ptr.next;
            remains--;
            return result;

        }

        @Override
        public boolean hasNext(){
            return remains > 0;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }
    public Iterator<Item> iterator(){
        return new dequeIterator();
    }

    public static void main(String[] args){
        Deque<String> dq = new Deque<>();
        for (int i = 0; i < 4; i++) {
            dq.addFirst("A" + i);
        }
        for (int i = 0; i < 4; i++) {
            dq.addLast("B" + i);
        }
        for (String s : dq) {
            System.out.println(s);
        }
        System.out.println("dq has " + dq.size() + " elements in total");
        for (int i = 0; i < 10; i++) {
            System.out.println(dq.removeFirst());
            System.out.println(dq.removeLast());
            System.out.println(dq.size());
        }
    }


}
