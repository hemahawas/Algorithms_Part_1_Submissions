import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

import java.util.ArrayList;

public class PointSET {
    private final SET<Point2D> set;
    public PointSET()    {
         this.set = new SET<>();
    }
    // construct an empty set of points

    public boolean isEmpty(){
        return this.set.isEmpty();
    }
    // is the set empty?

    public int size(){
        return this.set.size();
    }
    // number of points in the set

    public void insert(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        this.set.add(p);
    }
    // add the point to the set (if it is not already in the this.set)

    public boolean contains(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        return this.set.contains(p);
    }
    // does the set contain point p?

    public void draw(){
        for(Point2D p : this.set){
            p.draw();
        }
    }
    // draw all points to standard draw

    public Iterable<Point2D> range(RectHV rect){
        if(rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> arrayList = new ArrayList<>();
        for(Point2D p : this.set){
            if(rect.contains(p))
                arrayList.add(p);
        }
        return arrayList;
    }
    // all points that are inside the rectangle (or on the boundary)

    public Point2D nearest(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        if(this.set.isEmpty()) return null;
        double minDistance = 2;
        Point2D minPoint = new Point2D(0,0);
        for(Point2D point : set){
            if(point.distanceTo(p) < minDistance){
                minPoint = point;
                minDistance = point.distanceTo(p);
            }
        }
        return minPoint;
    }             // a nearest neighbor in the set to point p; null if the set is empty

    // unit testing of the methods (optional)
    public static void main(String[] args){
        PointSET pointSET = new PointSET();
//        RectHV rectHV = new RectHV(0.2,0.2,0.5,0.5);
//        pointSET.insert(new Point2D(0.3,0.3));
//        pointSET.insert(new Point2D(0.5,0.5));
//        pointSET.insert(new Point2D(0.1,0.1));
//        pointSET.insert(new Point2D(0.4,0.4));
//        pointSET.insert(new Point2D(0.2,0.2));
//        StdDraw.enableDoubleBuffering();
//        StdDraw.setXscale(0, 1);
//        StdDraw.setYscale(0, 1);
//        pointSET.draw();
//        Point2D p1 = new Point2D(0.15,0.2);
//        Point2D p2 = new Point2D(0.3,0.8);
//        p1.draw();
//        p2.draw();
//        rectHV.draw();
//        StdDraw.show();
//        System.out.println(pointSET.range(rectHV));
//        System.out.println(pointSET.nearest(p1));

        Point2D p1 = new Point2D(0.75,0);
        Point2D p2 = new Point2D(1.0,0.5);
        Point2D p3 = new Point2D(0.75,0.25);
        pointSET.insert(p2);
        pointSET.insert(p3);

        System.out.println(pointSET.nearest(p1));
//        System.out.println("distance between (0.75,0) and (0.75,0.25) is -> " + p1.distanceTo(p3));
    }
}