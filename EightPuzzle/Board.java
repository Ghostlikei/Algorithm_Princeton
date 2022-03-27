package EightPuzzle;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Iterator;

public class Board {

    private int rank;
    public int[][] board;

    private void exch(int row_1, int col_1, int row_2, int col_2){
        int temp = board[row_1][col_1];
        board[row_1][col_1] = board[row_2][col_2];
        board[row_2][col_2] = temp;
    }

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        rank = tiles.length;
        board = new int[rank][rank];
        if (tiles.length < 2 || tiles.length >= 128) throw new IllegalArgumentException("The size of the board is illegal!");
        for(int i = 0; i < tiles.length; i++){
            for(int j = 0; j < tiles.length; j++){
                board[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of the board
    @Override
    public String toString(){
        StringBuilder  result = new StringBuilder();
        result.append(dimension()+"\n");
        for (int i = 0; i < rank; i++)
        {
            for (int j = 0; j < rank; j++)
            {
                result.append(String.format("%d ",board[i][j]));
            }
            result.append("\n");
        }
        return result.toString();

    }

    // board dimension n
    public int dimension(){
        return rank;
    }

    // number of tiles out of place
    public int hamming(){
        int cnt = 0;
        int flag = 1;
        for(int i = 0; i < rank; i++){
            for(int j = 0; j < rank; j++){
                if(board[i][j] != flag) cnt++;
                flag++;
                if(flag == rank*rank) return cnt;
            }
        }

        return cnt;

    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int distance = 0;
        int flag = 1;

        for(int i = 0; i < rank; i++){
            for(int j = 0; j < rank; j++){
                if (board[i][j] != 0){
                    int newadd = 0;
                    if (board[i][j] % rank == 0)
                    {
                        newadd = Math.abs(board[i][j] / rank -1 - i) + Math.abs((board[i][j] - 1) % rank - j);
                    }
                    else
                    {
                        newadd = Math.abs(board[i][j] / rank - i) + Math.abs((board[i][j] - 1) % rank - j);
                    }
                    distance += newadd;
                }

                flag++;
                if (flag == rank*rank) return distance;
            }
        }

        return distance;
    }

    // is the board the goal bound?
    public boolean isGoal(){
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Board y){
        if (rank != y.rank) return false;
        for(int i = 0; i < rank; i++){
            for(int j = 0; j < rank; j++){
                if(board[i][j] != y.board[i][j]) return false;
            }
        }

        return true;
    }
//    public boolean equals(Object y)
//    {
//        if (y == this) return true;
//        if (y == null) return false;
//        if (y.getClass() != this.getClass()) return false;
//        Board that = (Board) y;
//        if (that.dimension() != this.dimension()) return false;
//        if (this.toString().compareTo(that.toString()) == 0)
//        {
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }
    // all neighboring boards
    public Iterable<Board> neighbors(){
        return new neighborBoards();
    }

    public class neighborBoards implements Iterable<Board>{
        Board[] neighbours;
        int index;

        neighborBoards(){
            Board init = new Board(board);
            int row = 0;
            int col = 0;
            for(int i = 0; i < rank; i++){
                for(int j = 0; j < rank; j++){
                    if(board[i][j] == 0){
                        row = i;
                        col = j;

                    }
                }

            }
            neighbours = new Board[4];

            index = 0;
            if (row != 0){
                exch(row, col, row - 1, col);
                neighbours[index++] = new Board(board);

                exch(row, col, row - 1, col);

            }

            if (row != rank - 1){
                exch(row, col, row + 1, col);

                neighbours[index] = new Board(board);
                index++;
                exch(row, col, row + 1, col);
            }

            if (col != 0){
                exch(row, col - 1, row, col);
                neighbours[index] = new Board(board);
                index++;
                exch(row, col - 1, row, col);
            }

            if (col != rank - 1){
                exch(row, col + 1, row, col);
                neighbours[index] = new Board(board);
                index++;
                exch(row, col + 1, row, col);
            }
        }

        public Iterator<Board> iterator(){
            return new neighborIterator();
        }

        private class neighborIterator implements Iterator<Board>{
            int cur = 0;

            @Override
            public boolean hasNext(){
                return cur < index;
            }

            @Override
            public Board next(){
                return neighbours[cur++];
            }
        }
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int row = 0;
        int col = 0;
        if (board[row][col] == 0 || board[row + 1][col] == 0){
            col ++;
        }
        exch(row, col, row+1, col);
        Board result = new Board(board);
        exch(row, col, row+1, col);
        return result;
    }

    // unit testing (not graded)
    public static void main(String[] args){
        int[][] tiles = {{3,2,1},{4,0,6},{8,7,5}};
        Board testBoard = new Board(tiles);
        System.out.println(testBoard);

        for(Board neigh1 : testBoard.neighbors()){
            System.out.println(neigh1);
        }
        System.out.println(testBoard.dimension());
        System.out.println(testBoard.manhattan());
        System.out.println(testBoard.hamming());
        System.out.println(testBoard.isGoal());
        System.out.println(testBoard.equals(testBoard));
    }
}
