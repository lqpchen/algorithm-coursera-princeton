/*
only works for DAG (directed acyclic graph)
easy to detect if there is a cycle, by tracking if a v is on stack
 */

public class TopologicalOrder {

    private boolean[] marked;
    private ResizingArrayStack<Integer> s;

    public TopologicalOrder(DiGraph G) {
        s = new ResizingArrayStack<>();
        marked = new boolean[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!marked[v]) {
                dfs(G, v);
            }
        }
    }

    private void dfs(Graph G, int v) {
        marked[v] = true;
        for (int w : G.adj(v)) {
            if (marked[w]) {
                continue;
            }
            dfs(G, w);
        }
        //System.out.print(v + " ");
        s.push(v);
    }

    public Iterable<Integer> order() {
        return s;
    }

    public static void main(String [] args) {
        DiGraph dg = new DiGraph(13);
        int[] from = {0, 2, 6, 0, 2, 3, 4, 3, 4, 5, 6, 11, 6, 8, 7, 6, 7, 9, 9, 12, 10, 11};
        int[] to = {1, 0, 0, 5, 3, 2, 2, 5, 3, 4, 4, 4, 8, 6, 6, 9, 9, 10, 11, 9, 12, 12};
        for (int i = 0; i < from.length; ++i) {
            dg.addEdge(from[i], to[i]);
        }
        //dg.print();
        TopologicalOrder ord = new TopologicalOrder(dg);
        for (int i : ord.order()) {
            System.out.print(i + " "); // print order for graph with cycle, not good
        }
        System.out.println();
    }
}
