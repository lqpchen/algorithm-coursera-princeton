/*
symbol table implementation based on hashing table
 */

public class HashSymTable<Key, Value> {

    private SeparateChainingHashing<Key, Value> ht;

    public HashSymTable() {
        ht = new SeparateChainingHashing<>();
    }

    public void put(Key key, Value value) {
        ht.put(key, value);
    }

    public Value get(Key key) {
        return ht.get(key);
    }

    public void delete(Key key) {
        ht.delete(key);
    }

    public boolean contains(Key key) {
        return ht.contains(key);
    }

    public boolean isEmpty() {
        return ht.isEmpty();
    }

    public int size() {
        return ht.size();
    }

    public Iterable<Key> keys() {
        return ht.keys();
    }

    public static void main(String [] args) {
        HashSymTable<String, Integer> st = new HashSymTable();
        String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (int i = 1; i < 10; ++i) {
            st.put(letter.substring(i - 1, i), i);
        }
        st.delete("E");
        st.delete("B");
        st.put("J", 10);
        st.put("E", 5);
        st.put("B", 2);
        for (String s : st.keys()) {
            System.out.println("Key: " + s + " Value: " + st.get(s));
        }
    }
}
