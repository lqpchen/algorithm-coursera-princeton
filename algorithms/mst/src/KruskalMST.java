/*

 */

import java.util.PriorityQueue;

public class KruskalMST {
    private Deque<Edge> mst;
    private double weight; // keep track of the total weight

    // E*log(E) in the worst case
    public KruskalMST(EdgeWeightedGraph G) {
        // algorithm within constructor
        mst = new Deque<>();
        PriorityQueue<Edge> edges = new PriorityQueue<>(); // MinPQ
        weight = 0.0;
        for (Edge e : G.edges()) {
            edges.add(e);
        }
        UnionFind uf = new UnionFind(G.V());

        while (!edges.isEmpty() && mst.size() < G.V() -1) {
            Edge e = edges.poll();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) {
                mst.addLast(e);
                uf.union(v, w);
                weight += e.weight();
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return this.weight;
    }

    public static void main(String [] args) {
        //
    }
}
