public class UndirectedGraph extends Graph {

    // create an empty graph with V vertices
    public UndirectedGraph(int V) {
        super(V);
    }

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    // return vertices adjacent to v
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
        return cnt/2;
    }

    public String toString() {
        return "";
    }

    public void print() {
        for (int v = 0; v < V; v++) {
            for (int w : adj(v)) {
                System.out.println(v + "--" + w);
            }
        }
    }

    public static void main(String [] args) {
        //
    }
}

