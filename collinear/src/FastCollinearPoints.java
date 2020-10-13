import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Stopwatch;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int segmentsLength;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        segments = new LineSegment[1000];
        segmentsLength = 0;
        int lineSegmentsIndex = 0;

        Arrays.sort(points, points[0].slopeOrder());

        int startPoint = 0;
        int endPoint = 0;
        int currentindex = 0;
        while (currentindex < points.length) {
            // segment is being built
            if (startPoint < currentindex) {
                // segment can continue && continues on
                if (currentindex + 1 < points.length
                        && points[currentindex - 1].slopeTo(points[currentindex])
                            == points[currentindex].slopeTo(points[currentindex + 1])) {
                    endPoint = ++currentindex;
                }
                // segment ends
                else {
                    // segment is built if more than 3 points
                    if ((endPoint - startPoint) >= 3) {
                        Point[] lineSegmentPoints = new Point[endPoint - startPoint + 1];
                        for (int i = startPoint; i <= endPoint; i++) {
                            lineSegmentPoints[i - startPoint] = points[i];
                        }
                        Arrays.sort(lineSegmentPoints);
                        segments[lineSegmentsIndex++] = new LineSegment(lineSegmentPoints[0], lineSegmentPoints[endPoint-startPoint]);
                    }
                    startPoint =currentindex;
                    endPoint = currentindex;
                }
            }
            //start new segment lookup
            else {
                // more than 1 points remains
                if (currentindex + 2 < points.length) {
                    // segment starts to build
                    if (points[currentindex].slopeTo(points[currentindex + 1])
                            == points[currentindex + 1].slopeTo(points[currentindex + 2])) {
                        startPoint = currentindex;
                        endPoint = currentindex + 2;
                        currentindex += 2;
                    }
                    // segment doesn't start to build
                    else {
                        startPoint = ++currentindex;
                        endPoint = currentindex;
                    }
                }
                // no more points for segments
                else {
                    startPoint = ++currentindex;
                    endPoint = currentindex;
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
        FastCollinearPoints collinearPoints = new FastCollinearPoints(points);
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
