import edu.princeton.cs.algs4.StdDraw;
import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private final Point[] points;
    private int arrayIndex;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        this.points = points;
        segments = new LineSegment[0];

        Point[] pointsOriginal = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            for (int j = 0; j < points.length; j++) {
                if (i != j && points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException();
            }
            pointsOriginal[i] = points[i];
        }

        for (int i = 0; i < points.length; i++) {
            Point p = pointsOriginal[i];
            Arrays.sort(points, p.slopeOrder());

            // simulate circular array
            arrayIndex = 1;
            int startPoint = arrayIndex;
            int endPoint = arrayIndex;
            int iterationCount = 0;
            while (iterationCount < points.length - 1) {

                //start new segment lookup
                if (startPoint == arrayIndex) {

                    // more than 1 points remains
                    if (iterationCount + 2 < points.length) {

                        // segment starts to build
                        if (p.slopeTo(points[getArrayIndex(0)])
                                == points[getArrayIndex(0)].slopeTo(points[getArrayIndex(1)])) {
                            startPoint = getArrayIndex(0);
                            endPoint = setArrayIndex(1);
                        }
                        // segment doesn't start to build
                        else {
                            startPoint = setArrayIndex(1);
                            endPoint = getArrayIndex(0);
                        }

                        // increment iteration counter
                        iterationCount++;
                    }
                    // no more points for segments
                    else {
                        // end while loop
                        iterationCount = points.length;
                    }
                }

                // segment is being built
                else {

                    // segment can continue && continues on
                    if (points[getArrayIndex(-1)].slopeTo(points[getArrayIndex(0)])
                            == points[getArrayIndex(0)].slopeTo(points[getArrayIndex(1)])) {
                        endPoint = setArrayIndex(1);

                        // start from beginning to see if segment continues on -> hold iteration counter
                        if (!(iterationCount == points.length - 2)) {
                            iterationCount++;
                        }
                    }

                    // segment ends
                    else {
                        // segment is built if 3 or more points
                        if (getDiff(endPoint, startPoint) >= 3) {

                            // save the old value of arrayIndex and use arrayIndex functions to create new array for segments
                            int oldArrayIndex = arrayIndex;
                            arrayIndex = startPoint;

                            // create array of points to sort and get the first and last point of line segment
                            Point[] lineSegmentPoints = new Point[getDiff(endPoint, startPoint) + 1];
                            for (int j = 0; j < lineSegmentPoints.length; j++) {
                                if (j == 0)
                                    lineSegmentPoints[j] = p;
                                else
                                    lineSegmentPoints[j] = points[getArrayIndex(j - 1)];
                            }
                            Arrays.sort(lineSegmentPoints);

                            // add line segment if the first point of the line is the same as the first point in the sorted array
                            if (lineSegmentPoints[0] == p) {
                                addSegment(new LineSegment(lineSegmentPoints[0], lineSegmentPoints[getDiff(endPoint, startPoint)]));
//                                segments[lineSegmentsIndex++] = new LineSegment(lineSegmentPoints[0], lineSegmentPoints[getDiff(endPoint, startPoint)]);
//                                segCount++;
                            }

                            // return the old value of arrayIndex
                            arrayIndex = oldArrayIndex;
                        }
                        startPoint = getArrayIndex(0);
                        endPoint = getArrayIndex(0);
                        iterationCount++;
                    }
                }
            }
        }
    }

    private int setArrayIndex(int increment) {
        arrayIndex = incrementIndex(arrayIndex, increment);
        return arrayIndex;
    }

    private int getArrayIndex(int increment) {
        int index = arrayIndex;
        return incrementIndex(index, increment);
    }

    private int incrementIndex(int index, int increment) {
        while (increment > 0) {
            if (index == points.length - 1)
                index = 0;
            else
                index++;
            increment--;
        }
        while (increment < 0) {
            if (index == 0)
                index = points.length - 1;
            else
                index--;
            increment++;
        }
        return index;
    }

    private int getDiff(int a, int b) {
        if (a < b) return - (a - b + 1);
        else if (a == b) return points.length;
        else return (a - b + 1);
    }

    private void addSegment(LineSegment segment) {
        LineSegment[] newSegments = new LineSegment[segments.length + 1];
        for (int i = 0; i < segments.length; i++) {
            newSegments[i] = segments[i];
        }
        newSegments[newSegments.length - 1] = segment;
        segments = newSegments;
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}
