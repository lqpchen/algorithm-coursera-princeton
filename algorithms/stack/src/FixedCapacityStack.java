/*
the clients needs to provide a capacity, which breaks the modularity.
better resize the array automatically, i.e. use ResizingArrayStackOfStrings
 */

import java.util.Iterator;

public class FixedCapacityStack<Type> implements Iterable<Type> {

    private int capacity;
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

    public FixedCapacityStack(int cap) {
        capacity = cap;
        arr = (Type []) new Object [cap];
        head = 0;
    }

    public void push(Type item) {
        arr[head++] = item;
    }

    public Type pop() {
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
        FixedCapacityStack<String> stack = new FixedCapacityStack<String>(20);
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
