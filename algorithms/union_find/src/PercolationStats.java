
/*
This is an union-find application with the details described in the following link:
https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
It makes use of the UnionFind class.
 */

import java.util.Random;

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
        Random rand = new Random();
        for (int trial = 0; trial < this.t; ++trial) {
            Percolation perc = new Percolation(n);
            int [] freq = new int[n*n]; // freq table for sampling
            for (int i = 0; i < n*n; ++i) {
                freq[i] = 1;
            }
            while (!perc.percolates()) {
                // int idx = StdRandom.discrete(freq); // makes use of the freq table
                int idx = rand.nextInt(n*n); // not the best solution
                if (!perc.isOpen(idx/n + 1, idx % n + 1)) {
                    perc.open(idx/n + 1, idx % n + 1);
                    freq[idx] = 0;
                }
            }
            xs[trial] = perc.numberOfOpenSites()*1.0 / (n*n);
            // System.out.println("round " + t + ": estimated prob is " + xs[t]);
        }
        calcMean();
        calcStdDev();
        calcConfidenceLo();
        calcConfidenceHi();
    }

    // sample mean of percolation threshold
    private void calcMean() {
        double tot = 0.0;
        for (int i = 0; i < t; ++i) {
            tot += xs[i];
        }
        avg = tot/t;
    }

    // sample standard deviation of percolation threshold
    private void calcStdDev() {
        double sum = 0.0;
        for (int i = 0; i < t; ++i) {
            sum += (xs[i] - avg)*(xs[i] - avg);
        }
        stdDev = sum/(t - 1);
    }

    // low endpoint of 95% confidence interval
    private void calcConfidenceLo() {
        ciLow = mean() - CONFIDENCE_95*stdDev/Math.sqrt(t);
    }

    // high endpoint of 95% confidence interval
    private void calcConfidenceHi() {
        ciUp = mean() + CONFIDENCE_95*stdDev/Math.sqrt(t);
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
        //int n = Integer.parseInt(args[0]); // percolation size
        //int t = Integer.parseInt(args[1]); // number of experiments
        int n = 10;
        int t = 10;
        PercolationStats ps = new PercolationStats(n, t);

        System.out.println("mean = " + ps.mean());
        System.out.println("stddev = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }

}