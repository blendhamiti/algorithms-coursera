/* *****************************************************************************
 *  Name:              Blend Hamiti
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */


import edu.princeton.cs.algs4.StdRandom;

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
                if (perc.isFull(x, y)) {
                    perc.open(x, y);
                    sitesOpened++;
                }
            }
            means[t] = sitesOpened / Math.pow(n, 2);
        }

        // System.out.println("mean: " + mean());
        // System.out.println("stddev: " + stddev());
        // System.out.println("confidenceLo: " + confidenceLo());
        // System.out.println("confidenceHi: " + confidenceHi());
    }

    // sample mean of percolation threshold
    public double mean() {
        double mean = 0;
        for (int i = 0; i < means.length; i++)
            mean += means[i];
        return mean / means.length;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double mean = mean();
        double stddev = 0;
        for (int i = 0; i < means.length; i++)
            stddev += Math.pow((means[i] - mean), 2);
        return Math.sqrt(stddev / (means.length - 1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(means.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(means.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }

}
