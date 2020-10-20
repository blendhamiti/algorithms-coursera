/* *****************************************************************************
 *  Name: Blend Hamiti
 *  Date: 18.10.2020
 *  Description: Board
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;

public class Board {
    private int[][] tiles;
    private final int n;
    private int blankRow;
    private int blankCol;
    private enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        n = tiles.length;

        // clone array and find the blank tile
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                }
            }
        }
    }

    // string representation of this board
    public String toString() {
        // "" + n != n + ""
        StringBuilder board = new StringBuilder(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] < 10) board.append(" " + tiles[i][j] + " ");
                else board.append(tiles[i][j] + " ");
            }
            board.append("\n");
        }
        return board.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != i * n + j + 1) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    manhattan += Math.abs(i - (tiles[i][j] - 1) / n)
                            + Math.abs(j - (tiles[i][j] - 1) % n);
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (this.hamming() == 0);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.dimension() != that.dimension()) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Bag<Board> neighbors = new Bag<>();
        Board board;
        for (Direction dir : Direction.values()) {
            board = new Board(tiles);
            if (board.moveBlank(dir))
                neighbors.add(board);
        }
        return neighbors;
    }

    private boolean moveBlank(Direction dir) {
        if (dir == Direction.UP) {
            return exchangeBlank(blankRow - 1, blankCol);
        }
        if (dir == Direction.DOWN) {
            return exchangeBlank(blankRow + 1, blankCol);
        }
        if (dir == Direction.LEFT) {
            return exchangeBlank(blankRow, blankCol - 1);
        }
        if (dir == Direction.RIGHT) {
            return exchangeBlank(blankRow, blankCol + 1);
        }
        return false;
    }

    private boolean exchangeBlank(int row, int col) {
        if (tileExists(row, col)) {
            tiles[blankRow][blankCol] = tiles[row][col];
            tiles[row][col] = 0;
            return true;
        }
        return false;
    }

    private boolean tileExists(int row, int col) {
        return  (row >= 0 && row < n) && (col >= 0 && col < n);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board board = new Board(tiles);
        int i = 0;
        int j = 0;
        if (i == blankRow && j == blankCol) {
            i = getNextTile(i, j)[0];
            j = getNextTile(i, j)[1];
        }
        int a = getNextTile(i, j)[0];
        int b = getNextTile(i, j)[1];
        if (a == blankRow && b == blankCol) {
            a = getNextTile(a, b)[0];
            b = getNextTile(a, b)[1];
        }
        board.exchangeTile(i, j, a, b);
        return board;
    }

    private int[] getNextTile(int row, int col) {
        int[] res = {row, col};
        if (col < n - 1) {
            res[1]++;
        }
        else {
            res[0]++;
            res[1] = 0;
        }
        return res;
    }

    private int[] getPrevTile(int row, int col) {
        int[] res = {row, col};
        if (col > 0) {
            res[1]--;
        }
        else {
            res[0]--;
            res[1] = n - 1;
        }
        return res;
    }

    private void exchangeTile(int rowi, int coli, int rowj, int colj) {
        int oldVal = tiles[rowi][coli];
        tiles[rowi][coli] = tiles[rowj][colj];
        tiles[rowj][colj] = oldVal;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // int[][] tiles = {{0, 2, 8}, {4, 5, 6}, {7, 3, 1}};
        // int[][] tiles = {{5, 1, 8}, {2, 7, 3}, {4, 0, 6}};
        int[][] tiles = {{1, 2, 3}, {4, 5, 7}, {6, 0, 8}};
        Board board = new Board(tiles);
        System.out.println(board);
        System.out.println("hamming: " + board.hamming());
        System.out.println("manhattan: " + board.manhattan());
        for (Board b : board.neighbors()) {
            System.out.println(b);
        }
        System.out.println(board.twin());
    }
}
