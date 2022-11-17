import java.util.LinkedList;
import java.util.Objects;

public class Board {

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    final private int[][] tiles;
    public Board(int[][] tiles){
        this.tiles = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            System.arraycopy(tiles[i], 0, this.tiles[i], 0, tiles.length);
        }
    }

    // string representation of this board
    public String toString(){
        String s = this.tiles.length+ "\n";
        for (int[] tile : this.tiles) {
            for (int j = 0; j < this.tiles.length; j++) {
                s = s.concat(" " + tile[j] + " ");
            }
            s = s.concat("\n");
        }
        return s;
    }

    // board dimension n
    public int dimension(){
        return this.tiles.length;
    }

    // number of tiles out of place
    public int hamming(){
        int counter = 0;
        for (int i = 0; i < this.tiles.length; i++){
            for (int j = 0; j < this.tiles.length; j++) {
                if(tiles[i][j] != indexOf(i,j) && tiles[i][j] != 0){
                    counter++;
                }
            }
        }
        return counter;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int manhattan = 0;
        for (int i = 0; i < this.tiles.length; i++){
            for (int j = 0; j < this.tiles.length; j++) {
                if(tiles[i][j] != indexOf(i,j) && tiles[i][j] != 0){
                    manhattan += Math.abs(rowOf(tiles[i][j]) -i) + Math.abs(colOf(tiles[i][j]) -j);
//                    System.out.println(tiles[i][j] + ": " + manhattan );
                }
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return hamming() == 0;
    }


    // does this board equal y?
    public boolean equals(Object y){
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        return Objects.equals(y.toString(), this.toString());
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
        LinkedList<int[][]> list = new LinkedList<>();
        LinkedList<Board> boards = new LinkedList<>();

        int tmp;
        int iIndex = 0;
        int jIndex = 0;
        for (int i = 0; i < this.tiles.length; i++) {
            for (int j = 0; j < this.tiles.length; j++) {
                if(this.tiles[i][j] == 0){
                    iIndex = i;
                    jIndex = j;
                    break;
                }
            }
        }

        if(iIndex-1 >= 0){
            list.addLast(new int[tiles.length][tiles.length]);
            for (int i = 0; i < this.tiles.length; i++) {
                for (int j = 0; j < this.tiles.length; j++) {
                    list.getLast()[i][j] = tiles[i][j];
                }
            }
            tmp = list.getLast()[iIndex][jIndex];
            list.getLast()[iIndex][jIndex] = list.getLast()[iIndex-1][jIndex];
            list.getLast()[iIndex-1][jIndex] = tmp;
            boards.addLast(new Board(list.removeLast()));
        }
        if(iIndex+1 < this.tiles.length){
            list.addLast(new int[tiles.length][tiles.length]);
            for (int i = 0; i < this.tiles.length; i++) {
                for (int j = 0; j < this.tiles.length; j++) {
                    list.getLast()[i][j] = tiles[i][j];
                }
            }
            tmp = list.getLast()[iIndex][jIndex];
            list.getLast()[iIndex][jIndex] = list.getLast()[iIndex+1][jIndex];
            list.getLast()[iIndex+1][jIndex] = tmp;
            boards.addLast(new Board(list.removeLast()));
        }
        if(jIndex-1 >= 0){
            list.addLast(new int[tiles.length][tiles.length]);
            for (int i = 0; i < this.tiles.length; i++) {
                for (int j = 0; j < this.tiles.length; j++) {
                    list.getLast()[i][j] = tiles[i][j];
                }
            }
            tmp = list.getLast()[iIndex][jIndex];
            list.getLast()[iIndex][jIndex] = list.getLast()[iIndex][jIndex-1];
            list.getLast()[iIndex][jIndex-1] = tmp;
            boards.addLast(new Board(list.removeLast()));
        }
        if(jIndex+1 < this.tiles.length){
            list.addLast(new int[tiles.length][tiles.length]);
            for (int i = 0; i < this.tiles.length; i++) {
                for (int j = 0; j < this.tiles.length; j++) {
                    list.getLast()[i][j] = tiles[i][j];
                }
            }
            tmp = list.getLast()[iIndex][jIndex];
            list.getLast()[iIndex][jIndex] = list.getLast()[iIndex][jIndex+1];
            list.getLast()[iIndex][jIndex+1] = tmp;
            boards.addLast(new Board(list.removeLast()));
        }

        return boards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        Board board = new Board(this.tiles);
        int s = 0;
        int i1 = 0;
        int j1 = 0;
        int i2 = 0;
        int j2 = 0;
        for (int i = 0; i < this.dimension(); i++) {
            for (int j = 0; j < this.dimension(); j++) {
                if(tiles[i][j] != 0){
                    i1 = i;
                    j1 = j;
                    s++;
                }
                if(s == 1 && tiles[i][j] != 0){
                    i2 = i;
                    j2 = j;
                    s++;
                }
                if(s == 2) break;
            }
        }
        swap(board.tiles, i1, j1, i2, j2);


        return board;
    }

    private void swap(int[][] tiles, int a1, int a2, int b1, int b2){
        int temp = tiles[a1][a2];
        tiles[a1][a2] = tiles[b1][b2];
        tiles[b1][b2] = temp;
    }

    private int indexOf(int row, int col){
        if(row == this.dimension() && col == this.dimension()) return 0;
        return this.dimension() * row + col +1;
    }

    private int rowOf(int x){
        if(x % this.dimension() == 0) return (x/this.dimension()) - 1;
        return (x / this.dimension());
    }

    private int colOf(int x){
        if(x % this.dimension() == 0) return this.dimension()-1;
        return (x % this.dimension()) - 1;
    }

    // unit testing (not graded)
    public static void main(String[] args){
        int[][] tiles = new int[][]{{0,1,3},{4,2,5},{7,8,6}};
        Board board = new Board(tiles);
        System.out.println(board);
        System.out.println(board.twin());


//        System.out.println(board.twin());
    }
}