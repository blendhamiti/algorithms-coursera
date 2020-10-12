import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        size = 0;
    }

    private void resizeArray(int newCapacity) {
        Item[] oldQueue = queue;
        queue = (Item[]) new Object[newCapacity];
        for (int i = 0; i < size; i++)
            queue[i] = oldQueue[i];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return false;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (size == queue.length) resizeArray(queue.length * 2);
        queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size == 0) throw new NoSuchElementException();
        if (size > 0 && size == queue.length / 4) resizeArray(queue.length / 2);
        int randomInt = StdRandom.uniform(size);
        Item item = queue[randomInt];
        queue[randomInt] = queue[--size];
        queue[size] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size == 0) throw new NoSuchElementException();
        return queue[StdRandom.uniform(size)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;
        private int[] indices;

        public RandomizedQueueIterator() {
            indices = new int[size];
            for (int j = 0; j < size; j++) indices[j] = j;
            StdRandom.shuffle(indices);
        }

        @Override
        public boolean hasNext() {
            return i < size;
        }

        @Override
        public Item next() {
            Item item = queue[indices[i++]];
            if (item == null) throw new NoSuchElementException();
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
//        String st1 = new String("string1");
//        String st2 = new String("string2");
//        String st3 = new String("string3");
//        String st4 = new String("string4");
//        String st5 = new String("string5");
//        RandomizedQueue<String> queue = new RandomizedQueue<>();
//
//        queue.enqueue(st1);
//        queue.enqueue(st3);
//        queue.enqueue(st2);
//        System.out.println(queue.isEmpty());
//        System.out.println(queue.size());
//        System.out.println(queue.dequeue());
//        System.out.println(queue.sample());

//        System.out.println(queue.dequeue());
//        System.out.println(queue.dequeue());
//        System.out.println(queue.sample());


//        Iterator<String> iterator = queue.iterator();
//        Iterator<String> iterator2;
//        System.out.println("Iterator:");
//        while (iterator.hasNext()) {
//            String strIt = iterator.next();
//            iterator2 = queue.iterator();
//            while (iterator2.hasNext())
//                System.out.println(strIt + " " + iterator2.next());
//        }
    }

}
