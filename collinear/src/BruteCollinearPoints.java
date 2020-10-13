import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
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

                    for (int l = k + 1; l < points.length; l++) {
                        if (points[l] == null) throw new IllegalArgumentException();
                        if (points[l].compareTo(points[k]) == 0
                                || points[l].compareTo(points[j]) == 0
                                || points[l].compareTo(points[i]) == 0)
                            throw new IllegalArgumentException();

                        if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
                                && points[i].slopeTo(points[k]) == points[i].slopeTo(points[l]))
                        {
                            segments[index++] = new LineSegment(points[i], points[j]);
                            segments[index++] = new LineSegment(points[j], points[k]);
                            segments[index++] = new LineSegment(points[k], points[l]);
                            segments[index++] = new LineSegment(points[i], points[l]);
                            segmentsLength += 4;
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
        Point[] points = new Point[14];
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                points[i] = new Point(15,18);
                continue;
            }
            points[i] = new Point(i+1,(i+1) * 2);
        }
        for (int i = 4; i < 8; i++) points[i] = new Point(i+2,i+1);
        for (int i = 8; i < 12; i++) points[i] = new Point(i*2,i * 3);
        points[12] = new Point(15, 15);
        points[13] = new Point(15, 17);
        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(points);
        System.out.println(collinearPoints.numberOfSegments());

        StdDraw.enableDoubleBuffering();
        StdDraw.setPenColor(Color.black);
        StdDraw.setPenRadius(0.02);
        StdDraw.setXscale(0, 40);
        StdDraw.setYscale(0, 40);
        for (Point p : points) p.draw();
        StdDraw.setPenColor(Color.red);
        StdDraw.setPenRadius(0.01);
        for (int i = 0; i < collinearPoints.numberOfSegments(); i++) collinearPoints.segments()[i].draw();
        StdDraw.show();
    }
}
