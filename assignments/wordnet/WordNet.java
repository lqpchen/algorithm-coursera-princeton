/*
wordnet
 */

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;

import java.util.HashMap;

public class WordNet {

    private final Digraph g;
    private final HashMap<String, Iterable<Integer>> nouns;
    private boolean[] marked;
    private boolean[] onstack;
    //private final String[] synsets;
    private final HashMap<Integer, String> synsets;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String fileSynsets, String fileHypernyms) {
        if (fileSynsets == null || fileHypernyms == null) {
            throw new IllegalArgumentException();
        }
        nouns = new HashMap<>();
        synsets = new HashMap<>();
        In inSynsets = new In(fileSynsets);
        In inHypernyms = new In(fileHypernyms);
        int id = 0, cntLn = 0;
        // construct the hash map for the nouns, ~O(n)
        // build a string array of synsets at the same time, ~O(V)
        while (inSynsets.hasNextLine()) {
            String line = inSynsets.readLine();
            id = Integer.parseInt(line.split(",")[0]);
            synsets.put(id, line.split(",")[1]);
            String[] strs = line.split(",")[1].split(" ");
            Queue<Integer> q;
            for (String str : strs) {
                if (nouns.get(str) == null) {
                    q = new Queue<>();
                } else {
                    q = (Queue<Integer>) nouns.get(str);
                }
                q.enqueue(id);
                nouns.put(str, q);
            }
        }
        // construct the digraph for the wordnet, ~O(V+E)
        cntLn = id + 1;
        g = new Digraph(cntLn);
        while (inHypernyms.hasNextLine()) {
            String line = inHypernyms.readLine();
            id = Integer.parseInt(line.split(",")[0]);
            for (String hyper : line.split(",")) {
                int hyperID = Integer.parseInt(hyper);
                if (hyperID == id) { // the first number is id
                    continue;
                } else { // the rest of the numbers are hyper id
                    g.addEdge(id, hyperID);
                }
            }
        }

        // check if DAG
        if (!isRootedDAG()) {
            throw new IllegalArgumentException();
        }

        sap = new SAP(g);
    }

    private boolean isRootedDAG() {
        // check if there is any cycles, ~O(V+E)
        // check if there is a root at the same time
        marked = new boolean[g.V()];
        onstack = new boolean[g.V()];
        int cntZeroOutDegree = 0;
        for (int v = 0; v < g.V(); ++v) {
            if (!marked[v]) {
                if (dfs(v)) {
                    return false;
                }
            }
            if (g.outdegree(v) == 0) {
                ++cntZeroOutDegree;
                if (cntZeroOutDegree > 1) {
                    return false;
                }
            }
        }
        if (cntZeroOutDegree == 0) {
            return false;
        }
        return true;
    }

    // return if any cycle has been detected
    private boolean dfs(int v) {
        marked[v] = true;
        onstack[v] = true;
        for (int w : g.adj(v)) {
            if (onstack[w]) {
                return true;
            } else if (marked[w]) {
                continue;
            } else {
                if (dfs(w)) {
                    return true;
                }
            }
        }
        onstack[v] = false;
        return false;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nouns.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException();
        }
        return nouns.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        return sap.length(nouns.get(nounA), nouns.get(nounB));
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        if (nounA == null || nounB == null || !isNoun(nounA) || !isNoun(nounB)) {
            throw new IllegalArgumentException();
        }
        int ancID = sap.ancestor(nouns.get(nounA), nouns.get(nounB));
        if (ancID != -1) {
            String synset = synsets.get(ancID);
            return synset;
        }
        return "";
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        /*for (String n : wn.nouns()) {
            System.out.print(n + " ");
        }*/
        System.out.println(wn.isNoun("bababa"));
    }
}
