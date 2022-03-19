package Collinear_Points;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FastCollinearPoints {
    private List<LineSegment> collinearLineSegments = new ArrayList<>();

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points){
        if (points == null) throw new IllegalArgumentException();
        for (Point eachPoint : points){
            if (eachPoint == null) throw new IllegalArgumentException();
        }

        int length = points.length;
        if (length < 4) return;
        for(int i = 0; i < length; i++){
            for(int j = i + 1; j < length; j++){
                if (points[i].equals(points[j])) throw new IllegalArgumentException("Same Point!");
            }
        }

        points = Arrays.copyOf(points, length);
        Arrays.sort(points);

        Point[] pointsForSort = Arrays.copyOf(points, length);
        for(Point p : points){
            Arrays.sort(pointsForSort, p.slopeOrder());

            for (int i = 1; i < length;){
                int j = i + 1;
                double test1 =  pointsForSort[0].slopeTo(pointsForSort[i]);
                double test2 = pointsForSort[0].slopeTo(pointsForSort[j]);
                while(j < length && test1 == test2){
                    j++;
                    if (j == length) break;
                    test1 = pointsForSort[0].slopeTo(pointsForSort[i]);
                    test2 = pointsForSort[0].slopeTo(pointsForSort[j]);

                }

                if(j - i >= 3 && pointsForSort[0].compareTo(min(pointsForSort, i, j-1)) < 0){
                    collinearLineSegments.add(new LineSegment(pointsForSort[0], max(pointsForSort, i, j-1)));

                }

                if (j == length) break;

                i = j;

            }
        }




    }

    private Point min(Point[] pts, int low, int high){
        if(low > high || pts == null) throw new IllegalArgumentException();
        Point minPoint = pts[low];
        for(int i = low + 1; i <= high; i++){
            if (pts[i].compareTo(minPoint) < 0) minPoint = pts[i];
        }

        return minPoint;

    }

    private Point max(Point[] pts, int low, int high){
        if(low > high || pts == null) throw new IllegalArgumentException();
        Point maxPoint = pts[low];
        for(int i = low + 1; i <= high; i++){
            if (pts[i].compareTo(maxPoint) > 0) maxPoint = pts[i];
        }

        return maxPoint;

    }
    // the number of line segments
    public int numberOfSegments(){
        return collinearLineSegments.size();
    }

    // the line segments
    public LineSegment[] segments(){
        LineSegment[] result = new LineSegment[numberOfSegments()];
        int cnt = 0;
        for(LineSegment line : collinearLineSegments){
            result[cnt] = line;
            cnt++;
        }

        return result;
    }

    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = input.nextInt();
            int y = input.nextInt();
            points[i] = new Point(x, y);
        }

        // draw the points
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
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
