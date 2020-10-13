import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stopwatch;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int segmentsLength;
    
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        segments = new LineSegment[points.length];
        int index = 0;
        segmentsLength = 0;

        for (int i = 0; i < points.length - 3; i++) {
            if (points[i] == null) throw new IllegalArgumentException();

            for (int j = i + 1; j < points.length - 2; j++) {
                if (points[j] == null) throw new IllegalArgumentException();
                if (points[j].compareTo(points[i]) == 0) throw new IllegalArgumentException();

                for (int k = j + 1; k < points.length - 1; k++) {
                    if (points[k] == null) throw new IllegalArgumentException();
                    if (points[k].compareTo(points[j]) == 0
                            || points[k].compareTo(points[i]) == 0)
                        throw new IllegalArgumentException();
                    if (points[i].slopeTo(points[j]) != points[i].slopeTo(points[k])) continue;

                    for (int l = k + 1; l < points.length; l++) {
                        if (points[l] == null) throw new IllegalArgumentException();
                        if (points[l].compareTo(points[k]) == 0
                                || points[l].compareTo(points[j]) == 0
                                || points[l].compareTo(points[i]) == 0)
                            throw new IllegalArgumentException();

                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                                && points[i].slopeTo(points[k]) == points[i].slopeTo(points[l]))
                        {
                            Point[] lineSegmentPoints = {points[i], points[j], points[k], points[l]};
                            Arrays.sort(lineSegmentPoints);
                            segments[index++] = new LineSegment(lineSegmentPoints[0], lineSegmentPoints[3]);
                            segmentsLength += 1;
                        }
                    }
                }
            }
            
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segmentsLength;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        Stopwatch stopwatch = new Stopwatch();
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
        System.out.println(stopwatch.elapsedTime());
        for (LineSegment l: collinearPoints.segments())
            if (l != null) System.out.println(l.toString());

        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.black);
        StdDraw.setPenRadius(0.02);
        StdDraw.setXscale(-1000, 33767);
        StdDraw.setYscale(-1000, 33767);
        for (Point p : points) p.draw();
        StdDraw.setPenColor(Color.red);
        StdDraw.setPenRadius(0.01);
        for (LineSegment l: collinearPoints.segments())
            if (l != null) l.draw();
        StdDraw.show();
    }
}
