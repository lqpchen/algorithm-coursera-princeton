/*
a faster and sorting-based solution
* */

import java.util.Arrays;

public class FastCollinearPoints {

    private final Point [] points;
    private LineSegment [] segments;
    private int numSeg;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        int n = points.length;
        this.points = new Point[n];
        for (int i = 0; i < n; ++i) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
            this.points[i] = points[i];
        }

        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        segments = new LineSegment[16];

        for (int i = 0; i < n; ++i) {
            // for each point, calculate its slope to all the rest points
            Arrays.sort(this.points, points[i].slopeOrder());

            // one pass scan for same slopes against the points[i]
            int s = 1, e = 1;
            for (int j = 2; j < n; ++j) {
                if (points[i].slopeTo(this.points[j - 1]) == points[i].slopeTo(this.points[j])) {
                    e = j;
                }
                if (points[i].slopeTo(this.points[j - 1]) != points[i].slopeTo(this.points[j]) || j == n - 1) {
                    if (e - s >= 2) {
                        // [i, s..e] form a segment
                        Point[] segPts = new Point[e - s + 2];
                        boolean flag = true; // set false when duplicated segments detected
                        segPts[0] = points[i];
                        for (int k = s; k <= e; ++k) {
                            if (this.points[k].compareTo(points[i]) < 0) {
                                flag = false;
                                break;
                            }
                            segPts[k - s + 1] = this.points[k];
                        }
                        if (flag) {
                            Arrays.sort(segPts);
                            addSegment(segPts[0], segPts[e - s + 1]);
                        }
                    }
                    s = j;
                    e = j;
                }
            }
        }
        adjSegSize(numSeg);
    }


    private void addSegment(Point p1, Point p2) {
        if (numSeg == segments.length) {
            adjSegSize(segments.length * 2);
        }
        segments[numSeg++] = new LineSegment(p1, p2);
    }

    private void adjSegSize(int size) {
        int n = numSeg;
        LineSegment [] copy = new LineSegment[size];
        for (int i = 0; i < Math.min(n, size); ++i) {
            copy[i] = segments[i];
            segments[i] = null;
        }
        segments = copy;
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return numSeg;
    }

    // the line segments
    public LineSegment[] segments() {
        int n = segments.length;
        LineSegment [] copy = new LineSegment[n];
        for (int i = 0; i < n; ++i) {
            copy[i] = segments[i];
        }
        return copy;
    }

    public static void main(String[] args) {
        int [] xs = {30000, 30950, 29550, 23000, 30001, 30002, 30003};
        int [] ys = {8500, 8500, 8500, 8500, 8501, 8502, 8503}; // {4, 2, 3, 1};
        int n = 7;
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(xs[i], ys[i]);
        }


        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment);
        }
    }
}
