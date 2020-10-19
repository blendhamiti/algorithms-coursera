/* *****************************************************************************
 *  Name: Blend Hamiti
 *  Date: 18.10.2020
 *  Description: Solver
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

import java.util.Comparator;

public class Solver {
    private Queue<Board> solution;
    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // TODO: immutable data type
        if (initial == null) throw new IllegalArgumentException();
        moves = 0;

        solution = new Queue<>();
        MinPQ<Node> pq = new MinPQ<>(new HammingPriority());
        pq.insert(new Node(initial, moves, null));

        // Queue<Board> twinSolution = new Queue<>();
        // Node twinPrevNode = null;
        // Node twinNode = new Node(initial.twin(), moves, twinPrevNode);
        // MinPQ<Node> twinPq = new MinPQ<>(new HammingPriority());
        // twinPq.insert(twinNode);

        int count = 0;

        Node min;
        while (!pq.min().board.isGoal()) {
        // while (!pq.min().board.isGoal() && !twinPq.min().board.isGoal()) {
            moves++;
            min = pq.delMin();
            Node temp;
            for (Board board : min.board.neighbors()) {
                temp = new Node(board, moves, min);
                if (temp.prevNode != null
                        && !temp.board.equals(temp.prevNode.board))
                    pq.insert(temp);
            }
            solution.enqueue(min.board);

            // twinPrevNode = twinNode;
            // for (Board board : twinPq.min().board.neighbors()) {
            //     twinNode = new Node(board, moves, twinPrevNode);
            //     if (twinNode.prevNode != null && !twinNode.board.equals(twinNode.prevNode.board))
            //         twinPq.insert(twinNode);
            // }
            // twinSolution.enqueue(twinPq.delMin().board);



            count++;
            if (count == 10) break;
        }

        // if (twinPq.min().board.isGoal()) {
        //     moves = -1;
        //     solution = null;
        //     return;
        // }
        // twinSolution.enqueue(twinPq.delMin().board);

        solution.enqueue(pq.delMin().board);



    }

    private class Node {
        private Board board;
        private int moves;
        private Node prevNode;
        private int hamming;
        private int manhattan;

        public Node(Board board, int moves, Node prevNode) {
            this.board = board;
            this.moves = moves;
            this.prevNode = prevNode;
            hamming = board.hamming();
            manhattan = board.manhattan();
        }
    }

    private class HammingPriority implements Comparator<Node> {

        public int compare(Node n, Node t) {
            return (n.hamming + n.moves) - (t.hamming + t.moves);
        }
    }

    private class ManhattanPriority implements Comparator<Node> {

        public int compare(Node n, Node t) {
            return (n.manhattan + n.moves) - (t.manhattan + t.moves);
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return (this.solution() == null);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return solution;
    }

    // test client (see below)
    public static void main(String[] args) {

        // moves = 5
        // int[][] tiles = {{1, 0, 3}, {5, 2, 6}, {4, 7, 8}};

        // moves = 1
        // int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};

        // solved
        // int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        // unsolvable
        // int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};

        // create initial board from file
        In in = new In("puzzle07.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();

        Board board = new Board(tiles);
        Solver solver = new Solver(board);
        System.out.println(solver.solution);
        System.out.println("moves: " + solver.moves());

        // // Example text client
        // // create initial board from file
        // In in = new In(args[0]);
        // int n = in.redInt();
        // int[][] tiles = new int[n][n];
        // for (int i = 0; i < n; i++)
        //     for (int j = 0; j < n; j++)
        //         tiles[i][j] = in.readInt();
        // Board initial = new Board(tiles);
        //
        // // solve the puzzle
        // Solver solver = new Solver(initial);
        //
        // // print solution to standard output
        // if (!solver.isSolvable())
        //     StdOut.println("No solution possible");
        // else {
        //     StdOut.println("Minimum number of moves = " + solver.moves());
        //     for (Board board : solver.solution())
        //         StdOut.println(board);
        // }
    }
}
