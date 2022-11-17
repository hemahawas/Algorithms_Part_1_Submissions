import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args){

        RandomizedQueue<Object> randomizedQueue = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);

        while(!StdIn.isEmpty()){
            String s = StdIn.readString();
            randomizedQueue.enqueue(s);
        }

        Iterator<Object> iterator = randomizedQueue.iterator();
        for (int i = 0; i < k; i++) {
            System.out.println(iterator.next());
        }


    }
}