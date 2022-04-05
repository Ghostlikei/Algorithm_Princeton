package Kd_Trees;

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> points;
    // construct an empty set of points
    public PointSET(){
        points = new SET<>();
    }

    // is the set empty?
    public boolean isEmpty(){
        return points.isEmpty();
    }

    // number of points in the set
    public int size(){
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }

    //does the set contain point p?
    public boolean contains(Point2D p){
        if (p == null) throw new IllegalArgumentException();
        return points.contains(p);
    }

    //draw all points to standard draw
    public void draw(){
        for (Point2D point: points){
            point.draw();
        }
    }

    // all points that inside the rectangle
    public Iterable<Point2D> range(RectHV rect){
        if (rect == null ) throw new IllegalArgumentException();
        ArrayList<Point2D> pointsInside = new ArrayList<>();
        for(Point2D point : points){
            if (rect.contains(point)){
                pointsInside.add(point);
            }
        }

        return pointsInside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p){
        if (p == null) throw new IllegalArgumentException();
        Point2D result = null;
        double distance = Double.MAX_VALUE;
        for(Point2D point : points){
            if(p.distanceTo(point) < distance){
                result = point;
                distance = p.distanceTo(point);
            }
        }

        return result;
    }

    public static void main(String[] args){
        return;
    }
}
