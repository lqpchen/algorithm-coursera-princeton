/*
shortest ancestral path
 */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.Queue;

import java.util.Arrays;

public class SAP {

    private final Digraph g;
    private final int[] vLen;
    private final int[] wLen;
    private final int[] vPath;
    private final int[] wPath;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph g) {
        if (g == null) {
            throw new IllegalArgumentException();
        }
        this.g = new Digraph(g); // deep copy
        vLen = new int[g.V()];
        wLen = new int[g.V()];
        vPath = new int[g.V()];
        wPath = new int[g.V()];
    }

    private void resetArr(int[] arr, int initVal) {
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = initVal;
        }
    }

    private void init() {
        resetArr(vLen, 0);
        resetArr(wLen, 0);
        resetArr(vPath, -1);
        resetArr(wPath, -1);
    }

    private void bfs(int[] path, int[] len, Iterable<Integer> vs) {
        Queue<Integer> q = new Queue<>();
        for (int v : vs) {
            if (Integer.valueOf(v) == null)
                throw new IllegalArgumentException();
            if (vertexInvalid(v))
                throw new IllegalArgumentException();
            q.enqueue(v);
            path[v] = v;
            len[v] = 0;
        }
        while (!q.isEmpty()) {
            int from = q.dequeue();
            for (int to : g.adj(from)) {
                if (path[to] != -1)
                    continue;
                q.enqueue(to);
                path[to] = from;
                len[to] = len[from] + 1;
            }
        }
    }

    private void bfs(int[] path, int[] len, int v) {
        if (Integer.valueOf(v) == null)
            throw new IllegalArgumentException();
        if (vertexInvalid(v))
            throw new IllegalArgumentException();
        Queue<Integer> q = new Queue<>();
        q.enqueue(v);
        path[v] = v;
        len[v] = 0;
        while (!q.isEmpty()) {
            int from = q.dequeue();
            for (int to : g.adj(from)) {
                if (path[to] != -1)
                    continue;
                q.enqueue(to);
                path[to] = from;
                len[to] = len[from] + 1;
            }
        }
    }

    private boolean vertexInvalid(int v) {
        if (v < 0 || v >= g.V())
            return true;
        else
            return false;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        if (new Integer(v) == null || new Integer(w) == null) {
            throw new IllegalArgumentException();
        }
        if (vertexInvalid(v) || vertexInvalid(w))
            throw new IllegalArgumentException();
        init();
        bfs(vPath, vLen, v);
        bfs(wPath, wLen, w);
        int minLen = g.V()*2;
        for (int i = 0; i < g.V(); ++i) {
            if (vPath[i] == -1 || wPath[i] == -1) continue;
            if (vLen[i] + wLen[i] < minLen) minLen = vLen[i] + wLen[i];
        }
        if (minLen == g.V()*2) return -1;
        else return minLen;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        if (new Integer(v) == null || new Integer(w) == null) {
            throw new IllegalArgumentException();
        }
        if (vertexInvalid(v) || vertexInvalid(w))
            throw new IllegalArgumentException();
        init();
        bfs(vPath, vLen, v);
        bfs(wPath, wLen, w);
        int minLen = g.V()*2;
        int minAncestor = -1;
        for (int i = 0; i < g.V(); ++i) {
            if (vPath[i] == -1 || wPath[i] == -1) continue;
            if (vLen[i] + wLen[i] < minLen) {
                minLen = vLen[i] + wLen[i];
                minAncestor = i;
            }
        }
        return minAncestor;
    }

    private void checkIteratorArg(Iterable<Integer> v) {
        for (Integer i : v) {
            if (i == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
    // public int length(int[] v, int[] w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        checkIteratorArg(v);
        checkIteratorArg(w);
        init();
        bfs(vPath, vLen, v);
        bfs(wPath, wLen, w);
        int minLen = g.V()*2;
        for (int i = 0; i < g.V(); ++i) {
            if (vPath[i] == -1 || wPath[i] == -1) continue;
            if (vLen[i] + wLen[i] < minLen) minLen = vLen[i] + wLen[i];
        }
        if (minLen == g.V()*2) return -1;
        else return minLen;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    // public int ancestor(int[] v, int[] w) {
        if (v == null || w == null) {
            throw new IllegalArgumentException();
        }
        checkIteratorArg(v);
        checkIteratorArg(w);
        init();
        bfs(vPath, vLen, v);
        bfs(wPath, wLen, w);
        int minLen = g.V()*2;
        int minAncestor = -1;
        for (int i = 0; i < g.V(); ++i) {
            if (vPath[i] == -1 || wPath[i] == -1) continue;
            if (vLen[i] + wLen[i] < minLen) {
                minLen = vLen[i] + wLen[i];
                minAncestor = i;
            }
        }
        return minAncestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        Digraph g = new Digraph(25);
        int[] from = {13, 14, 21, 22, 15, 16, 8, 7, 9, 3, 4, 1, 23, 24, 19, 20, 17, 18, 10, 12, 11, 5, 6, 2};
        int[] to = {7, 7, 16, 16, 9, 9, 3, 3, 3, 1, 1, 0, 20, 20, 12, 12, 10, 10, 5, 5, 5, 2, 2, 0};
        for (int i = 0; i < from.length; ++i) {
            g.addEdge(from[i], to[i]);
        }
        SAP sap = new SAP(g);
        /*int v = 23, w = 6;
        System.out.println(sap.ancestor(v, w));
        System.out.println(sap.length(v, w));*/

        Integer[] v = {13, 23, 24};
        Integer[] w = {16, 17, 6};
        Iterable<Integer> vv = Arrays.asList(v);
        Iterable<Integer> ww= Arrays.asList(w);
        System.out.println(sap.ancestor(vv, ww));
        System.out.println(sap.length(vv, ww));

    }
}
