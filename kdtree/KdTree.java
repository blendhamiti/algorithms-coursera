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

// TODO: Change comments to refer to the KdTree
public class KdTree {
    private SET<Node> set;

    // construct an empty set of points
    public KdTree() {
        set = new SET<>();
    }

    private class Node implements Comparable<Node> {
        private static final boolean VERTICAL = false;
        private static final boolean HORIZONTAL = true;
        private Point2D p;
        private boolean orientation;

        public Node(Point2D p) {
            if (p == null) throw new IllegalArgumentException();
            this.p = p;
            orientation = VERTICAL;
        }

        public Point2D getPoint() {
            return p;
        }

        public boolean isVertical() {
            return !orientation;
        }

        public int compareTo(Node that) {
            // orientation is vertical, compare x
            if (that.isVertical()) {
                this.orientation = HORIZONTAL;
                return Double.compare(this.p.x(), that.p.x());
            }
            // orientation is horizontal, compare y
            this.orientation = VERTICAL;
            return Double.compare(this.p.y(), that.p.y());
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        set.add(new Node(p));
    }

    // does the set contain point p?
    public boolean contains(Point2D p)  {
        return set.contains(new Node(p));
    }

    // draw all points to standard draw
    public void draw()  {
        for (Node node : set) {
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            node.getPoint().draw();

            if (node.isVertical()) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(0.005);
                StdDraw.line(node.getPoint().x(), 0, node.getPoint().x(), 1);
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius(0.005);
                StdDraw.line(0, node.getPoint().y(), 1, node.getPoint().y());
            }
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        // TODO
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        // TODO
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In("input4b.txt");
        KdTree kdTree = new KdTree();
        while (!in.isEmpty())
            kdTree.insert(new Point2D(in.readDouble(), in.readDouble()));

        // Test draw()
        // kdTree.draw();

    }
}
