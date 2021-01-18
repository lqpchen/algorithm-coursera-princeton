public abstract class Graph {

    // adjacency list representation, since graphs tend to be sparse in real applications
    protected final int V;
    protected final Bag<Integer>[] adj;

    public Graph(int V) {
        this.V = V;
        adj = (Bag<Integer>[]) new Bag[V];
        for (int v = 0; v < V; ++v) {
            adj[v] = new Bag<Integer>();
        }
    }

    public abstract void addEdge(int v, int w);

    // return vertices adjacent to v
    public abstract Iterable<Integer> adj(int v);

    // number of vertices
    public abstract int V();

    // number of edges
    public abstract int E();

    public abstract String toString();

    public static int degree(UndirectedGraph G, int v) {
        int degree = 0;
        for (int w : G.adj(v)) {
            ++degree;
        }
        return degree;
    }

    public static int maxDegree(UndirectedGraph G) {
        int maxDegree = 0;
        for (int v = 0; v < G.V(); ++v) {
            int degree = G.degree(G, v);
            if (degree > maxDegree) {
                maxDegree = degree;
            }
        }
        return maxDegree;
    }

    public static double averageDegree(UndirectedGraph G) {
        return 2.0*G.E()/G.V();
    }

    public static int numberOfSelfLoops(UndirectedGraph G) {
        int cnt = 0;
        for (int v = 0; v < G.V(); ++v) {
            for (int w : G.adj(v)) {
                if (w == v) {
                    ++cnt;
                }
            }
        }
        return cnt; // cnt/2 ??
    }
}
