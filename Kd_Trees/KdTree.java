package Kd_Trees;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class KdTree{
    private class Node{
        private Point2D point;
        private boolean isVertical;
        private Node smallerNode;
        private Node biggerNode;
        private RectHV rect;

        public Node(Point2D p, boolean vertical, RectHV curRect){
            point = p;
            isVertical = vertical;
            smallerNode = null;
            biggerNode = null;
            // smallerNode = leftNode;
            // biggerNode = rightNode;
            rect = curRect;
        }

        public boolean isVertical(){
            return this.isVertical;
        }

        public RectHV leftRect(){
            if(isVertical()) return new RectHV(rect.xmin(), rect.ymin(), point.x(), rect.ymax());
            else return new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), point.y());
        }

        public RectHV rightRect(){
            if(isVertical()) return new RectHV(point.x(), rect.ymin(), rect.xmax(), rect.ymax());
            else return new RectHV(rect.xmin(), point.y(), rect.xmax(), rect.ymax());
        }
    }

    private Node root;
    private int size;

    public KdTree(){
        size = 0;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    private Node insert(Point2D p, Node node, Node parent, boolean isSmaller){

        if(node == null) {
            size++;
            if (parent == null){

                return new Node(p, true, new RectHV(0, 0, 1, 1));
            }
            else{
                if (isSmaller){
                    return new Node(p, !parent.isVertical(), parent.leftRect());
                }
                else{
                    return new Node(p, !parent.isVertical(), parent.rightRect());
                }
            }
        }
        else if(p.equals(node.point)){
            return node;
        }
        else if(node.isVertical()){

            if (p.x() < node.point.x()){
                node.smallerNode = insert(p, node.smallerNode, node, true);
            }
            else {
                node.biggerNode = insert(p, node.biggerNode, node, false);
            }
        }
        else {
            if(p.y() < node.point.y()){
                node.smallerNode = insert(p, node.smallerNode, node, true);
            }
            else{
                node.biggerNode = insert(p, node.biggerNode, node, false);
            }
        }
        return node;
    }

    public void insert(Point2D p){
        if (p == null) throw new IllegalArgumentException();
        root = insert(p, root, null, true);
    }

    private boolean contains(Node node, Point2D p){
        if (node == null) return false;
        if (p.equals(node.point)) return true;
        if (node.isVertical()){
            if (p.x() < node.point.x()){
                return contains(node.smallerNode, p);
            }
            else{
                return contains(node.biggerNode,p);
            }
        }
        else{
            if (p.y() < node.point.y()){
                return contains(node.smallerNode, p);
            }
            else{
                return  contains(node.biggerNode, p);
            }
        }
    }

    public boolean contains(Point2D p){
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p);
    }

    private void draw(Node node, Node parent){
        if (node == null) {
            return;
        }
        if (parent == null) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.point.x(), 0, node.point.x(), 1);
        } else if (node.isVertical()) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.point.x(), node.point.y());
        draw(node.smallerNode, node);
        draw(node.biggerNode, node);

    }

    public void draw(){
        draw(root, null);
    }

    private void range(RectHV rect, ArrayList<Point2D> points, Node node){
        if (node == null) return;
        if (rect.contains(node.point)){
            points.add(node.point);
        }
        if (node.isVertical()){
            if (node.point.x() > rect.xmax()){
                range(rect, points, node.smallerNode);
            }
            else if (node.point.x() < rect.xmin()){
                range(rect, points, node.biggerNode);
            }
            else {
                range(rect, points, node.smallerNode);
                range(rect, points, node.biggerNode);
            }
        }
        else{
            if (node.point.y() > rect.ymax()){
                range(rect, points, node.smallerNode);
            }
            else if (node.point.y() < rect.ymin()){
                range(rect, points, node.biggerNode);
            }
            else {
                range(rect, points, node.smallerNode);
                range(rect, points, node.biggerNode);
            }
        }
    }

    public Iterable<Point2D> range(RectHV rect){
        if (rect == null) throw new IllegalArgumentException();
        ArrayList<Point2D> points = new ArrayList<>();
        range(rect, points, root);
        return points;
    }

    private double distance;
    private Point2D nearestPoint;
    private double getDistanceP(Point2D lhs, Point2D rhs){
        if (lhs.x() == rhs.x() && lhs.y() == rhs.y()){
            return Double.MAX_VALUE;
        }
        return lhs.distanceSquaredTo(rhs);
    }

    private double getDistanceR(Node pForRect, Point2D rhs, boolean isLeft){
        if (pForRect.point.x() == rhs.x() && pForRect.point.y() == rhs.y()){
            return Double.MAX_VALUE;
        }
        if (isLeft) return pForRect.leftRect().distanceSquaredTo(rhs);
        return pForRect.rightRect().distanceSquaredTo(rhs);
    }
    private void nearest(Point2D p , Node node){
        if (node == null) return;
        if (p.equals(node)) return;
        if (getDistanceP(p, node.point) < distance){
            nearestPoint = node.point;
            distance = p.distanceSquaredTo(node.point);
        }

        boolean searchLeft = false;
        boolean searchRight = false;
        if (node.smallerNode != null && getDistanceR(node, p, true) < distance){
            searchLeft = true;
        }
        if (node.biggerNode != null && getDistanceR(node, p, false) < distance){
            searchRight = true;
        }

        if (searchLeft && searchRight){
            if (node.isVertical()){
                if (p.x() < node.point.x()) {
                    nearest(p, node.smallerNode);
                    if (getDistanceR(node, p, false) < distance){
                        nearest(p, node.biggerNode);
                    }
                }
                else{
                    nearest(p, node.biggerNode);
                    if(getDistanceR(node, p, true) < distance){
                        nearest(p, node.smallerNode);
                    }
                }
            }

            else{
                if (p.y() < node.point.y()) {
                    nearest(p, node.smallerNode);
                    if (getDistanceR(node, p, false) < distance){
                        nearest(p, node.biggerNode);
                    }
                }
                else{
                    nearest(p, node.biggerNode);
                    if(getDistanceR(node, p, true) < distance){
                        nearest(p, node.smallerNode);
                    }
                }
            }
        }
        else{
            if (searchLeft) nearest(p, node.smallerNode);
            if (searchRight) nearest(p, node.biggerNode);
        }
    }

    public Point2D nearest(Point2D p){
        if (size <= 1) throw new UnsupportedOperationException();
        if (p == null) throw new IllegalArgumentException();
        distance = Double.MAX_VALUE;
        nearestPoint = null;
        nearest(p, root);
        return nearestPoint;
    }

    public static void main(String[] args) {
        KdTree kdtree = new KdTree();
        kdtree.insert(new Point2D(0.7, 0.2));
        System.out.println(kdtree.size());
        System.out.println(kdtree.contains(new Point2D(0.7, 0.2)));

        kdtree.insert(new Point2D(0.5, 0.4));
        System.out.println(kdtree.size());
        System.out.println(kdtree.contains(new Point2D(0.5, 0.3)));

        ;

        kdtree.insert(new Point2D(0.2, 0.3));
        kdtree.insert(new Point2D(0.4, 0.7));
//        kdtree.insert(new Point2D(0.9, 0.6));
        kdtree.draw();
    }


}
