/*
see more info at: https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> points;
    private Point head;

    private class Point {
        public Point2D point;
        public Point next;
    }

    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<>();
        head = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        points.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Iterator<Point2D> iter = points.iterator(); iter.hasNext(); ) {
            Point2D point = iter.next();
            point.draw();
        }
    }

    private void addToPoint(Point2D point) {
        Point newPt = new Point();
        newPt.point = point;
        newPt.next = head;
        head = newPt;
    }

    private class IterablePoint implements Iterable<Point2D> {
        private class IterPoint implements Iterator<Point2D> {
            private Point cur;

            public IterPoint() {
                cur = head;
            }

            public boolean hasNext() {
                return cur != null;
            }

            public Point2D next() {
                Point2D p = cur.point;
                cur = cur.next;
                return p;
            }

            public void remove() {
                return;
            }
        }

        public Iterator<Point2D> iterator() {
            return new IterPoint();
        }
    }

    private boolean contains(RectHV rect, Point2D p) {
        return p.x() <= rect.xmax() &&
                p.x() >= rect.xmin() &&
                p.y() <= rect.ymax() &&
                p.y() >= rect.ymin();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        head = null;
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        for (Iterator<Point2D> iter = points.iterator(); iter.hasNext(); ) {
            Point2D point = iter.next();
            // if (rect.contains(point)) {
            if (contains(rect, point)) {
                addToPoint(point);
            }
        }
        return new IterablePoint();
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (points.isEmpty()) {
            return null;
        }
        double minDist = Double.POSITIVE_INFINITY;
        Point2D minPt = new Point2D(0, 0);
        for (Iterator<Point2D> iter = points.iterator(); iter.hasNext(); ) {
            Point2D point = iter.next();
            double curDist = p.distanceSquaredTo(point);
            if (curDist < minDist) {
                minDist = curDist;
                minPt = point;
            }
        }
        return minPt;
    }

    /*public static void main(String[] args) {
        PointSET set = new PointSET();
        set.isEmpty();
        set.insert(new Point2D(1, 1));
        set.range(new RectHV(0, 1, 1, 1));
        set.insert(new Point2D(0.0, 0.0));
        set.nearest(new Point2D(0, 1));
        set.range(new RectHV(0, 0, 0, 0));
        set.insert(new Point2D(0.0, 0.0));
        set.insert(new Point2D(1.0, 1.0));
        set.insert(new Point2D(0.0, 1.0));
        set.insert(new Point2D(1.0, 0.0));
        set.size();
        // set.range([1.0, 1.0] x [0.0, 1.0])
        Iterable<Point2D> iter = set.range(new RectHV(1, 0, 1, 1));
        for (Point2D p : iter) {
            System.out.println(p);
        }

    }*/

    public static void main(String[] args) {
        PointSET set = new PointSET();
        set.isEmpty();
        set.insert(new Point2D(1, 1));
        set.range(new RectHV(0, 0, 1, 1));

        // set.range([1.0, 1.0] x [0.0, 1.0])
        Iterable<Point2D> iter = set.range(new RectHV(0, 0, 0, 1));
        for (Point2D p : iter) {
            System.out.println(p);
        }

    }
}
