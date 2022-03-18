package Queues;

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private int capacity;
    private final int originalSize = 8;
    private Item[] array;

    public RandomizedQueue(){
        array = (Item[]) new Object[originalSize];
        size = 0;
        capacity = originalSize;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void resize(int newSize){
        Item[] newArray = (Item[]) new Object[newSize];
        for(int i = 0; i < size; i++){
            newArray[i] = array[i];
        }
        array = newArray;
        capacity = newSize;
    }

    public void enqueue(Item item){
        if (item == null) throw new IllegalArgumentException();
        if (size == capacity) resize(capacity * 2);
        array[size] = item;
        size ++;
    }

    public Item dequeue(){
        if (this.isEmpty()) throw new NoSuchElementException();
        int tempIndex = StdRandom.uniform(size);
        Item result = array[tempIndex];
        array[tempIndex] = array[size - 1];
        array[size - 1] = null;
        size --;
        if (size > 0 && (double)size / capacity <= 0.25) resize(capacity / 2);
        return result;
    }

    // return a random item but do not remove it
    public Item sample(){
        if (this.isEmpty()) throw new NoSuchElementException();
        int tempIndex = StdRandom.uniform(size);
        return array[tempIndex];
    }

    // this iterator is read-only
    private class RandomizedQueueIterator implements Iterator<Item>{
        private int remain;
        private Item[] copyArray;

        public RandomizedQueueIterator(){
            remain = size;
            copyArray = (Item[]) new Object[remain];
            for(int i = 0; i < size; i++){
                copyArray[i] = array[i];
            }
            StdRandom.shuffle(copyArray);
        }

        @Override
        public Item next(){
            if (remain == 0) throw new NoSuchElementException();
            Item result = copyArray[--remain];
            return result;
        }

        @Override
        public boolean hasNext(){
            return remain > 0;
        }

        @Override
        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }

    public static void main(String[] args){
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        for (int i = 0; i < 18; i++) {
            rq.enqueue("A" + i);
        }
        System.out.println("first iterator");
        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
        System.out.println("second iterator ");
        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
        for (int i = 0; i < 18; i++) {
            System.out.print("deque ");
            System.out.print(rq.dequeue());
            System.out.println(". remain " + rq.size() + " elements. now capacity ");
        }

    }
}
