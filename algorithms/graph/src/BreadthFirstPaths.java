public class BreadthFirstPaths extends Paths {
    private int[] distTo;

    public BreadthFirstPaths(Graph G, int s) {
        super(G, s);
        distTo = new int[G.V()];
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        Deque<Integer> q = new Deque<>();
        marked[s] = true;
        distTo[s] = 0;
        q.addLast(s);
        while (!q.isEmpty()) {
            int v = q.removeFirst();
            for (int w : G.adj(v)) {
                if (marked[w]) {
                    continue;
                }
                marked[w] = true;
                q.addLast(w);
                edgeTo[w] = v;
                distTo[w] = 1 + distTo[v];
            }
        }
    }

    public static void main(String [] args) {
        //
    }
}
