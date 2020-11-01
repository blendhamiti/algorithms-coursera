/* *****************************************************************************
 *  Name: Blend Hamiti
 *  Date: 26/10/2020
 *  Description: PointSET Class
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {
    private SET<Point2D> set;

    // construct an empty set of points
    public PointSET() {
        set = new SET<>();
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
        set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p)  {
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw()  {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D p : set)
            StdDraw.point(p.x(), p.y());
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        SET<Point2D> onRect = new SET<>();
        for (Point2D current : set)
            if (rect.contains(current))
                onRect.add(current);
        return onRect;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (isEmpty()) return null;
        Point2D nearest = null;
        for (Point2D current : set) {
            if (nearest == null)
                nearest = current;
            if (p.distanceSquaredTo(current) < p.distanceSquaredTo(nearest))
                nearest = current;
        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        In in = new In("input10.txt");
        PointSET pointSET = new PointSET();
        while (!in.isEmpty())
            pointSET.insert(new Point2D(in.readDouble(), in.readDouble()));

        // Test draw()
        pointSET.draw();

        // Test range()
        // Point2D min = new Point2D(0.2, 0.2);
        // Point2D max = new Point2D(0.6, 0.8);
        // RectHV rect = new RectHV(min.x(), min.y(), max.x(), max.y());
        // rect.draw();
        // StdDraw.setPenColor(StdDraw.RED);
        // StdDraw.setPenRadius(0.01);
        // for (Point2D p : pointSET.range(rect))
        //     p.draw();

        // Test nearest()
        // pointSET.draw();
        // Point2D p = new Point2D(0.2, 0.3);
        // StdDraw.setPenColor(StdDraw.RED);
        // StdDraw.setPenRadius(0.01);
        // p.draw();
        // StdDraw.setPenColor(StdDraw.GREEN);
        // pointSET.nearest(p).draw();
    }
}
