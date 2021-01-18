public class ConnectedComponents {

    private boolean[] marked;
    private int[] id;
    private int count; // #components

    public ConnectedComponents(UndirectedGraph G) {
        marked = new boolean[G.V()];
        id = new int[G.V()];
        count = 0;
        for (int v = 0; v < G.V(); ++v) {
            if (marked[v]) {
                continue;
            }
            dfs(G, v);
            ++count;
        }
    }

    private void dfs(UndirectedGraph G, int v) {
        marked[v] = true;
        id[v] = count;
        for (int w : G.adj(v)) {
            if (marked[w]) {
                continue;
            }
            dfs(G, w);
        }
    }

    public boolean connected(int v, int w) {
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

    public static void main(String [] args) {
        //
    }
}
