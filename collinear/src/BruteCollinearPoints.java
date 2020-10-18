import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;

    // this is needed because otherwise the user can mess with the pointer provided to the constructor
    private final Point[] points;
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        this.points = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i == 0 && points[j] == null) throw new IllegalArgumentException();
                if (i != j && points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
            this.points[i] = points[i];
        }

        segments = new LineSegment[0];

        for (int i = 0; i < points.length - 3; i++) {

            for (int j = i + 1; j < points.length - 2; j++) {

                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) continue;

                    for (int l = k + 1; l < points.length; l++) {
                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                                && points[i].slopeTo(points[k]) == points[i].slopeTo(points[l]))
                        {
                            Point[] lineSegmentPoints = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(lineSegmentPoints);
                            addSegment(new LineSegment(lineSegmentPoints[0], lineSegmentPoints[3]));
                        }
                    }
                }
            }
            
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }

    private void addSegment(LineSegment segment) {
        LineSegment[] newSegments = new LineSegment[segments.length + 1];
        for (int i = 0; i < segments.length; i++) {
            newSegments[i] = segments[i];
        }
        newSegments[newSegments.length - 1] = segment;
        segments = newSegments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
