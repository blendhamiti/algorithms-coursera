/* *****************************************************************************
 *  Name:              Blend Hamiti
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] means;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException();

        means = new double[trials];
        Percolation perc;
        int sitesOpened;
        int x;
        int y;

        for (int t = 0; t < trials; t++) {
            perc = new Percolation(n);
            sitesOpened = 0;
            while (!perc.percolates()) {
                x = StdRandom.uniform(1, n+1);
                y = StdRandom.uniform(1, n+1);
                if (!perc.isOpen(x, y)) {
                    perc.open(x, y);
                    sitesOpened++;
                }
            }
            means[t] = sitesOpened / Math.pow(n, 2);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(means);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(means);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(means.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(means.length));
    }

    // private void printResults() {
    //     System.out.println("mean                    = " + mean());
    //     System.out.println("stddev                  = " + stddev());
    //     System.out.println("95% confidence interval = [" + confidenceLo() + ", " + confidenceHi() + "]");
    // }

    // test client (see below)
    public static void main(String[] args) {
        // Stopwatch stopwatch = new Stopwatch();

        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        // stats.printResults();
        System.out.println("mean                    = " + stats.mean());
        System.out.println("stddev                  = " + stats.stddev());
        System.out.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");

        // System.out.println("Elapsed time: " + stopwatch.elapsedTime());
    }

}
