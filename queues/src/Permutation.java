import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        int outputCount = Integer.parseInt(args[0]);
        while (!StdIn.isEmpty()) {
            String input = StdIn.readString();
            if (queue.size() < outputCount) queue.enqueue(input);
            else if (StdRandom.bernoulli()) queue.dequeue(); queue.enqueue(input);
        }
        if (outputCount > 0) for (int i = 0; i < outputCount; i++) System.out.println(queue.dequeue());
    }
}
