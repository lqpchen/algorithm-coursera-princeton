public class EdgeWeightedDigraph {

    private final int V;
    private final Deque<DirectedEdge> edges;
    private final Bag<DirectedEdge> [] adj;

    public EdgeWeightedDigraph(int V) {
        this.V = V;
        edges = new Deque<>();
        adj = (Bag<DirectedEdge>[]) new Bag[V];
        for (int v = 0; v < V; ++v) {
            adj[v] = new Bag<DirectedEdge>();
        }
    }

    public void addEdge(DirectedEdge e) {
        int from = e.from();
        adj[from].add(e);
        edges.addFirst(e);
    }

    Iterable<DirectedEdge> adj(int v) {
        return adj[v];
    }

    public Iterable<DirectedEdge> edges() {
        // can use a deque for this
        return edges;
    }

    public int V() {
        return this.V;
    }

    public int E() {
        int cnt = 0;
        for (int v = 0; v < V(); ++v) {
            cnt += adj[v].size();
        }
        return cnt;
    }

    public String toString() {
        return "";
    }

    public static void main(String [] str) {
        //
    }
}
