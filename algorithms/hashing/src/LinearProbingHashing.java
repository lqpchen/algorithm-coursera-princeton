import java.util.Iterator;

public class LinearProbingHashing<Key, Value> {
    private int M; // table length
    private Node[] hashTable;
    private int sz; // sz/M ~ 1/2 for optimal performance

    private static class Node {
        public Object key;
        public Object value;

        public Node(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }

    public LinearProbingHashing() {
        M = 97; // initial length
        hashTable = new Node[M];
        sz = 0;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public void put(Key key, Value value) {
        int idx = hash(key);
        while (hashTable[idx] != null) {
            if (hashTable[idx].key.equals(key)) {
                hashTable[idx].value = value;
                return;
            }
            idx = (idx + 1) % M;
        }
        hashTable[idx] = new Node(key, value);
        ++sz;
    }

    public Value get(Key key) {
        int idx = hash(key);
        while (hashTable[idx] != null) {
            if (hashTable[idx].key.equals(key)) {
                return (Value) hashTable[idx].value;
            }
            idx = (idx + 1) % M;
        }
        return null;
    }

    public void delete(Key key) {
        // to do, a bit complicated
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
                    keys[cnt++] = (Key) hashTable[i].key;
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
            return new KeyIterable.KeyIter();
        }
    }

    public Iterable<Key> keys() {
        return new KeyIterable();
    }

    public static void main(String [] args) {
        LinearProbingHashing<String, Integer> ht = new LinearProbingHashing();
        String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 1; i < 10; ++i) {
            ht.put(letter.substring(i - 1, i), i);
        }
        //ht.delete("E");
        //ht.delete("B");
        ht.put("J", 10);
        ht.put("E", 5);
        ht.put("B", 2);
        for (String s : ht.keys()) {
            System.out.println("Key: " + s + " Value: " + ht.get(s));
        }
    }
}
