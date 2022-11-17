import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private class Node
    {
        Item item;
        Node next;
    }
    private Node first;
    // construct an empty randomized queue
    public RandomizedQueue(){
        first = new Node();
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return this.first.item == null;
    }

    // return the number of items on the randomized queue
    public int size(){
        Node current = this.first;
        int size = 0;
        while(current.next != null){
            size++;
            current = current.next;
        }
        return size;
    }
    // add the item
    public void enqueue(Item item){
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node tmp = new Node();
        tmp.item = item;
        tmp.next = this.first;
        this.first = tmp;
    }
    // remove and return a random item
    public Item dequeue(){
        if(isEmpty()){
            throw new java.util.NoSuchElementException();
        }
        Node tmp;
        int s = StdRandom.uniformInt(size());
        Node current = this.first;
        if(s == 0){
            tmp = first;
            first = first.next;
        }else{
            while(--s > 0){
                current = current.next;
            }
            tmp = current.next;
            current.next = current.next.next;

        }

        return tmp.item;
    }
    // return a random item (but do not remove it)
    public Item sample(){
        if(isEmpty()){
            throw new java.util.NoSuchElementException();
        }
        int s = StdRandom.uniformInt(size());
        Node current = this.first;
        while(--s > 0){
            current = current.next;
        }
        Node tmp = current;
        return tmp.item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        return new Itr();
    }
    private class Itr implements Iterator<Item>{

        private Node node = new Node();
        private Node current;
        Itr(){
            Node tmp = node;
            while(first.next != null){
                tmp.item = dequeue();
                tmp.next = new Node();
                tmp = tmp.next;
            }
            first = node;
            current = node;
        }
        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Object> randomizedQueue = new RandomizedQueue<>();

        System.out.println("####### isEmpty() & size() Testing #######");

        System.out.println("True, the deque is empty -> " + randomizedQueue.isEmpty());
        System.out.println("Size must be 0 -> " + randomizedQueue.size());

        System.out.println();
        System.out.println("Adding one element...");
        randomizedQueue.enqueue(10);

        System.out.println();
        System.out.println("False, the randomizedQueue is not empty -> " + randomizedQueue.isEmpty());
        System.out.println("Size must be 1 -> " + randomizedQueue.size());

        System.out.println();


        System.out.println("#######dequeue() and sample() Testing #######");
        randomizedQueue.enqueue(20);
        randomizedQueue.enqueue(30);
        randomizedQueue.enqueue(40);
        System.out.println("size -> " + randomizedQueue.size());

        System.out.println("dequeue a random element -> " + randomizedQueue.dequeue());
        System.out.println("get a random element as a sample -> " + randomizedQueue.sample());
        System.out.println();


        System.out.println("#######Iterator Testing #######");

        Iterator<Object> iterator = randomizedQueue.iterator();

        System.out.println("the randomizedQueue is : ");
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println();

        iterator = randomizedQueue.iterator();

        System.out.println("the randomizedQueue is : ");
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println();

    }

}