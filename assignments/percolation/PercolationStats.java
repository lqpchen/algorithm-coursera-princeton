/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final int n;
    private final int t;
    private double [] xs; // list of estimated probabilities
    private double avg;
    private double stdDev;
    private double ciLow;
    private double ciUp;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int num, int trials) {
        if (num <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n and trials must be greater than 0!");
        }

        n = num;
        t = trials;
        xs = new double [t];

        run();
    }

    private void run() {
        for (int trial = 0; trial < this.t; ++trial) {
            Percolation perc = new Percolation(n);
            int [] freq = new int[n*n]; // freq table for sampling
            for (int i = 0; i < n*n; ++i) {
                freq[i] = 1;
            }
            while (!perc.percolates()) {
                int idx = StdRandom.discrete(freq);
                if (!perc.isOpen(idx/n + 1, idx % n + 1)) {
                    perc.open(idx/n + 1, idx % n + 1);
                    freq[idx] = 0;
                }
            }
            xs[trial] = perc.numberOfOpenSites()*1.0 / (n*n);
            // System.out.println("round " + t + ": estimated prob is " + xs[t]);
        }
        avg = StdStats.mean(xs);
        stdDev = StdStats.stddev(xs);
        ciLow = mean() - CONFIDENCE_95*stddev()/Math.sqrt(t);
        ciUp = mean() + CONFIDENCE_95*stddev()/Math.sqrt(t);
    }

    // sample mean of percolation threshold
    public double mean() {
        return avg;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return stdDev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return ciLow;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return ciUp;
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]); // percolation size
        int t = Integer.parseInt(args[1]); // number of experiments
        // StdOut.println(n);
        // StdOut.println(t);
        PercolationStats ps = new PercolationStats(n, t);

        StdOut.println("mean = " + ps.mean());
        StdOut.println("stddev = " + ps.stddev());
        StdOut.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}