/*
maximum priority queue implementation based on binary heap
which is more efficient than unordered array or linked list
(Java has an PriorityQueue class but it's min priority queue)
 */

public class PriorityQueue <Key extends Comparable<Key>> {

    private BinaryHeap<Key> heap;

    public PriorityQueue() {
        heap = new BinaryHeap<Key>();
    }

    public PriorityQueue(Key [] keys) {
        heap = new BinaryHeap<Key>();
        for (Key k : keys) {
            heap.insert(k);
        }
    }

    public void insert(Key k) {
        heap.insert(k);
    }

    public Key delMax() {
        return heap.delMax();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public Key max() {
        return heap.max();
    }

    public int size() {
        return heap.size();
    }

    public void printAll() {
        heap.printAll();
    }

    public static void main(String [] args) {
        PriorityQueue<String> pq = new PriorityQueue<String>();
        String [] input = {"D", "C", "B", "A", "E", "F", "Z", "X", "P", "M", "O", "Q"};
        for (String s : input) {
            pq.insert(s);
        }
        pq.printAll();

        while (!pq.isEmpty()) {
            System.out.print(pq.delMax() +" ");
        }
        System.out.println();
    }
}
