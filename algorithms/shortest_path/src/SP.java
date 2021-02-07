/*
find shortest path from s to every other vertex. (single source)
 */

public class SP {
    private double [] distTo;
    private DirectedEdge [] edgeTo;
    private int s;

    public SP(EdgeWeightedDigraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        this.s = s;
        distTo[s] = 0;
        // edgeTo[s] = s;

        // shortest path algorithm
    }

    public double distTo(int v) {
        return distTo(v);
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        ResizingArrayStack<DirectedEdge> path = new ResizingArrayStack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }

        return path;
    }

    private void relax(DirectedEdge e) {
        int from = e.from();
        int to = e.to();
        if (distTo[to] > distTo[from] + e.weight()) {
            distTo[to] = distTo[from] + e.weight();
            edgeTo[to] = e;
        }
    }

    public boolean hasPathTo(int v) {
        return false;
    }

    public static void main(String [] str) {
        //
    }
}
