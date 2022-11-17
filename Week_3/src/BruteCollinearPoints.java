import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class BruteCollinearPoints {

    private final LinkedList<LineSegment> segments = new LinkedList<>();

    public BruteCollinearPoints(Point[] points){
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if(points[i] == null) throw new IllegalArgumentException();
            for (int j = 0; j < points.length; j++) {
                if (points[i] == points[j] && i != j) throw new IllegalArgumentException();
            }
        }
        // sort the slope with origin respect
        Point[] aux = new Point[points.length];
        System.arraycopy(points, 0, aux, 0, points.length);
        LinkedList<Point> list = new LinkedList<>();
        LinkedList<Point> firstList = new LinkedList<>();
        LinkedList<Point> lastList = new LinkedList<>();

        for (Point point : points) {
            Arrays.sort(aux, point.slopeOrder());
            for (int i = 1; i < aux.length; i++) {
                list.addLast(point);
                if(aux[i].compareTo(point) > 0) {
                    for (int j = 2; j < aux.length; j++) {
                        if (point.slopeTo(aux[i]) == point.slopeTo(aux[j])  && i != j) {
                            list.addLast(aux[j]);
                            if (list.size() == 3) {
                                list.addLast(aux[i]);
                                list.sort(Comparator.naturalOrder());
                                if(!firstList.contains(point) && !lastList.contains(list.getLast())){
                                    firstList.add(point);
                                    lastList.add(list.getLast());
                                    segments.addLast(new LineSegment(list.getFirst(), list.getLast()));
                                    list.clear();
                                    break;
                                }
                            }
                        }
                    }
                }
                list.clear();
            }
        }
    }
       // finds all line segments containing 4 points

    public int numberOfSegments(){
        return this.segments.size();
    }        // the number of line segments

    public LineSegment[] segments(){
        LineSegment[] seg = new LineSegment[this.segments.size()];
        for (int i = 0; i < this.segments.size(); i++) {
            seg[i] = this.segments.get(i);
        }
        return seg;
    }
    // the line segments
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

         //draw the points
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
            System.out.println(segment.toString());
            segment.draw();
        }
        StdDraw.show();
    }
}

class LineSegment {
    private final Point p;   // one endpoint of this line segment
    private final Point q;   // the other endpoint of this line segment

    /**
     * Initializes a new line segment.
     *
     * @param  p one endpoint
     * @param  q the other endpoint
     * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt>
     *         is <tt>null</tt>
     */
    public LineSegment(Point p, Point q) {
        if (p == null || q == null) {
            throw new IllegalArgumentException("argument to LineSegment constructor is null");
        }
        if (p.equals(q)) {
            throw new IllegalArgumentException("both arguments to LineSegment constructor are the same point: " + p);
        }
        this.p = p;
        this.q = q;
    }


    /**
     * Draws this line segment to standard draw.
     */
    public void draw() {
        p.drawTo(q);
    }

    /**
     * Returns a string representation of this line segment
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this line segment
     */
    public String toString() {
        return p + " -> " + q;
    }

    /**
     * Throws an exception if called. The hashCode() method is not supported because
     * hashing has not yet been introduced in this course. Moreover, hashing does not
     * typically lead to good *worst-case* performance guarantees, as required on this
     * assignment.
     *
     * @throws UnsupportedOperationException if called
     */
    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported");
    }
}

