import java.util.Iterator;

public class ResizingArrayStack<Type> implements Iterable<Type> {

    private Type [] arr;
    private int head;

    private class ReverseArrayIter implements Iterator<Type> {
        int cur = head;
        public boolean hasNext() {
            return cur > 0;
        }

        public Type next() {
            return arr[--cur];
        }

        public void remove() {
            //
        }
    }
    public Iterator<Type> iterator() {
        return new ReverseArrayIter();
    }

    public ResizingArrayStack() {
        arr = (Type [])new Object [1];
        head = 0;
    }

    private void resize(int cap) {
        Type [] copy = (Type [])new Object[cap];
        int bound = Math.min(cap, arr.length);
        for (int i = 0; i < bound; ++i) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    public void push(Type item) {
        if (size() == arr.length) {
            resize(2*arr.length);
        }
        arr[head++] = item;
    }

    public Type pop() {
        if (size() == arr.length/4) {
            resize(arr.length/2);
        }
        Type item = arr[--head];
        arr[head] = null; // avoid loitering
        return item;
    }

    public boolean isEmpty() {
        return head == 0;
    }

    public int size() {
        return head;
    }

    // client code for testing purposes
    public static void main(String[] args)
    {
        ResizingArrayStack<String> stack = new ResizingArrayStack<String>();
        String strs [] = {"to", "be", "or", "not", "to", "-", "be", "-", "-", "that", "-", "-", "-", "is"};
        for (String s : strs) {
            if (s.equals("-")) {
                System.out.print(stack.pop() + " ");
            }
            else {
                stack.push(s);
            }
        }
        System.out.println();
        System.out.println("Correct answer: to be not that or be");

        for (String s : stack) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}
