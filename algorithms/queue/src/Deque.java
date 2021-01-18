/*
double-ended queue, a generalization of a stack and a queue.
implemented using doubly-linked list
see more info at: https://coursera.cs.princeton.edu/algs4/assignments/queues/specification.php
 */

//import edu.princeton.cs.algs4.System.out.;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        public Item data;
        public Node next; // refer to the next Node
        public Node prev; // refer to the previous Node
    }

    private class ListIterator implements Iterator<Item> {
        Node cur = head;

        public boolean hasNext() {
            return cur != null;
        }

        public Item next() {
            if (cur == null) {
                throw new NoSuchElementException("No more entry!");
            }
            Item data = cur.data;
            cur = cur.next;
            return data;
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported!");
        }
    }

    private Node head; // refer to the front
    private Node tail; // refer to the back
    private int sz; // number of Nodes in the deque

    // construct an empty deque
    public Deque() {
        head = null;
        tail = null;
        sz = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return sz == 0;
    }

    // return the number of items on the deque
    public int size() {
        return sz;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Added item can't be null!");
        }

        Node oldHead = head;
        head = new Node();
        head.data = item;
        head.prev = null;
        head.next = oldHead;
        if (oldHead != null) { // non-empty deque
            oldHead.prev = head;
        } else {
            tail = head;
        }

        ++sz;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Added item can't be null!");
        }

        Node oldTail = tail;
        tail = new Node();
        tail.data = item;
        tail.next = null;
        tail.prev = oldTail;
        if (oldTail != null) { // non-empty deque
            oldTail.next = tail;
        } else { // empty deque
            head = tail;
        }

        ++sz;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (size() == 0) {
            throw new NoSuchElementException("The deque is empty!");
        }

        Item data = head.data;
        head.data = null;

        if (size() == 1) {
            head = null;
            tail = null;
        } else { // size() >= 2
            head = head.next;
            head.prev.next = null;
            head.prev = null;
        }

        --sz;
        return data;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (size() == 0) {
            throw new NoSuchElementException("The deque is empty!");
        }

        Item data = tail.data;
        tail.data = null;

        if (size() == 1) {
            head = null;
            tail = null;
        } else { // size() >= 2
            tail = tail.prev;
            tail.next.prev = null;
            tail.next = null;
        }

        --sz;
        return data;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        String [] inputs = {"aa", "bb", "cc", "dd"};
        Deque<String> dq = new Deque<String>();
        System.out.println("isEmpty: " + dq.isEmpty());
        dq.addFirst(inputs[0]);
        dq.addFirst(inputs[1]);
        dq.addLast(inputs[2]);
        dq.addLast(inputs[3]);
        System.out.println("size: " + dq.size());

        // iterator
        for (String s : dq) {
            System.out.print(s + " ");
        }
        System.out.println();

        System.out.println("remove first: " + dq.removeFirst());
        System.out.println("remove last: " + dq.removeLast());
        System.out.println("remove first: " + dq.removeFirst());
        System.out.println("remove last: " + dq.removeLast());

        System.out.println("size: " + dq.size());
    }
}
