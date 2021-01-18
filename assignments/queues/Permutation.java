/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        // int k = 3;
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String data = StdIn.readString();
            rq.enqueue(data);
        }

        Iterator<String> iter = rq.iterator();
        for (int i = 0; i < k; ++i) {
            StdOut.println(iter.next());
        }
    }

}

