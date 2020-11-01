/* *****************************************************************************
 *  Name: Blend Hamiti
 *  Date: 26/10/2020
 *  Description: KdTree Class
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
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
            if (newNode.compareTo(current) < 0) {
                if (current.left == null) {
                    newNode.parent = current;
                    newNode.orientation = !newNode.parent.orientation;
                    newNode.leftLimit = newNode.parent.parent == null ? 0 : newNode.parent.parent.leftLimit;
                    newNode.rightLimit = newNode.parent.isVertical() ? newNode.parent.p.x() : newNode.parent.p.y();
                    current.left = newNode;

                    // increase all previous node sizes
                    Node parent = current;
                    parent.size++;
                    while (parent.parent != null) {
                        parent = parent.parent;
                        parent.size++;
                    }
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

                    // increase all previous node sizes
                    Node parent = current;
                    parent.size++;
                    while (parent.parent != null) {
                        parent = parent.parent;
                        parent.size++;
                    }
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
        if (rect == null) throw new IllegalArgumentException();
        SET<Point2D> onRect = new SET<>();
        if (isEmpty()) return onRect;
        searchSubtreeForRange(root, rect, onRect);
        return onRect;
    }

    private void searchSubtreeForRange(Node node, RectHV rect, SET<Point2D> set) {
        if (node.left != null && rectangleOf(node.left).intersects(rect))
            searchSubtreeForRange(node.left, rect, set);
        if (node.right != null && rectangleOf(node.right).intersects(rect))
            searchSubtreeForRange(node.right, rect, set);
        if (rect.contains(node.p)) set.add(node.p);
    }

    private RectHV rectangleOf(Node node) {
        if (node == root) return new RectHV(0, 0, 1, 1);
        if (node.isVertical())
            return new RectHV(node.parent.leftLimit, node.leftLimit, node.parent.rightLimit, node.rightLimit);
        else
            return new RectHV(node.leftLimit, node.parent.leftLimit, node.rightLimit, node.parent.rightLimit);
    }

    // a nearest neighbor in the tree to point p; null if the tree is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        // java cannot pass by reference, fix as below ?!
        Node closestNeighbor = new Node(root.p);
        searchSubtreeForCN(root, p, closestNeighbor);
        return closestNeighbor.p;
    }

    private void searchSubtreeForCN(Node node, Point2D p, Node closestNeighbor) {
        if (node.left != null && rectangleOf(node.left).distanceTo(p) <= node.p.distanceTo(p))
            searchSubtreeForCN(node.left, p, closestNeighbor);
        if (node.right != null && rectangleOf(node.right).distanceTo(p) <= node.p.distanceTo(p))
            searchSubtreeForCN(node.right, p, closestNeighbor);
        if (node.p.distanceTo(p) < closestNeighbor.p.distanceTo(p)) closestNeighbor.p = node.p;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In("input4b.txt");
        KdTree kdTree = new KdTree();
        while (!in.isEmpty())
            kdTree.insert(new Point2D(in.readDouble(), in.readDouble()));

        // Test draw()
        kdTree.draw();

        // Closest neighbor test
        // kdTree.nearest(new Point2D(0.1, 0.1));
    }
}
