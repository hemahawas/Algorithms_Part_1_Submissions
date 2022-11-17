import edu.princeton.cs.algs4.BST;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree {
    private final BST<Point2D, Node> bst;
    private final Node root;

    // construct an empty set of points
    public KdTree(){
        this.bst = new BST<>();
        root = new Node();
        root.rect = new RectHV(0,0,1,1);
    }

    // is the set empty?
    public boolean isEmpty(){
        return this.bst.isEmpty();
    }

    // number of points in the set
    public int size(){
        return this.bst.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        if(isEmpty()){
            root.p = p;
            this.bst.put(p,root);
            return;
        }
        Node current = root;
        boolean isHorizontal = true;
        while (true){
            if(isHorizontal) {
                if (p.x() < current.p.x()) {
                    if (current.lb == null) {
                        current.lb = new Node();
                        current.lb.p = p;
                        current.lb.rect = new RectHV(current.rect.xmin(),current.rect.ymin(),current.p.x(),current.rect.ymax());
                        this.bst.put(p, current.lb);
                        break;
                    } else {
                        current = current.lb;
                        isHorizontal = !isHorizontal;
                    }
                } else {
                    if (current.rt == null) {
                        current.rt = new Node();
                        current.rt.p = p;
                        current.rt.rect = new RectHV(current.p.x(),current.rect.ymin(),current.rect.xmax(),current.rect.ymax());
                        this.bst.put(p, current.rt);
                        break;
                    } else {
                        current = current.rt;
                        isHorizontal = !isHorizontal;
                    }
                }
            }else{
                if (p.y() < current.p.y()) {
                    if (current.lb == null) {
                        current.lb = new Node();
                        current.lb.p = p;
                        current.lb.rect = new RectHV(current.rect.xmin(),current.rect.ymin(),current.rect.xmax(),current.p.y());
                        this.bst.put(p, current.lb);
                        break;
                    } else {
                        current = current.lb;
                        isHorizontal = !isHorizontal;
                    }
                } else {
                    if (current.rt == null) {
                        current.rt = new Node();
                        current.rt.p = p;
                        current.rt.rect = new RectHV(current.rect.xmin(),current.p.y(),current.rect.xmax(),current.rect.ymax());
                        this.bst.put(p, current.rt);
                        break;
                    } else {
                        current = current.rt;
                        isHorizontal = !isHorizontal;
                    }
                }
            }
        }
    }
    // does the set contain point p?
    public boolean contains(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        return this.bst.contains(p);
    }

    // draw all points to standard draw
    public void draw(){
        //Initialization
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        draw(root,0,0,0);
        StdDraw.show();
    }

    private void draw(Node current, int isHorizontal, int rt, double m){
        if(current == null) return;

        if(current == root){
            //Draw Vertical Line
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(current.p.x(),0,current.p.x(),1);
            //call the left/bottom
            draw(current.lb, 1, 0, current.p.x());
            //call the right/top
            draw(current.rt, 1, 1, current.p.x());
        }

        if(isHorizontal == 1){
            //Draw Horizontal Line
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            if(rt == 1){
                StdDraw.line(m, current.p.y(),1,current.p.y());
            }
            else{
                StdDraw.line(0, current.p.y(),m,current.p.y());
            }
            //call the left/bottom
            draw(current.lb, 0, 0, current.p.y());
            //call the right/top
            draw(current.rt, 0, 1, current.p.y());
        }else {
            //Draw Vertical Line
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            if(rt == 1){
                StdDraw.line(current.p.x(), m,current.p.x(),1);
            }
            else{
                StdDraw.line(current.p.x(), 0,current.p.x(),m);
            }
            //call the left/bottom
            draw(current.lb, 1, 0, current.p.x());
            //call the right/top
            draw(current.rt, 1, 1, current.p.x());
        }

        //Draw Point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        current.p.draw();

        //current.rect.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect){
        if(rect == null) throw new IllegalArgumentException();
        if(this.bst.isEmpty()) return null;
        ArrayList<Point2D> list = new ArrayList<>();
        range(root,rect,list);
        return list;
    }

    private void range(Node current,RectHV rect, ArrayList<Point2D> list){
        if(current == null) return;
        if(rect.contains(current.p) && !list.contains(current.p)){
            list.add(current.p);
        }
        range(current.lb, rect, list);
        range(current.rt, rect, list);
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p){
        if(p == null) throw new IllegalArgumentException();
        if(this.bst.isEmpty()) return null;
        //Base case
        Point2D nearestPoint = root.p;

        return nearest(root, p, nearestPoint);
    }

    private Point2D nearest(Node current, Point2D desiredPoint, Point2D nearestPoint){
        if(current == null) return null;

        if(current.p.distanceTo(desiredPoint) < nearestPoint.distanceTo(desiredPoint)){
            nearestPoint = current.p;
        }

        if(current.lb != null)
            if(current.lb.rect.contains(desiredPoint) || current.lb.rect.distanceTo(desiredPoint) < nearestPoint.distanceTo(desiredPoint))
                nearestPoint = nearest(current.lb,desiredPoint,nearestPoint);

        if(current.rt != null)
            if(current.rt.rect.contains(desiredPoint) || current.rt.rect.distanceTo(desiredPoint) < nearestPoint.distanceTo(desiredPoint))
                nearestPoint = nearest(current.rt,desiredPoint,nearestPoint);

        return nearestPoint;
    }

    private static class Node{
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
    }

    // unit testing of the methods (optional)
    public static void main(String[] args){
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.7,0.2));
        kdTree.insert(new Point2D(0.5,0.4));
        kdTree.insert(new Point2D(0.2,0.3));
        kdTree.insert(new Point2D(0.4,0.7));
        kdTree.insert(new Point2D(0.9,0.6));
        RectHV rectHV = new RectHV(0,0,0.5,0.5);
        System.out.println(kdTree.range(rectHV));
        kdTree.draw();
    }

}
