import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {

    private final Deque<Item> q;

    public Bag() {
        q = new Deque<>();
    }

    public void add(Item x) {
        q.addLast(x);
    }

    public int size() {
        return q.size();
    }

    public Iterator<Item> iterator() {
        return q.iterator();
    }

    // unit testing
    public static void main(String[] args) {
        String [] inputs = {"aa", "bb", "cc", "dd"};
        Bag<String> bag = new Bag<>();
        bag.add(inputs[0]);
        bag.add(inputs[1]);
        bag.add(inputs[2]);
        bag.add(inputs[3]);
        System.out.println("size: " + bag.size());

        // iterator
        for (String s : bag) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}
