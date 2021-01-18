public class DiGraph extends Graph {

    // create an empty graph with V vertices
    public DiGraph(int V) {
        super(V);
    }

    // v->w
    public void addEdge(int v, int w) {
        adj[v].add(w);
    }

    // return vertices pointing from v
    public Iterable<Integer> adj(int v) {
        return adj[v];
    }

    // number of vertices
    public int V() {
        return V;
    }

    // number of edges
    public int E() {
        int cnt = 0;
        for (int v = 0; v < V; ++v) {
            cnt += adj[v].size();
        }
        return cnt;
    }

    public DiGraph reverse() {
        DiGraph revG = new DiGraph(V());
        for (int v = 0; v < V(); ++v) {
            for (int w : adj(v)) {
                revG.addEdge(w, v);
            }
        }
        return revG;
    }

    public String toString() {
        return "";
    }

    public void print() {
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                System.out.println(v + "->" + w);
            }
        }
    }

    public static void main(String [] args) {
        DiGraph dg = new DiGraph(13);
        int[] from = {0, 2, 6, 0, 2, 3, 4, 3, 4, 5, 6, 11, 6, 8, 7, 6, 7, 9, 9, 12, 10, 11};
        int[] to = {1, 0, 0, 5, 3, 2, 2, 5, 3, 4, 4, 4, 8, 6, 6, 9, 9, 10, 11, 9, 12, 12};
        for (int i = 0; i < from.length; ++i) {
            dg.addEdge(from[i], to[i]);
        }
        dg.print();
        dg.reverse().print();
    }
}
