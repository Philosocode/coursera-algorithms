import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

// REFERENCED: https://algs4.cs.princeton.edu/13stacks/DoublyLinkedList.java.html
public class Deque<Item> implements Iterable<Item> {
    private int n;        // number of elements on list
    private Node head;
    private Node tail;

    // linked list node helper data type
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    public Deque() {
        head = null;
        tail = null;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("item must not be null");

        Node oldHead = head;

        Node newHead = new Node();
        newHead.item = item;
        newHead.next = oldHead;
        newHead.prev = null;

        if (oldHead != null) {
            oldHead.prev = newHead;
        }
        // if this is the first node, set tail == to head as well
        else {
            tail = newHead;
        }

        head = newHead;

        n++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("item must not be null");

        Node oldTail = tail;

        //  old < new
        Node newTail = new Node();
        newTail.item = item;
        newTail.next = null;
        newTail.prev = oldTail;

        if (oldTail != null) {
            oldTail.next = newTail;
        }
        // if this is the first node, set head == tail
        else {
            head = newTail;
        }

        tail = newTail;

        n++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");

        Item removedItem = head.item;

        head = head.next;
        n--;

        // update pointer
        if (head != null) {
            head.prev = null;
        }

        if (isEmpty()) tail = null;

        return removedItem;
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");

        Item removedItem = tail.item;
        tail = tail.prev;
        n--;

        // update pointer
        if (tail != null) {
            tail.next = null;
        }

        if (isEmpty()) head = null;

        return removedItem;
    }

    public Iterator<Item> iterator() {
        return new LinkedIterator(head);
    }

    // an iterator, doesn't implement remove() since it's optional
    private class LinkedIterator implements Iterator<Item> {
        private Node current;

        public LinkedIterator(Node first) {
            current = first;
        }

        public boolean hasNext()  { return current != null;                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;

            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        /*
        Deque d = new Deque<Integer>();

        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);

        d.addLast(4);
        d.addLast(5);
        d.addLast(6);

        d.removeLast();
        d.removeLast();
        d.removeLast();
        d.removeLast();
        d.removeLast();
        d.removeLast();

        for (Object item : d) {
            StdOut.println(item);
        }
        */
    }
}