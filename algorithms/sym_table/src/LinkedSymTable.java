/*
Associative array abstraction, associate one value with each key
assumes that the values are not null
 */

import java.util.Iterator;

// the linked list is inefficient and is only for demonstration purposes
public class LinkedSymTable <Key extends Comparable<Key>, Value> {
    
    private Node head;
    private int size;
    
    // node for singly linked list
    private class Node {
        public Key key;
        public Value val;
        public Node next;

        public Node(Key key, Value val, Node next) {
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public LinkedSymTable() {
        head = null;
        size = 0;
    }

    // overwirtes old value with new value
    public void put(Key key, Value val) {
        Node cur = head;

        // scan existing keys
        while (cur != null) {
            if (cur.key.equals(key)) {
                if (cur.val == null) {
                    ++size;
                }
                cur.val = val;
                return;
            }
            cur = cur.next;
        }

        // create new entry added to the front of list
        Node newNode = new Node(key, val, head);
        ++size;
        head = newNode;
        return;
    }

    // returns null if key not present
    public Value get(Key key) {
        Node cur = head;

        // scan existing keys
        while (cur != null) {
            if (cur.key.equals(key)) {
                return cur.val; // can be null
            }
            cur = cur.next;
        }
        return null;
    }

    public void delete(Key key) {
        // lazy implementation
        if (contains(key)) {
            put(key, null);
            --size;
        }
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    private class IterableKeys implements Iterable<Key> {

        private class IteratorKeys implements Iterator<Key> {
            private Node cur;

            public IteratorKeys() {
                cur = head;
                skipNullVal();
            }

            private void skipNullVal() {
                while (cur != null && cur.val == null) {
                    cur = cur.next;
                }
            }

            public boolean hasNext() {
                return cur != null;
            }

            public Key next() {
                Key key = cur.key;
                cur = cur.next;
                skipNullVal();
                return key;
            }
        }

        public Iterator<Key> iterator() {
            return new IteratorKeys();
        }
    }

    Iterable<Key> keys() {
        return new IterableKeys();
    }


    public static void main(String [] args) {
        LinkedSymTable<String, Integer> lst = new LinkedSymTable();
        String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 1; i < 10; ++i) {
            lst.put(letter.substring(i - 1, i), i);
        }
        lst.delete("E");
        lst.delete("B");
        lst.put("J", 10);
        lst.put("E", 5);
        lst.put("B", 2);
        Iterable<String> iter = lst.keys();
        for (String s : iter) {
            System.out.println("Key: " + s + " Value: " + lst.get(s));
        }
    }
}
