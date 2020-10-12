/* *****************************************************************************
 *  Name:              Blend Hamiti
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private int dim, dimSq;
    private int firstGridIndex, lastGridIndex;
    private int topSiteIndex;
    private int botSiteIndex;
    private boolean[] openSites;
    private boolean[] fullSites;
    private int openSitesCount;
    private boolean percolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)  {
        if (n <= 0)
            throw new IllegalArgumentException();
        dim = n;
        dimSq = (int) Math.pow(n, 2);
        grid = new WeightedQuickUnionUF(dimSq + 2);
        topSiteIndex = dimSq;
        botSiteIndex = dimSq + 1;
        firstGridIndex = 1;
        lastGridIndex = dim;
        openSites = new boolean[dimSq];
        fullSites = new boolean[dimSq];
        openSitesCount = 0;
        percolates = false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!withinBounds(row, col)) throw new IllegalArgumentException();
        if (isOpen(row, col))
            return;

        openSites[positionToStrucIndex(row, col)] = true;
        openSitesCount++;

        // if in the top row
        if (row == firstGridIndex)
            union(positionToStrucIndex(row, col), topSiteIndex);
        // if in the bottom row
        // if (row == lastGridIndex)
        //     union(positionToStrucIndex(row, col), botSiteIndex);
        setFullSite(row, col);

        unionWithNeighbours(row, col);

        for (int j = 1; j <= dim; j++) {
            for (int k = 1; k <= dim; k++) {
                if (isOpen(j, k)) {
                    setFullSite(j, k);
                }
            }
        }
    }

    private void union(int p, int q) {
        grid.union(p, q);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!withinBounds(row, col)) throw new IllegalArgumentException();
        return openSites[positionToStrucIndex(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!withinBounds(row, col)) throw new IllegalArgumentException();
        return fullSites[positionToStrucIndex(row, col)];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    private int root(int i) {
        return grid.find(i);
    }

    private int positionToStrucIndex(int row, int col) {
        return dim * (row - 1) + (col - 1);
    }

    private boolean withinBounds(int row, int col) {
        return (row >= firstGridIndex && row <= lastGridIndex) && (col >= firstGridIndex && col <= lastGridIndex);
    }

    private void unionWithNeighbours(int row, int col) {
        int[] rowN = {-1, 0, 0, 1};
        int[] colN = {0, -1, 1, 0};
        for (int i = 0; i < 4; i++) {
            if (withinBounds(row + rowN[i], col + colN[i]) && isOpen(row + rowN[i], col + colN[i])) {
                union(positionToStrucIndex(row, col), positionToStrucIndex(row + rowN[i], col + colN[i]));
            }
        }
    }

    private void setFullSite(int row, int col) {
        if (root(positionToStrucIndex(row, col)) == root(topSiteIndex)) {
            fullSites[positionToStrucIndex(row, col)] = true;
            if (row == dim)
                percolates = true;
        }
    }

    // test client (optional)
    public static void main(String[] args) {

        // Stopwatch stopwatch = new Stopwatch();
        // WeightedQuickUnionUF weightedQuickUnionUF = new WeightedQuickUnionUF(1000);
        // System.out.println(stopwatch.elapsedTime());
        //
        // stopwatch = new Stopwatch();
        // int[] b = new int[1000];
        // System.out.println(stopwatch.elapsedTime());




    }
}
