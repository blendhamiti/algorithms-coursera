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
    private Node root;

    // construct an empty set of points
    public KdTree() {
        root = null;
    }

    private class Node implements Comparable<Node> {
        public static final boolean VERTICAL = false;
        public static final boolean HORIZONTAL = true;
        public Node parent;
        public Node left;
        public Node right;
        public double leftLimit;
        public double rightLimit;
        public Point2D p;
        public boolean orientation;
        public int size;

        public Node(Point2D p) {
            if (p == null) throw new IllegalArgumentException();
            this.p = p;
            orientation = VERTICAL;
            size = 1;
            parent = null;
            left = null;
            right = null;
            leftLimit = 0;
            rightLimit = 1;
        }

        public boolean isVertical() {
            return !orientation;
        }

        public int compareTo(Node that) {
            if (that.isVertical())
                return Double.compare(this.p.x(), that.p.x());
            else
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
        if (root == null) root = newNode;
        Node current = root;
        while (newNode.p.compareTo(current.p) != 0) {
            current.size++;
            if (newNode.compareTo(current) < 0) {
                if (current.left == null) {
                    newNode.parent = current;
                    newNode.orientation = !newNode.parent.orientation;
                    newNode.leftLimit = newNode.parent.parent == null ? 0 : newNode.parent.parent.leftLimit;
                    newNode.rightLimit = newNode.parent.isVertical() ? newNode.parent.p.x() : newNode.parent.p.y();
                    current.left = newNode;
                }
                current = current.left;
            }
            else {
                if (current.right == null) {
                    newNode.parent = current;
                    newNode.orientation = !newNode.parent.orientation;
                    newNode.leftLimit = newNode.parent.isVertical() ? newNode.parent.p.x() : newNode.parent.p.y();
                    newNode.rightLimit = newNode.parent.parent == null ? 1 : newNode.parent.parent.rightLimit;
                    current.right = newNode;
                }
                current = current.right;
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
            else                                   current = current.right;
        }
        return false;
    }

    // draw all points to standard draw
    public void draw()  {
        if (isEmpty()) return;
        drawSubtrees(root);
    }

    private void drawSubtrees(Node node) {
        if (node.left != null) drawSubtrees(node.left);
        if (node.right != null) drawSubtrees(node.right);
        drawNode(node);
    }

    private void drawNode(Node node) {
        drawLine(node);
        drawPoint(node);
    }

    private void drawPoint(Node node) {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);
        node.p.draw();

        // // Draw the coordinate point text
        // StdDraw.setPenColor(StdDraw.MAGENTA);
        // StdDraw.setPenRadius(0.005);
        // StdDraw.text(node.p.x(), node.p.y(), "(" + node.p.x() + ", " + node.p.y() + ")");
    }

    private void drawLine(Node node) {
        if (node.isVertical()) {
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.p.x(), node.leftLimit, node.p.x(), node.rightLimit);
        }
        else {
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.leftLimit, node.p.y(), node.rightLimit, node.p.y());
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
        In in = new In("input10.txt");
        KdTree kdTree = new KdTree();
        while (!in.isEmpty())
            kdTree.insert(new Point2D(in.readDouble(), in.readDouble()));

        // Test draw()
        kdTree.draw();
    }
}
