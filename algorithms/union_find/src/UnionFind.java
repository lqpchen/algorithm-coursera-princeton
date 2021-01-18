public class UnionFind {
    // this class implements WQUPC, i.e. weighted quick union path compression
    // a sequence of union-find ops costs log*(n) in amortized analysis

    private int [] pars; // pars[i] is the parent node id for node i
    private int [] sz; // sz[i] is the tree size rooted at node i
    private int cnt; // the number of components

    public UnionFind(int n) {
        pars = new int[n];
        sz = new int[n];
        for (int i = 0; i < pars.length; ++i) {
            pars[i] = i;
            sz[i] = 1;
        }
        cnt = n;
    }

    // implemented for quick find, deprecated
    void naive_union(int p, int q) {
        int root_p = find(p);
        int root_q = find(q);
        for (int i = 0; i < pars.length; ++i) {
            if (pars[i] == root_p) {
                pars[i] = root_q;
            }
        }
        if (root_p != root_q) {
            --cnt;
        }
    }

    // weighted quick union
    void quick_union(int p, int q) {
        int root_p = find(p);
        int root_q = find(q);

        if (root_p == root_q) {
            return;
        }

        // attach small tree to large tree rather than the reverse
        // makes the height to be O(lg2(n))
        if (sz[root_p] < sz[root_q]) {
            pars[root_p] = root_q;
            sz[root_q] += sz[root_p];
        }
        else {
            pars[root_q] = root_p;
            sz[root_p] += sz[root_q];
        }

        if (root_p != root_q) {
            --cnt;
        }
    }

    void union(int p, int q) {
        quick_union(p, q);
    }

    boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    int find(int i) {
        // path compression along the way
        while (i != pars[i]) {
            pars[i] = pars[pars[i]]; // not full compression but good enough in practice
            i = pars[i];
        }
        return i;
    }

    int count() {
        return cnt;
    }

    public static void main(String [] args) {
        UnionFind uf = new UnionFind(8);
        uf.union(1, 4);
        uf.union(5,1);
        System.out.println(uf.connected(4, 5)); // true
        uf.union(2, 3);
        uf.union(6,2);
        uf.union(7,3);
        System.out.println(uf.connected(2,7)); // true
        System.out.println(uf.connected(1,2)); // false
        uf.union(5,2);
        System.out.println(uf.connected(5,2)); // true
        System.out.println(uf.count() + " components");
        System.out.println("Answer: true true false true 2");
    }
}
