/*
LLRB (left leaning red-black tree), an easy implementation of 2-3 tree
guaranteed O(n*log(n)) performance for all operations, thus preferred over BST
most ops are same as BST but benefit from perfect balance from RBT

Note: deletion not implemented since it's complex and not covered in videos or books
 */

import java.util.Random;

public class RBT<Key extends Comparable<Key>, Value> {
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private Node root;

    private class Node {
        public Key key;
        public Value value;
        public Node left;
        public Node right;
        private int count; // #nodes in sub-tree rooted at this node, in the sense of BST
        private boolean color; // color of parent link

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.count = 1;
            this.color = RED;
        }
    }

    private boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        return node.color == RED;
    }

    // orient a temporarily right-leaning red link to lean left
    private Node rotateLeft(Node cur) {
        assert isRed(cur.right);
        Node node = cur.right;
        cur.right = node.left;
        node.left = cur;
        node.color = cur.color;
        cur.color = RED;

        cur.count = 1 + size(cur.left) + size(cur.right);
        node.count = 1 + size(node.left) + size(node.right);

        return node;
    }

    // orient a left-leaning red link to lean right temporarily
    private Node rotateRight(Node cur) {
        assert isRed(cur.left);

        Node node = cur.left;
        cur.left = node.right;
        node.right = cur;
        node.color = cur.color;
        cur.color = RED;

        cur.count = 1 + size(cur.left) + size(cur.right);
        node.count = 1 + size(node.left) + size(node.right);

        return node;
    }

    // to bring up the middle node from the
    private void filpColor(Node cur) {
        assert !isRed(cur);
        assert isRed(cur.left);
        assert isRed(cur.right);

        cur.color = RED;
        cur.left.color = BLACK;
        cur.right.color = BLACK;
    }

    private Node put(Node cur, Key key, Value value) {
        if (cur == null) {
            return new Node(key, value);
        }
        int cmp = cur.key.compareTo(key);
        if (cmp > 0) {
            cur.left = put(cur.left, key, value);
        } else if (cmp < 0) {
            cur.right = put(cur.right, key, value);
        } else { // cmp == 0
            cur.value = value;
        }

        // transformation to maintain the RBT
        if (isRed(cur.right) && !isRed(cur.left)) {
            cur = rotateLeft(cur);
        }
        if (isRed(cur.left) && isRed(cur.left.left)) {
            cur = rotateRight(cur);
        }
        if (isRed(cur.left) && isRed(cur.right)) {
            filpColor(cur);
        }

        cur.count = 1 + size(cur.left) + size(cur.right);
        return cur;
    }

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    // returns null if key not present
    public Value get(Key key) {
        Node cur = root;
        int cmp;
        while (cur != null) {
            cmp = cur.key.compareTo(key);
            if (cmp == 0) {
                return cur.value;
            } else if (cmp < 0) {
                cur = cur.right;
            } else if (cmp > 0) {
                cur = cur.left;
            }
        }
        return null;
    }

    public void delete(Key key) {
        // not covered in videos and textbooks, bit more complicated
    }
    // return the minimum key
    public Key min() {
        Node cur = root;
        // find the leftmost key
        while (cur != null) {
            if (cur.left == null) {
                return cur.key;
            }
            cur = cur.left;
        }
        return null;
    }

    public Key max() {
        Node cur = root;
        // find rightmost key
        while (cur != null) {
            if (cur.right == null) {
                return cur.key;
            }
            cur = cur.right;
        }
        return null;
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    // return the node with minimum key rooted at cur
    private Node min(Node cur) {
        // find the leftmost key
        while (cur != null) {
            if (cur.left == null) {
                return cur;
            }
            cur = cur.left;
        }
        return null;
    }

    // return the node having floor(key)
    private Node floor(Node cur, Key key) {
        if (cur == null) {
            return null;
        }
        int cmp = cur.key.compareTo(key);
        if (cmp == 0) {
            return cur;
        } else if (cmp > 0) { // floor must be in left sub-tree
            return floor(cur.left, key);
        } else {
            Node floorNode = floor(cur.right, key);
            if (floorNode != null) {
                return floorNode;
            } else {
                return cur;
            }
        }
    }

    public Key floor(Key key) {
        Node floorNode = floor(root, key);
        if (floorNode != null) {
            return floorNode.key;
        } else {
            return null;
        }
    }

    private int size(Node node) {
        if (node == null) {
            return 0;
        }
        return node.count;
    }

    public int size() {
        return size(root);
    }


    // return the node having ceiling(key)
    private Node ceiling(Node cur, Key key) {
        if (cur == null) {
            return null;
        }
        int cmp = cur.key.compareTo(key);
        if (cmp == 0) {
            return cur;
        } else if (cmp < 0) {
            return ceiling(cur.right, key);
        } else {
            Node ceilNode = ceiling(cur.left, key);
            if (ceilNode != null) {
                return ceilNode;
            } else {
                return cur;
            }
        }
    }

    public Key ceiling(Key key) {
        Node ceilNode = ceiling(root, key);
        if (ceilNode != null) {
            return ceilNode.key;
        } else {
            return null;
        }
    }

    // returns the #nodes in sub-tree rooted at cur having < key
    private int rank(Node cur, Key key) {
        if (cur == null) {
            return 0;
        }
        int cmp = cur.key.compareTo(key);
        if (cmp == 0) {
            return size(cur.left);
        } else if (cmp > 0) {
            return rank(cur.left, key);
        } else {
            return 1 + size(cur.left) + rank(cur.right, key);
        }
    }

    // returns the #nodes < key
    public int rank(Key key) {
        return rank(root, key);
    }

    private Node select(Node cur, int k) {
        if (cur == null) {
            return null;
        }
        if (size(cur.left) == k - 1) {
            return cur;
        } else if (size(cur.left) < k - 1) {
            return select(cur.right, k - 1 - size(cur.left));
        } else {
            return select(cur.left, k);
        }
    }

    // return the k-th smallest key
    public Key select(int k) {
        Node kNode = select(root, k);
        if (kNode == null) {
            return null;
        }
        return kNode.key;
    }

    private void inorder(Node cur, Deque<Key> queue) {
        if (cur == null) {
            return;
        }
        inorder(cur.left, queue);
        queue.addLast(cur.key);
        inorder(cur.right, queue);
    }

    public Iterable<Key> iterator() {
        Deque<Key> queue = new Deque<>();
        inorder(root, queue);
        return queue;
    }

    public static void main(String [] args) {
        RBT<String, Integer> rbt = new RBT();
        String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        for (int i = 1; i <= 100; ++i) {
            int r = rand.nextInt(26);
            rbt.put(letter.substring(r, r + 1), r + 1);
        }
        
        Iterable<String> iter = rbt.iterator();
        for (String s : iter) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}
