package Percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int Trials;
    private int depth;
    private double[] percoThreshold;
    private double area;
    //perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        area = n*n;
        depth = n;
        Trials = trials;
        percoThreshold = new double[Trials];
        for (int i = 0; i < Trials; i++){
            Percolation perc = new Percolation(n);
            while (!perc.percolates()){
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                perc.open(row, col);

            }
            percoThreshold[i] = perc.numberOfOpenSites() / area;
        }
    }

    //sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(percoThreshold);
    }

    //sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(percoThreshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - (1.96*stddev())/Math.sqrt(Trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + (1.96*stddev())/Math.sqrt(Trials);
    }

    // test client
    public static void main(String[] args){
        if (args.length != 2) System.out.println("Usage: java PercolationStats n T");
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats percoStats = new PercolationStats(n, t);
        System.out.println("Mean:                      "+percoStats.mean());
        System.out.println("Stddev:                    "+percoStats.stddev());
        System.out.println("95% of confidence interval:"+"["+percoStats.confidenceLo() + "," + percoStats.confidenceHi() +"]");
    }

}
