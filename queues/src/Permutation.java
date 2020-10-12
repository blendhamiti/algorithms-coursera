import java.util.Iterator;

public class Permutation {

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
//        int k = 3;
//        for (int i = 0; i < k; i++) queue.enqueue(StdIn.readString());
        for (int i = 0; i < Integer.parseInt(args[0]); i++) queue.enqueue(StdIn.readString());
        Iterator<String> it = queue.iterator();
        while (it.hasNext())
            System.out.println(it.next());
    }
}
