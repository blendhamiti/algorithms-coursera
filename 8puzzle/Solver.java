/* *****************************************************************************
 *  Name: Blend Hamiti
 *  Date: 18.10.2020
 *  Description: Solver
 **************************************************************************** */

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private Stack<Board> solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        solution = new Stack<>();
        int moves = 0;

        MinPQ<Node> pq = new MinPQ<>(new ManhattanPriority());
        pq.insert(new Node(initial, moves, null));

        MinPQ<Node> twinPq = new MinPQ<>(new ManhattanPriority());
        twinPq.insert(new Node(initial.twin(), moves, null));

        Bag<MinPQ<Node>> queues = new Bag<>();
        queues.add(pq);
        queues.add(twinPq);


        while (!pq.min().board.isGoal() && !twinPq.min().board.isGoal()) {
            moves++;

            for (MinPQ<Node> queue : queues) {
                for (Board neighbor : queue.min().board.neighbors()) {
                    if (queue.min().prevNode != null && neighbor.equals(queue.min().prevNode.board))
                        continue;
                    else
                        queue.insert(new Node(neighbor, moves, queue.min()));
                }
                queue.delMin();
            }
        }

        // build up the solution
        // original board is solved
        if (pq.min().board.isGoal()) {

            // find trail from solved to initial board
            solution.push(pq.min().board);
            Node trail = pq.min();
            while (trail.prevNode != null) {
                trail = trail.prevNode;
                solution.push(trail.board);
            }

        }
        // twin is solved
        else {
            solution = null;
        }
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
        return (this.solution() != null);
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (this.solution == null) return -1;
        else return (solution.size() - 1);
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

        // coursera test 3b fail
        // int[][] tiles = {{1, 3, 6}, {5, 0, 2}, {4, 7, 8}};

        // create initial board from file
        // In in = new In("puzzle07.txt");
        // int n = in.readInt();
        // int[][] tiles = new int[n][n];
        // for (int i = 0; i < n; i++)
        //     for (int j = 0; j < n; j++)
        //         tiles[i][j] = in.readInt();

        // Board board = new Board(tiles);
        // Solver solver = new Solver(board.twin());
        // System.out.println(solver.solution);
        // System.out.println("moves: " + solver.moves());

        // Example text client
        // create initial board from file
        In in = new In("puzzle14.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}