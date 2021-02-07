
import java.util.PriorityQueue;

public class LazyPrimMST {

    private Deque<Edge> mst;
    private PriorityQueue<Edge> pq;
    private boolean [] marked;
    private double weight;

    // time ~ E*log(E) the worst case, space ~ E
    public LazyPrimMST(EdgeWeightedGraph G) {
        // algorithm within constructor
        mst = new Deque<>();
        pq = new PriorityQueue<>();
        marked = new boolean[G.V()];
        weight = 0.0;

        visit(G, 0);
        for (Edge e : G.adj(0)) {
            pq.add(e);
        }

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.poll();
            int v = e.either();
            int w = e.other(v);
            if (marked[v] && marked[w]) continue;
            mst.addLast(e);
            weight += e.weight();
            if (!marked[v]) visit(G, v);
            if (!marked[w]) visit(G, w);
        }
    }

    private void visit(EdgeWeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.adj(v)) {
            if (marked[e.other(v)]) continue;
            pq.add(e);
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
}
