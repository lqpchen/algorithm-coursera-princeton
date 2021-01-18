/*
see more info at: https://coursera.cs.princeton.edu/algs4/assignments/kdtree/specification.php
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.Iterator;

public class KdTree {

    // kd-tree node
    private class Node {
        public Point2D point;
        public Node left;
        public Node right;
        public int height; // used to determine the coordinate for division

        public Node(Point2D point, Node left, Node right, int height) {
            this.point = point;
            this.left = left;
            this.right = right;
            this.height = height;
        }
    }

    // to store the resulting points for the range query
    private class Point {
        public Point2D point;
        public Point next;
    }

    private Point head;
    private Node root;
    private int sz;

    // construct an empty set of points
    public KdTree() {
        root = null;
        sz = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return sz;
    }

    // update subtree rooted at cur with new point inserted
    // the cur node has height
    private Node insert(Point2D p, Node cur, int height) {
        // base case
        if (cur == null) {
            ++sz;
            return new Node(p, null, null, height);
        }

        // decide if the plane is divided by x or y coord
        Double thisCmp, thatCmp;
        int cmp;
        if (height % 2 == 0) {
            thisCmp = cur.point.x();
            thatCmp = p.x();
        } else {
            thisCmp = cur.point.y();
            thatCmp = p.y();
        }

        // recursive insertion
        cmp = thatCmp.compareTo(thisCmp);
        if (p.equals(cur.point)) {
            return cur;
        }
        if (cmp <= 0) {
            cur.left = insert(p, cur.left, height + 1);
        } else {
            cur.right = insert(p, cur.right, height + 1);
        }
        return cur;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        root = insert(p, root, 0);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Node cur = root;
        Double thisCmp, thatCmp;
        int cmp;
        while (cur != null) {
            // if found
            if (cur.point.equals(p)) {
                return true;
            }

            // determine the dividing orientation
            if (cur.height % 2 == 0) {
                thisCmp = cur.point.x();
                thatCmp = p.x();
            } else {
                thisCmp = cur.point.y();
                thatCmp = p.y();
            }

            // determine the subdivision
            cmp = thatCmp.compareTo(thisCmp);
            if (cmp <= 0) {
                cur = cur.left;
            } else {
                cur = cur.right;
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        //
    }

    private boolean contains(RectHV rect, Point2D p) {
        return p.x() <= rect.xmax() &&
                p.x() >= rect.xmin() &&
                p.y() <= rect.ymax() &&
                p.y() >= rect.ymin();
    }

    private void addToPoint(Point2D point) {
        Point newPt = new Point();
        newPt.point = point;
        newPt.next = head;
        head = newPt;
    }

    private void range(RectHV rect, Node cur) {
        if (cur == null) {
            return;
        }
        //if (rect.contains(cur.point)) {
        if (contains(rect, cur.point)) {
            addToPoint(cur.point);
            range(rect, cur.left);
            range(rect, cur.right);
            return;
        }

        double curCmp, rectMin, rectMax;
        if (cur.height % 2 == 0) {
            curCmp = cur.point.x();
            rectMin = rect.xmin();
            rectMax = rect.xmax();
        } else {
            curCmp = cur.point.y();
            rectMin = rect.ymin();
            rectMax = rect.ymax();
        }

        if (rectMin <= curCmp) {
            range(rect, cur.left);
        }
        if (rectMax >= curCmp) {
            range(rect, cur.right);
        }
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

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        head = null; // reset
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        range(rect, root);

        return new IterablePoint();
    }

    private Point2D nearest(Node cur, Point2D p,
                            double minDist, Point2D minPt,
                            RectHV rect) {
        Point2D candPoint;
        double candDist;

        candDist = cur.point.distanceSquaredTo(p);
        if (candDist < minDist) {
            minDist = candDist;
            minPt = cur.point;
        }

        double thisCmp, thatCmp;
        RectHV leftRect, rightRect, firstRect, secondRect;
        Node firstNode, secondNode;
        if (cur.height % 2 == 0) {
            thisCmp = cur.point.x();
            thatCmp = p.x();
            leftRect = new RectHV(rect.xmin(), rect.ymin(), cur.point.x(), rect.ymax());
            rightRect = new RectHV(cur.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
        } else {
            thisCmp = cur.point.y();
            thatCmp = p.y();
            leftRect = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), cur.point.y());
            rightRect = new RectHV(rect.xmin(), cur.point.y(), rect.xmax(), rect.ymax());
        }

        if (thatCmp <= thisCmp) {
            firstRect = leftRect;
            secondRect = rightRect;
            firstNode = cur.left;
            secondNode = cur.right;
        } else {
            firstRect = rightRect;
            secondRect = leftRect;
            firstNode = cur.right;
            secondNode = cur.left;
        }

        if (firstNode != null) {
            candPoint = nearest(firstNode, p, minDist, minPt, firstRect);
            candDist = candPoint.distanceSquaredTo(p);
            if (candDist < minDist) {
                minDist = candDist;
                minPt = candPoint;
            }
        }
        if (secondRect.distanceSquaredTo(p) < minDist && secondNode != null) {
            candPoint = nearest(secondNode, p, minDist, minPt, secondRect);
            candDist = candPoint.distanceSquaredTo(p);
            if (candDist < minDist) {
                // minDist = candDist;
                minPt = candPoint;
            }
        }

        return minPt;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }
        double minDist = Double.POSITIVE_INFINITY;
        Point2D minPt = new Point2D(0, 0);

        RectHV rect = new RectHV(0, 0, 1, 1);
        minPt = nearest(root, p, minDist, minPt, rect);

        return minPt;
    }

    public static void main(String[] args) {
        KdTree kdt = new KdTree();
        double [] xs = {0.1, 0.9, 0.5, 0.3, 0.7};
        double [] ys = {0.5, 0.2, 0.8, 0.1, 0.9};
        for (int i = 0; i < 5; ++i) {
            kdt.insert(new Point2D(xs[i], ys[i]));
        }
        System.out.println(kdt.nearest(new Point2D(0.6, 0.7)));

    }

}
