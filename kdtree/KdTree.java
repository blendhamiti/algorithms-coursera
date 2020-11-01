/* *****************************************************************************
 *  Name: Blend Hamiti
 *  Date: 26/10/2020
 *  Description: KdTree Class
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static final boolean LEFT = false;
    private static final boolean RIGHT = true;
    private Node root;

    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    private class Node implements Comparable<Node> {
        public static final boolean VERTICAL = false;
        public static final boolean HORIZONTAL = true;
        public Node left;
        public Node right;
        public Point2D p;
        public boolean orientation;
        public int size;

        public Node(Point2D p) {
            if (p == null) throw new IllegalArgumentException();
            this.p = p;
            orientation = VERTICAL;
            left = null;
            right = null;
            size = 1;
        }

        public boolean isVertical() {
            return !orientation;
        }

        public int compareTo(Node that) {
            // if orientation of that is vertical, compare x
            if (that.isVertical()) {
                this.orientation = HORIZONTAL;
                return Double.compare(this.p.x(), that.p.x());
            }
            // if orientation of that is horizontal, compare y
            this.orientation = VERTICAL;
            return Double.compare(this.p.y(), that.p.y());
        }
    }

    // is the tree empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the tree
    public int size() {
        if (isEmpty()) return 0;
        return root.size;
    }

    // add the point to the tree (if it is not already in the tree)
    public void insert(Point2D p) {
        Node newNode = new Node(p);
        if (root == null) {
            root = newNode;
            return;
        }
        Node current = root;
        while (newNode.p.compareTo(current.p) != 0) {
            current.size++;
            if (newNode.compareTo(current) < 0) {
                if (current.left != null) {
                    current = current.left;
                    continue;
                }
                current.left = newNode;
                return;
            }
            else {
                if (current.right != null) {
                    current = current.right;
                    continue;
                }
                current.right = newNode;
                return;
            }

        }
    }

    // does the tree contain point p?
    public boolean contains(Point2D p)  {
        if (isEmpty()) return false;
        Node searchNode = new Node(p);
        Node current = root;
        while (current != null) {
            if (current.p.compareTo(p) == 0) return true;
            if (searchNode.compareTo(current) < 0) current = current.left;
            current = current.right;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw()  {
        if (isEmpty()) return;
        drawSubtrees(null, root);
    }

    private void drawSubtrees(Node parent, Node child) {
        if (child.left != null) drawSubtrees(child, child.left);
        if (child.right != null) drawSubtrees(child, child.right);
        drawNode(parent, child);
    }

    private void drawNode(Node parent, Node child) {
        drawLine(parent, child);
        drawPoint(child);
    }

    private void drawPoint(Node node) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);
        node.p.draw();
    }

    private void drawLine(Node parent, Node child) {
        StdDraw.setPenRadius(0.005);

        // draw root node
        if (parent == null) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(child.p.x(), 0, child.p.x(), 1);
        }

        // draw left child
        else if (child == parent.left) {
            if (child.isVertical()) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(child.p.x(), 0, child.p.x(), parent.p.y());
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(0, child.p.y(), parent.p.x(), child.p.y());
            }
        }

        // draw right child
        else {
            if (child.isVertical()) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(child.p.x(), parent.p.y(), child.p.x(), 1);
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(parent.p.x(), child.p.y(), 1, child.p.y());
            }
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        // TODO
        return null;
    }

    // a nearest neighbor in the tree to point p; null if the tree is empty
    public Point2D nearest(Point2D p) {
        // TODO
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In("input5b.txt");
        KdTree kdTree = new KdTree();
        while (!in.isEmpty())
            kdTree.insert(new Point2D(in.readDouble(), in.readDouble()));

        // Test draw()
        kdTree.draw();
    }
}
