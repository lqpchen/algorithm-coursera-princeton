import java.util.Queue;

public class EdgeWeightedGraph {

    private final int V;
    private Bag<Edge>[] adj;
    private Deque<Edge> edges;

    public EdgeWeightedGraph(int V) {
        this.V = V;
        adj = (Bag<Edge>[]) new Bag[this.V];
        for (int v = 0; v < this.V; ++v) {
            adj[v] = new Bag<Edge>();
        }
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        edges.addLast(e);
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
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
        return cnt/2;
    }

    public String toString() {
        return "";
    }
}
