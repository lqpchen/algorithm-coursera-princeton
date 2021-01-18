/*
Garantees constant ops for pushes and pops, but takes extra space and time
maintaining the linked list.
 */

import java.util.Iterator;

public class LinkedStack<Type> implements Iterable<Type> {

    private class Node {
        Type item;
        Node next;
    }

    private Node head;
    private int sz;

    private class LinkedListIter implements Iterator<Type> {
        private Node iter = head;
        public boolean hasNext() {
            return iter != null;
        }

        public Type next() {
            Type elem = iter.item;
            iter = iter.next;
            return elem;
        }

        public void remove() {
            //
        }
    }

    public Iterator<Type> iterator() {
        return new LinkedListIter();
    }

    public LinkedStack() {
        head = null;
        sz = 0;
    }
    public void push(Type item) {
        Node oldHead = head;
        head = new Node();
        head.item = item;
        head.next = oldHead;
        ++sz;
    }

    public Type pop() {
        Type item = head.item;
        head = head.next;
        --sz;
        return item;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return sz;
    }

    // client code for testing purposes
    public static void main(String[] args)
    {
        LinkedStack<String> stack = new LinkedStack<String>();
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
