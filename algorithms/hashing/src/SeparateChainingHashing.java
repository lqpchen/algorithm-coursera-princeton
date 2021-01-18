import java.util.Iterator;

public class SeparateChainingHashing<Key, Value> {

    private int M; // table length
    private Node [] hashTable;
    private int sz; // M ~ sz/5 for best performance

    private static class Node {
        public Object key;
        public Object value;
        public Node next;

        public Node(Object key, Object value, Node next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public SeparateChainingHashing() {
        M = 97; // initial length
        hashTable = new Node[M];
        sz = 0;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public void put(Key key, Value value) {
        int idx = hash(key);
        Node node = hashTable[idx];
        for(; node != null; node = node.next) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }
        hashTable[idx] = new Node(key, value, node);
        ++sz;
    }

    public Value get(Key key) {
        for(Node node = hashTable[hash(key)]; node != null; node = node.next) {
            if (node.key.equals(key)) {
                return (Value) node.value;
            }
        }
        return null;
    }

    public void delete(Key key) {
        int idx = hash(key);
        Node node = hashTable[idx], prev = node;
        for(; node != null; node = node.next) {
            if (!node.key.equals(key)) {
                prev = node;
                continue;
            }
            if (node == hashTable[idx]) {
                hashTable[idx] = node.next;
            } else {
                prev.next = node.next;
            }
            --sz;
            return;
        }
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return sz == 0;
    }

    public int size() {
        return sz;
    }

    private class KeyIterable implements Iterable<Key> {
        private class KeyIter implements Iterator<Key> {
            private Key [] keys;
            private int cnt;
            private int iter;

            public KeyIter() {
                cnt = 0;
                iter = 0;
                keys = (Key []) new Object[sz];
                for (int i = 0; i < M; ++i) {
                    if (hashTable[i] == null) {
                        continue;
                    }
                    Node node = hashTable[i];
                    while (node != null) {
                        keys[cnt++] = (Key) node.key;
                        node = node.next;
                    }
                }
            }

            public boolean hasNext() {
                return iter < sz;
            }

            public Key next() {
                return keys[iter++];
            }

            public void remove() {
                //
            }
        }

        public Iterator<Key> iterator() {
            return new KeyIter();
        }
    }

    public Iterable<Key> keys() {
        return new KeyIterable();
    }

    public static void main(String [] args) {
        SeparateChainingHashing<String, Integer> ht = new SeparateChainingHashing();
        String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 1; i < 10; ++i) {
            ht.put(letter.substring(i - 1, i), i);
        }
        ht.delete("E");
        ht.delete("B");
        ht.put("J", 10);
        ht.put("E", 5);
        ht.put("B", 2);
        for (String s : ht.keys()) {
            System.out.println("Key: " + s + " Value: " + ht.get(s));
        }
    }

}
