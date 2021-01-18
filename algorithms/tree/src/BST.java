/*
Binary search tree
Assumptions: values are not null
 */

import java.util.Random;

public class BST<Key extends Comparable<Key>, Value> {

    private Node root;

    private class Node {
        public Key key;
        public Value value;
        public Node left;
        public Node right;
        private int count; // #nodes in sub-tree rooted at this node

        public Node(Key key, Value value) {
            this.key = key;
            this.value = value;
            this.count = 1;
        }
    }

    // concise recursive code
    // return the updated sub-tree root (cur) with the new key-value pair
    private Node put(Node cur, Key key, Value value) {
        // base case
        if (cur == null) {
            return new Node(key, value);
        }
        // recursive search
        int cmp = cur.key.compareTo(key);
        if (cmp == 0) {
            cur.value = value;
        } else if (cmp > 0) { // search for left sub-tree
            cur.left = put(cur.left, key, value);
        } else if (cmp < 0) { // search for right sub-tree
            cur.right = put(cur.right, key, value);
        }
        // update the count for the current node before tracing back
        cur.count = 1 + size(cur.left) + size(cur.right);
        return cur;
    }

    // overwrites old value with new value
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

    // update the subtree rooted at cur with min node deleted
    private Node delMin(Node cur) {
        if (cur.left == null) {
            return cur.right;
        }
        cur.left = delMin(cur.left);
        cur.count = 1 + size(cur.left) + size(cur.right);
        return cur;
    }

    // delete the node with the minimum key
    public void delMin() {
        if (root == null) {
            return;
        }
        root = delMin(root);
    }

    private Node delMax(Node cur) {
        if (cur.right == null) {
            return cur.left;
        }
        cur.right = delMax(cur.right);
        cur.count = 1 + size(cur.left) + size(cur.right);
        return cur;
    }

    public void delMax() {
        if (root == null) {
            return;
        }
        root = delMax(root);
    }

    // return the updated node (cur) with key deleted in its subtree
    private Node delete(Node cur, Key key) {
        if (cur == null) {
            return null;
        }
        int cmp = cur.key.compareTo(key);
        if (cmp == 0) { // found the node to delete
            // case 0 and case 1
            if (cur.left == null) {
                return cur.right;
            } else if (cur.right == null) {
                return cur.left;
            }
            // case 2
            Node tmp = cur;
            cur = min(tmp.right);
            cur.left = tmp.left;
            cur.right = delMin(tmp.right);
        } else if (cmp > 0) {
            cur.left = delete(cur.left, key);
        } else {
            cur.right = delete(cur.right, key);
        }
        cur.count = 1 + size(cur.left) + size(cur.right);
        return cur;
    }

    // delete node with key
    // large number of deletion makes the height ~ sqrt(n)
    public void delete(Key key) {
        root = delete(root, key);
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public boolean isEmpty() {
        return root == null;
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

    private Node nextInOrder(Node cur) {
        cur = cur.right;
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

    // return the node with k-th smallest key from sub-tree rooted at cur
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

    public Iterable<Key> iterable() {
        Deque<Key> queue = new Deque<>();
        inorder(root, queue);
        return queue;
    }

    public static void main(String [] args) {
        BST<String, Integer> bst = new BST();
        String letter = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        for (int i = 1; i <= 10; ++i) {
            int r = rand.nextInt(26);
            bst.put(letter.substring(r, r + 1), r + 1);
        }
        Iterable<String> iter = bst.iterable();
        for (String s : iter) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}

