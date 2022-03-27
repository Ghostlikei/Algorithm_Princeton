package EightPuzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import java.util.Arrays;

public class Solver {
    private Board[] solution;
    private boolean solvable;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        solvable = false;
        MinPQ<searchNode> gameTree = new MinPQ<>();
        if(initial == null) throw new IllegalArgumentException();
        gameTree.insert(new searchNode(initial));
        gameTree.insert(new searchNode(initial.twin()));
        while(!gameTree.isEmpty()){
            StdOut.println("Size" + gameTree.size());
            searchNode minNode = gameTree.delMin();
            for(Board neighbour : minNode.curboard.neighbors()){

                if (minNode.move >= 1 && neighbour.equals(minNode.prev.curboard));
                else {
                    gameTree.insert(new searchNode(neighbour, minNode));
                }
            }

            if (minNode.curboard.isGoal()){
                searchNode curNode = minNode;
                solution = new Board[minNode.move + 1];
                for (int i = minNode.move; i>=0; i--){
                    solution[i] = curNode.curboard;
                    curNode = curNode.prev;
                }

                if (solution[0].equals(initial)) solvable = true;
                break;
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable(){
        return solvable;
    }

    // min number of moves to solve initial board. -1 if unsolvable
    public int moves(){
        if (!solvable) return -1;
        else return solution.length - 1;
    }

    public class searchNode implements Comparable<searchNode>{
        private final Board curboard;
        private final int move;
        private final searchNode prev;
        private final int priority;
        private final int manhatten;

        searchNode(Board node){
            curboard = node;
            move = 0;
            prev = null;
            manhatten = curboard.manhattan();
            priority = move + manhatten;
        }

        searchNode(Board node, searchNode pre){
            curboard = node;
            move = pre.move + 1;
            prev = pre;
            manhatten = curboard.manhattan();
            priority = move + manhatten;
        }

        @Override
        public int compareTo(searchNode rhs){
            int sub = priority - rhs.priority;
            if (sub == 0) return manhatten - rhs.manhatten;
            return sub;
        }
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if (!solvable) return null;
        else return Arrays.asList(solution);
    }

    // test client
    public static void main(String[] args){
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
