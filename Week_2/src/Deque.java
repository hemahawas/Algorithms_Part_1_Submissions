import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node
    {
        Item item;
        Node next;
    }
    private Node first;


    // construct an empty deque
    public Deque() {
         this.first = new Node();
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.first.item == null;
    }

    // return the number of items on the deque
    public int size() {
        Node current = this.first;
        int size = 0;
        while (current.next != null) {
            size++;
            current = current.next;
        }
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node tmp = new Node();
        tmp.item = item;
        tmp.next = this.first;
        this.first = tmp;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if(isEmpty()){
            this.first.item = item;
            first.next = new Node();
        }
        else{
            Node current = this.first;
            while (current.next != null) {
                current = current.next;
            }
            current.next = new Node();
            current.item = item;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node tmp = this.first;
        this.first = this.first.next;
        return tmp.item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node tmp = new Node();
        if(size() == 1){
            tmp.item = removeFirst();
        }else{

            Node current = this.first;
            while(current.next.next != null){
                current = current.next;
            }
            tmp.item = current.item;
            current.next = null;
        }

        return tmp.item;
    }


    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node node = first;
            @Override
            public boolean hasNext() {
                return node.next != null;
            }

            @Override
            public Item next() {
                if(!hasNext()){
                    throw new NoSuchElementException();
                }
                Item item = node.item;
                node = node.next;
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Object> deque = new Deque<>();

        System.out.println("####### isEmpty() & size() Testing #######");

        System.out.println("True, the deque is empty -> " + deque.isEmpty());
        System.out.println("Size must be 0 -> " + deque.size());

        System.out.println();
        System.out.println("Adding one element...");
        deque.addFirst(10);

        System.out.println();
        System.out.println("False, the deque is not empty -> " + deque.isEmpty());
        System.out.println("Size must be 1 -> " + deque.size());

        System.out.println();
        System.out.println("#######Adding & Removing Testing #######");

        deque.addFirst(20);
        System.out.println("Adding 20 at first...");
        System.out.println("Last element must be 10 -> " + deque.removeLast());

        System.out.println();
        deque.addLast(30);
        System.out.println("Adding 30 at last...");
        System.out.println("First element must be 20 -> " + deque.removeFirst());

        System.out.println();
        System.out.println("#######Iterator Testing #######");

        deque.addFirst(20);
        deque.addFirst(10);
        Iterator<Object> iterator = deque.iterator();
        System.out.println("deque should be 10 -> 20 -> 30");

        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
        System.out.println();
    }
}