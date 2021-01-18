/*
examines 4 points at a time and checks whether they all lie on the same line segment
see more info at: https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php
 */

public class BruteCollinearPoints {

    private final Point [] points;
    private LineSegment [] segments;
    private int numSeg;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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
        numSeg = 0;

        // brute force calculation
        double [] slopes = new double[3];
        Point s, e; // start point and end point of the segment
        for (int i = 0; i < n; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = j + 1; k < n; ++k) {
                    for (int m = k + 1; m < n; ++m) {
                        slopes[0] = this.points[m].slopeTo(this.points[i]);
                        slopes[1] = this.points[m].slopeTo(this.points[j]);
                        slopes[2] = this.points[m].slopeTo(this.points[k]);
                        if (slopes[0] == slopes[1] && slopes[0] == slopes[2]) {
                            s = minPoint(minPoint(this.points[i], this.points[j]), minPoint(this.points[k], this.points[m]));
                            e = maxPoint(maxPoint(this.points[i], this.points[j]), maxPoint(this.points[k], this.points[m]));
                            addSegment(s, e);
                        }
                    }
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

    private Point minPoint(Point p1, Point p2) {
        if (p1.compareTo(p2) <= 0) {
            return p1;
        } else {
            return p2;
        }
    }

    private Point maxPoint(Point p1, Point p2) {
        if (p1.compareTo(p2) >= 0) {
            return p1;
        } else {
            return p2;
        }
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



    }
}
