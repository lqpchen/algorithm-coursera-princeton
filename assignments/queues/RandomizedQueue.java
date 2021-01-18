/*
queue that pop items at random, implemented using array
 */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item [] arr;
    private int head;
    private int tail;
    private int sz;

    private class RandomIterator implements Iterator<Item> {
        private final Item [] rand;
        private int remain;

        public RandomIterator() {
            rand = (Item []) new Object[sz];
            remain = rand.length;
            int randIdx;
            Item tmp; // swapping
            for (int i = 0; i < sz; ++i) {
                randIdx = StdRandom.uniform(sz - i);
                rand[i] = arr[(tail + i + randIdx) % arr.length];

                // swapping
                tmp = arr[(tail + i + randIdx) % arr.length];
                arr[(tail + i + randIdx) % arr.length] = arr[(tail + i) % arr.length];
                arr[(tail + i) % arr.length] = tmp;
            }
        }

        public boolean hasNext() {
            return remain > 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("The deque is empty!");
            }
            return rand[--remain];
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove not supported!");
        }
    }

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item []) new Object[1];
        head = 0;
        tail = 0;
        sz = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return sz == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return sz;
    }

    private void resize(int cap) {
        Item [] copy = (Item []) new Object[cap];
        int bound = Math.min(cap, arr.length);
        for (int i = 0; i < bound; ++i) {
            copy[i] = arr[(tail + i) % arr.length];
        }
        arr = copy;
        tail = 0;
        head = sz;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Added item can't be null!");
        }

        if (size() == arr.length) {
            resize(arr.length*2);
        }
        arr[head] = item;
        head = (head + 1) % arr.length;
        ++sz;
    }

    // remove and return a random item
    public Item dequeue() {
        if (size() == 0) {
            throw new NoSuchElementException("The deque is empty!");
        }

        if (size() == arr.length/4) {
            resize(arr.length/2);
        }

        int randIdx = StdRandom.uniform(sz);
        Item tmp; // swapping
        Item data = arr[(tail + randIdx) % arr.length];

        // swapping
        tmp = arr[(tail + randIdx) % arr.length];
        arr[(tail + randIdx) % arr.length] = arr[(tail) % arr.length];
        arr[(tail) % arr.length] = tmp;

        ++tail;
        --sz;
        return data;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (size() == 0) {
            throw new NoSuchElementException("The deque is empty!");
        }

        int randIdx = StdRandom.uniform(sz);
        return arr[(tail + randIdx) % arr.length];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        String [] inputs = {"aa", "bb", "cc", "dd", "ee", "ff", "gg", "hh"};
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        StdOut.println("isEmpty: " + rq.isEmpty());
        rq.enqueue(inputs[0]);
        rq.enqueue(inputs[1]);
        rq.enqueue(inputs[2]);
        rq.enqueue(inputs[3]);
        rq.enqueue(inputs[4]);
        rq.enqueue(inputs[5]);
        rq.enqueue(inputs[6]);
        rq.enqueue(inputs[7]);
        StdOut.println("size: " + rq.size());

        // iterator

        for (String s : rq) {
            StdOut.print(s + " ");
        }
        StdOut.println();

        StdOut.println("remove: " + rq.dequeue());
        StdOut.println("remove: " + rq.dequeue());
        StdOut.println("remove: " + rq.dequeue());
        StdOut.println("remove: " + rq.dequeue());

        StdOut.println("size: " + rq.size());
    }
}
