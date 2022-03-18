package Percolation;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation {
    //creates n-by-n grid, with all sties initially blocked
    private boolean[][] openPositions;
    private boolean isPercolated;
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF ufForFull;
    private int neigh1[] = {1,0,-1,0};
    private int neigh2[] = {0,1,0,-1};
    private int OpenNumbers;
    private int depth;
    public Percolation(int n){
        if (n <= 0) throw new IllegalArgumentException("Your size of the system must be > 0 !");
        openPositions = new boolean[n][n];
        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                openPositions[i][j] = false;
            }
        }
        uf = new WeightedQuickUnionUF(n*n +2);
        ufForFull = new WeightedQuickUnionUF(n*n +1);
        isPercolated = false;
        depth = n;

    }

    public int calIndex(int row, int col){
        return (row-1)*depth + col;
    }

    public boolean isInGrid(int row, int col){
        if ((row <= 0 || row > depth) || (col <= 0 || col > depth)){
            return false;
        }
        return true;
    }

    //opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if ((row <= 0 || row > depth) || (col <= 0 || col > depth)){
            throw new IllegalArgumentException("Your brick is out of the System!");
        }
        if(!isOpen(row, col)){
            openPositions[row-1][col-1] = true;
            if(row == 1){
                uf.union(calIndex(row, col), 0);
                ufForFull.union(calIndex(row, col), 0);
            }

            if(row == depth) uf.union(calIndex(row, col), depth*depth+1);
            unionNeighbours(row, col);
            OpenNumbers++;
        }
    }

    public void unionNeighbours(int row, int col){
        if ((row < 0 || row > depth) || (col < 0 || col > depth)){
            throw new IllegalArgumentException("Your brick is out of the System!");
        }

        int currIndex = calIndex(row, col);
        for(int i = 0; i < 4; i++){
            int newRow = row + neigh1[i];
            int newCol = col + neigh2[i];
            if(isInGrid(newRow, newCol) && isOpen(newRow, newCol)){
                uf.union(currIndex, calIndex(newRow, newCol));
                ufForFull.union(currIndex, calIndex(newRow, newCol));
            }
        }
    }

    //is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if ((row <= 0 || row > depth) || (col <= 0 || col > depth)){
            throw new IllegalArgumentException("Your brick is out of the System!");
        }
        return openPositions[row-1][col-1];
    }

    //is the site (row,col) full?
    public boolean isFull(int row, int col){
        if ((row <= 0 || row > depth) || (col <= 0 || col > depth)){
            throw new IllegalArgumentException("Your brick is out of the System!");
        }
        return ufForFull.find(calIndex(row,col)) == ufForFull.find(0);
    }

    //returns the number of open sites
    public int numberOfOpenSites(){
        return OpenNumbers;
    }

    //does the system percolate
    public boolean percolates(){
        return uf.find(depth*depth + 1) == uf.find(0);
    }

    // test client
    public static void main(String[] args){
        Percolation perc = new Percolation(3);
        perc.open(1,3);
        perc.open(2,3);
        perc.open(2,2);
        StdOut.println(perc.percolates());
        perc.open(3,2);
        StdOut.println(perc.percolates());
    }
}
