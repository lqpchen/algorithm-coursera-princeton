/*
array representation of a heap-ordered complete binary tree
 */

public class BinaryHeap <Key extends Comparable<Key>> {

    private Key [] nodes;
    private int size;

    public BinaryHeap() {
        nodes = (Key []) new Comparable[2];
        nodes[0] = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(Key node) {
        if (size == nodes.length - 1) {
            resize(size*2 + 1);
        }
        nodes[++size] = node;
        swim(size);
    }

    public Key max() {
        return nodes[1];
    }

    public Key delMax() {
        Key max = nodes[1];
        nodes[1] = nodes[size];
        nodes[size--] = null;
        sink(1);
        if (size*4 <= nodes.length - 1 && size > 0) {
            resize(size*2 + 1);
        }
        return max;
    }

    private void resize(int sz) {
        Key [] copy = (Key []) new Comparable[sz];
        for (int i = 1; i <= size; ++i) {
            copy[i] = nodes[i];
        }
        nodes = copy;
    }

    private void swim(int idx) {
        while (idx/2 >= 1 && less(idx/2, idx)) {
            exch(idx, idx/2);
            idx/=2;
        }
    }

    private void sink(int idx) {
        while (idx*2 <= size) {
            idx*=2;
            if (idx + 1 <= size && less(idx, idx + 1)) {
                ++idx;
            }
            if (less(idx/2, idx)) {
                exch(idx/2, idx);
            }
        }
    }

    private boolean less(int i, int j) {
        return nodes[i].compareTo(nodes[j]) < 0;
    }

    private void exch(int i, int j) {
        Key temp = nodes[i];
        nodes[i] = nodes[j];
        nodes[j] = temp;
    }

    public void printAll() {
        for (int i = 1; i <= size; ++i) {
            System.out.print(nodes[i] + " ");
        }
        System.out.println();
    }

    public static void main(String [] args) {
        BinaryHeap<String> bh = new BinaryHeap<String>();
        String [] input = {"D", "C", "B", "A", "E", "F", "Z", "X", "P", "M", "O", "Q"};
        for (String s : input) {
            bh.insert(s);
        }
        bh.printAll();

        while (!bh.isEmpty()) {
            System.out.print(bh.delMax() +" ");
        }
        System.out.println();
    }
}
