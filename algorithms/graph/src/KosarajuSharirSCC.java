/*
two-pass linear-time algorithm from Kosaraju-Sharir.
 */

public class KosarajuSharirSCC {

    private boolean[] marked;
    private int[] id;
    private int count; // #components

    public KosarajuSharirSCC(DiGraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        count = 0;
        TopologicalOrder ord = new TopologicalOrder(G.reverse());
        for (int v : ord.order()) {
            if (marked[v]) {
                continue;
            }
            dfs(G, v);
            ++count;
        }
    }

    private void dfs(DiGraph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v)) {
            if (marked[w]) {
                continue;
            }
            dfs(G, w);
        }
    }

    public boolean StronglyConnected(int v, int w) {
        return id(v) == id(w);
    }

    // number of connected components
    public int count() {
        return count;
    }

    // component identifier for v
    public int id(int v) {
        return id[v];
    }

    public void print() {
        for (int i = 0; i < id.length; ++i) {
            System.out.println(i + " " + id(i));
        }
    }

    public static void main(String [] args) {
        DiGraph dg = new DiGraph(13);
        int[] from = {0, 2, 6, 0, 2, 3, 4, 3, 4, 5, 6, 11, 6, 8, 7, 6, 7, 9, 9, 12, 10, 11};
        int[] to = {1, 0, 0, 5, 3, 2, 2, 5, 3, 4, 4, 4, 8, 6, 6, 9, 9, 10, 11, 9, 12, 12};
        for (int i = 0; i < from.length; ++i) {
            dg.addEdge(from[i], to[i]);
        }
        KosarajuSharirSCC scc = new KosarajuSharirSCC(dg);
        scc.print();
        System.out.println();
    }
}
