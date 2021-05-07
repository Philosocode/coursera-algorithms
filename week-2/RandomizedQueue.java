import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INIT_CAPACITY = 8;
    private Item[] q;       // queue elements
    private int n;          // number of elements on queue
    private int first;      // index of first element of queue
    private int last;       // index of next available slot

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
        first = 0;
        last = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < n; i++) {
            copy[i] = q[(first + i) % q.length];
        }

        q = copy;
        first = 0;
        last  = n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("item must not be null");

        // double size of array if necessary and recopy to front of array
        if (n == q.length) resize(2*q.length);   // double size of array if necessary
        q[last++] = item;                        // add item
        if (last == q.length) last = 0;          // wrap-around
        n++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");

        int randomIndex = StdRandom.uniform(0, n);
        randomIndex = (randomIndex + first) % q.length;

        Item removedItem = q[randomIndex];

        // swap randomIndex with first item. Preserving insertion order isn't important
        q[randomIndex] = q[first];

        // remove first item
        q[first] = null;
        n--;
        first++;

        if (first == q.length) first = 0;

        // shrink array size if necessary
        if (n > 0 && n == q.length/4) resize(q.length/2);

        return removedItem;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");

        int randomIndex = StdRandom.uniform(0, n);
        randomIndex = (randomIndex + first) % q.length;

        return q[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomArrayIterator();
    }

    private class RandomArrayIterator implements Iterator<Item> {
        private int i = 0;
        private Item[] qCopy;

        RandomArrayIterator() {
            qCopy = (Item[]) new Object[n];

            for (int i = 0; i < n; i++) {
                qCopy[i] = q[(i + first) % q.length];
            }

            StdRandom.shuffle(qCopy);
        }

        public boolean hasNext()  { return i < n;                               }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = qCopy[i];
            i++;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        /*
        RandomizedQueue rq = new RandomizedQueue<Integer>();

        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);

        for (int i = 0 ; i < 5; i++) {
            System.out.println(rq.dequeue());
        }
        */
    }
}