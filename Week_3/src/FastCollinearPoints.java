import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;

public class FastCollinearPoints {
    private final LinkedList<LineSegment> segments = new LinkedList<>();

    public FastCollinearPoints(Point[] points){
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length; i++) {
            if(points[i] == null) throw  new IllegalArgumentException();
            for (int j = 0; j < points.length; j++){
                if(i != j && points[i] == points[j]){
                    throw new IllegalArgumentException();
                }
            }
        }
        Point[] aux = new Point[points.length];

        System.arraycopy(points, 0, aux, 0, points.length);
        // sort the slope with origin respect
        LinkedList<Point> list = new LinkedList<>();
        LinkedList<Point> firstList = new LinkedList<>();
        LinkedList<Point> lastList = new LinkedList<>();

        for (Point point : points) {
            Arrays.sort(aux, point.slopeOrder());
            for (int i = 1; i < aux.length-1; i++) {
                list.addLast(point);
                while(point.slopeTo(aux[i]) == point.slopeTo(aux[i+1])){
                    list.addLast(aux[i]);
                    i++;
                    if(i == aux.length-1){
                        break;
                    }
                }
                if(list.size() >= 3){
                    list.addLast(aux[i]);
                    list.sort(Comparator.naturalOrder());
                    if (!firstList.contains(list.getFirst()) || !lastList.contains(list.getLast())) {
                        firstList.add(list.getFirst());
                        lastList.add(list.getLast());
                        segments.addLast(new LineSegment(list.getFirst(), list.getLast()));
                    }
                    break;
                }
                else
                    list.clear();
            }
            list.clear();
        }

    }     // finds all line segments containing 4 or more points
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            System.out.println(segment.toString());
            segment.draw();
        }
        StdDraw.show();
    }// the line segments
}
