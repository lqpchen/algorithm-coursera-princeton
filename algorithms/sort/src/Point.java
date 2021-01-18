/*
an immutable data type Point that represents a point in the plane
see more info at: https://coursera.cs.princeton.edu/algs4/assignments/collinear/specification.php
 */


import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    /*public void draw() {
        StdDraw.point(x, y);
    }*/

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     */
    /*public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }*/

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     */
    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0) {
            return Double.NEGATIVE_INFINITY;
        } else if (this.y == that.y) {
            return +0.0;
        } else if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        } else {
            return Double.valueOf(that.y - this.y) / Double.valueOf(that.x - this.x);
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     */
    public int compareTo(Point that) {
        if (this.y < that.y) {
            return -1;
        } else if (this.y > that.y) {
            return 1;
        }
        if (this.x < that.x) {
            return -1;
        } else if (this.x > that.x) {
            return 1;
        }
        return 0;
    }

    private class SlopeOrder implements Comparator<Point> {

        public int compare(Point p1, Point p2) {
            if (slopeTo(p1) < slopeTo(p2)) {
                return -1;
            } else if (slopeTo(p1) > slopeTo(p2)) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new SlopeOrder();
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p1 = new Point(289, 29);
        Point p2 = new Point(483, 290);
        Point p3 = new Point(0, -1);
        System.out.println(p1.slopeTo(p2));
        System.out.println(p1.compareTo(p3));

        System.out.println(+0.0 == +0.0);
    }
}

