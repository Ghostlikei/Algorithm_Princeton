package Collinear_Points;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class BruteCollinearPoints {
    private List<LineSegment> collinearLineSegments = new ArrayList<>();

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points){
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

        Point[] tempPoints = Arrays.copyOf(points, length);
        Arrays.sort(tempPoints);
        for(int i = 0; i < length; i++){
            for(int j = i + 1; j < length; j++){
                for(int k = j + 1; k < length; k++){
                    for(int l = k + 1; l < length; l++){
                        double test1 = tempPoints[i].slopeTo(tempPoints[j]);
                        double test2 = tempPoints[i].slopeTo(tempPoints[k]);
                        double test3 = tempPoints[i].slopeTo(tempPoints[l]);
                        if(test1 == test2 && test1 == test3){
                            collinearLineSegments.add(new LineSegment(tempPoints[i], tempPoints[l]));
                        }
                    }
                }
            }
        }


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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

