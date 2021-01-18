/*
tree based symbol table supports ordered operations. this particular implementation
is based on BST for demonstration purposes, since RBT is a better alternative
that is guaranteed to be more balanced so that the height ~ O(log(n))
 */

import java.util.Random;

public class TreeSymTable<Key extends Comparable<Key>, Value> {
    private BST<Key, Value> bst;

    public TreeSymTable() {
        bst = new BST<>();
    }

    public void put(Key key, Value value) {
        bst.put(key, value);
    }

    public Value get(Key key) {
        return bst.get(key);
    }

    public void delMin() {
        bst.delMin();
    }

    public void delMax() {
        bst.delMax();
    }

    public void delete(Key key) {
        bst.delete(key);
    }

    public boolean contains(Key key) {
        return bst.contains(key);
    }

    public boolean isEmpty() {
        return bst.isEmpty();
    }

    private int size() {
        return bst.size();
    }

    public Key min() {
        return bst.min();
    }

    public Key max() {
        return bst.max();
    }

    // return the largest key that is smaller than key
    public Key floor(Key key) {
        return bst.floor(key);
    }

    // return the smallest key that is greater than key
    public Key ceiling(Key key) {
        return bst.ceiling(key);
    }

    // returns the #nodes < key
    public int rank(Key key) {
        return bst.rank(key);
    }

    // return the k-th smallest key
    public Key select(int k) {
        return bst.select(k);
    }

    public Iterable<Key> iterable() {
        return bst.iterable();
    }

    public static void main(String [] args) {
        TreeSymTable<String, Integer> st = new TreeSymTable();
        String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        for (int i = 1; i <= 10; ++i) {
            int r = rand.nextInt(26);
            st.put(letter.substring(r, r + 1), r + 1);
        }

        for (String s : st.iterable()) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}
