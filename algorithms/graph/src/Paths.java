public abstract class Paths {
    protected boolean[] marked;
    protected int[] edgeTo;
    protected int s;

    public Paths(Graph G, int s) {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s; // starting point
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        ResizingArrayStack<Integer> path = new ResizingArrayStack<>();
        for (int w = v; w != s; w = edgeTo[w]) {
            path.push(w);
        }
        path.push(s);
        return path;
    }

    public static void main(String [] args) {
        //
    }
}
