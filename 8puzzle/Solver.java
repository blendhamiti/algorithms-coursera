/* *****************************************************************************
 *  Name: Blend Hamiti
 *  Date: 18.10.2020
 *  Description: Solver
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

import java.util.Comparator;

public class Solver {
    private Queue<Board> solution;
    int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // TODO: immutable data type
        if (initial == null) throw new IllegalArgumentException();
        solution = new Queue<>();
        moves = 0;

        Node prevNode = null;
        Node node = new Node(initial, moves, prevNode);
        MinPQ<Node> pq = new MinPQ<>(new ManhattanPriority());
        pq.insert(node);


        int count = 0;
        
        while (!pq.min().board.isGoal()) {
            moves++;
            prevNode = node;
            for (Board board : pq.min().board.neighbors()) {
                node = new Node(board, moves, prevNode);
                if (node.prevNode != null && !node.board.equals(node.prevNode.board))
                    pq.insert(node);
            }
            solution.enqueue(pq.delMin().board);

            count++;
            if (count == 100) break;
        }

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
        return (this.moves() >= 0);
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
        int[][] tiles = {{1, 0, 3}, {5, 2, 6}, {4, 7, 8}};

        // moves = 1
        // int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 0, 8}};

        // solved
        // int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};

        // unsolvable
        // int[][] tiles = {{1, 2, 3}, {4, 5, 6}, {8, 7, 0}};

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
