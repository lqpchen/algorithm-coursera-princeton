public class DepthFirstPaths extends Paths {

    public DepthFirstPaths(Graph G, int s) {
        super(G, s);
        dfs(G, s);
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (marked[w]) {
                continue;
            }
            dfs(G, w);
            edgeTo[w] = v;
        }
    }

    public static void main(String [] args) {
        //
    }
}
