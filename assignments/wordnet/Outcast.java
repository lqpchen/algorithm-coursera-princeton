/*
detecting outliers among a set of nouns
 */

public class Outcast {

    private final WordNet wn;

    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        wn = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        int[] dist = new int[nouns.length];
        int maxDist = -1, maxIdx = 0;
        for (int i = 0; i < dist.length; ++i) {
            for (int j = 0; j < dist.length; ++j) {
                dist[i] += wn.distance(nouns[i], nouns[j]);
            }
            if (dist[i] > maxDist) {
                maxDist = dist[i];
                maxIdx = i;
            }
        }
        return nouns[maxIdx];
    }

    public static void main(String[] args) {
        WordNet wn = new WordNet("synsets.txt", "hypernyms.txt");
        Outcast oc = new Outcast(wn);
        // String[] nouns = {"horse", "zebra", "cat", "bear", "table"};
        String[] nouns = {"water", "soda", "bed", "orange_juice", "milk", "apple_juice", "tea", "coffee"};
        System.out.println(oc.outcast(nouns));
    }
}
