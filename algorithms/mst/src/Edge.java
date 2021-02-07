/*
Weighted edge API
 */

public class Edge implements Comparable<Edge> {
    private final int v, w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    // return either of the endpoint
    public int either() {
        return v;
    }

    // return endpoint that's not v
    public int other(int vertex) {
        if (vertex == v) {
            return w;
        } else {
            return v;
        }
    }

    public int compareTo(Edge that) {
        if (weight() < that.weight()) {
            return -1;
        } else if (weight() > that.weight()) {
            return 1;
        } else {
            return 0;
        }
    }

    public double weight() {
        return weight;
    }

    public String toString() {
        return "";
    }
}
