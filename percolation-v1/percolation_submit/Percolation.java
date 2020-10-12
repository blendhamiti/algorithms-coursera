/* *****************************************************************************
 *  Name:              Blend Hamiti
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

public class Percolation {
    private int[][] id;
    private int[][] sz;
    private boolean[][] state;
    private int stateCount;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)  {
        if (n <= 0)
            throw new IllegalArgumentException();
        id = new int[n][n];
        sz = new int[n][n];
        state = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                id[i][j] = i*id.length+j;
            }
        }
        stateCount = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if ((row < 1 || row > id.length) || (col < 1 || col > id.length))
            throw new IllegalArgumentException();

        // System.out.println("cell: " + row + " " + col);
        row = row - 1;
        col = col - 1;
        state[row][col] = true;

        // // connect to top point
        // if (row == 0)
        //     for (int i = 0; i < id.length; i++)
        //         union(id[row][col], id[0][i]);
        //
        // // connect to bottom point
        // if (row == id[row].length - 1)
        //     for (int i = 0; i < id.length; i++)
        //         union(id[row][col], id[id.length-1][i]);

        // connect to neighbours
        unionWithNeighbours(row, col);
        stateCount++;
        // printGrid();
    }

    private void unionWithNeighbours(int row, int col) {
        if ((row-1 >= 0 && row-1 < id.length) && (col >= 0 && col < id.length))
            union(id[row][col], id[row-1][col]);
        if ((row+1 >= 0 && row+1 < id.length) && (col >= 0 && col < id.length))
            union(id[row][col], id[row+1][col]);
        if ((row >= 0 && row < id.length) && (col-1 >= 0 && col-1 < id.length))
            union(id[row][col], id[row][col-1]);
        if ((row >= 0 && row < id.length) && (col+1 >= 0 && col+1 < id.length))
            union(id[row][col], id[row][col+1]);
    }

    private void union(int p, int q) {
        int i = root(p);
        int j = root(q);
        int rowi = i / id.length;
        int coli = i % id.length;
        int rowj = j / id.length;
        int colj = j % id.length;
        if (i == j)
            return;
        if (!state[rowi][coli] || !state[rowj][colj])
            return;
        if (sz[rowi][coli] < sz[rowj][colj]) {
            id[rowi][coli] = j;
            sz[rowj][colj] += sz[rowi][coli];
        } else {
            id[rowj][colj] = i;
            sz[rowi][coli]++;
            sz[rowi][coli] += sz[rowj][colj];
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if ((row < 1 || row > id.length) || (col < 1 || col > id.length))
            throw new IllegalArgumentException();
        return state[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if ((row < 1 || row > id.length) || (col < 1 || col > id.length))
            throw new IllegalArgumentException();
        return !state[row-1][col-1];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return stateCount;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 0; i < id.length; i++) {
            for (int j = 0; j < id.length; j++) {
                if (root(id[0][i]) == root(id[id.length-1][j]))
                    return true;
            }
        }
        return false;
    }

    private int root(int i) {
        int row = i / id.length;
        int col = i % id.length;
        while (i != id[row][col]) {
            id[row][col] = id[id[row][col] / id.length][id[row][col] % id.length];
            i = id[row][col];
            row = i / id.length;
            col = i % id.length;
        }
        return i;
    }

    // private void printGrid() {
    //     System.out.println("id print:");
    //     for (int i = 0; i < id.length; i++) {
    //         for (int j = 0; j < id[i].length; j++)
    //             System.out.print(id[i][j] + " ");
    //         System.out.println("");
    //     }
    // }
    //
    // private void printState() {
    //     System.out.println("state print:");
    //     for (int i = 0; i < state.length; i++) {
    //         for (int j = 0; j < state[i].length; j++)
    //             System.out.print(state[i][j] + " ");
    //         System.out.println("");
    //     }
    // }
    //
    // private void printSz() {
    //     System.out.println("sz print:");
    //     for (int i = 0; i < sz.length; i++) {
    //         for (int j = 0; j < sz[i].length; j++)
    //             System.out.print(sz[i][j] + " ");
    //         System.out.println("");
    //     }
    // }
    //
    // private void printAll() {
    //     System.out.println("______________________");
    //     printGrid();
    //     printState();
    //     printSz();
    // }

    // test client (optional)
    public static void main(String[] args) {
        int n = 10;
        Percolation percolation = new Percolation(n);
        // percolation.printAll();
        // percolation.open(0, 1);
        for (int i = 1; i <= n; i++)
            percolation.open(1, i);
        for (int i = 1; i <= n; i++)
            percolation.open(i, 1);
        for (int i = 1; i <= n; i++)
            percolation.open(i, 2);
        // percolation.printAll();
        System.out.println(percolation.percolates());
    }
}
