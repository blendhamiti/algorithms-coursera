import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int itemCount;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        itemCount = 0;
    }

    private class Node {
        public Item item = null;
        public Node next = null;
        public Node prev = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return itemCount == 0;
    }

    // return the number of items on the deque
    public int size() {
//        if (isEmpty()) {
//            return 0;
//        }
//        else if (first == last) {
//            return 1;
//        }
//        else {
//            int count = 2;
//            Node node = first;
//            while (node.next.next != null) {
//                node = node.next;
//                count++;
//            }
//            return count;
//        }
        return itemCount;
    }

    // add the item to the front
    public void addFirst(Item item)  {
        if (item == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
        }
        else {
            Node oldFirst = first;
            first = new Node();
            first.item = item;
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        itemCount++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (isEmpty()) {
            first = new Node();
            first.item = item;
            last = first;
        }
        else {
            Node oldLast = last;
            last = new Node();
            last.item = item;
            last.prev = oldLast;
            oldLast.next = last;
        }
        itemCount++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        if (size() == 1) {
            Item item = first.item;
            itemCount--;
            first = null;
            last = null;
            return item;
        }
        else {
            Item item = first.item;
            itemCount--;
            first = first.next;
            first.prev = null;
            return item;
        }

    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        if (size() == 1) {
            Item item = first.item;
            itemCount--;
            first = null;
            last = null;
            return item;
        }
        else {
            Item item = last.item;
            itemCount--;
            last = last.prev;
            last.next = null;
            return item;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        Node node = first;

        @Override
        public boolean hasNext() {
            return node != null;
        }

        @Override
        public Item next() {
            if (node == null) throw new NoSuchElementException();
            Item item = node.item;
            node = node.next;
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
//        Deque<String> deque = new Deque<>();
//        System.out.println(deque.size());
//        deque.addFirst(st1);
//        deque.addFirst(st2);
//        deque.addLast(st3);
//        deque.addFirst(st4);
//        deque.addLast(st5);
//        deque.removeLast();
//        deque.addLast(st5);
//        deque.removeFirst();
//        deque.addFirst(st5);
//        System.out.println(deque.size());
//        Iterator<String> iterator = deque.iterator();
//        System.out.println("Iterator:");
//        while (iterator.hasNext())
//            System.out.println(iterator.next());
//        System.out.println(iterator.next());
    }
}
