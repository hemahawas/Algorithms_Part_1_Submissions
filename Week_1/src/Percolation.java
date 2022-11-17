/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    private final int[] id;
    private int openedSites = 0;
    private final WeightedQuickUnionUF uf;
    private static final int virtualTop = 0;
    private final int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.uf = new WeightedQuickUnionUF((n*n)+ 2);
        this.n = n;
        this.id = new int[(n*n)+ 1];
        this.virtualBottom = n*n + 1;

        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive value");
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                this.id[indexOf(i, j)]= -1;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("n must be positive value");
        }

        if (this.id[indexOf(row, col)] == -1) {
            this.id[indexOf(row, col)] = 0;
            this.openedSites++;
        }
        if (row == 1){
            this.uf.union(virtualTop, indexOf(row, col));
        }
        if (row == this.n) {
            this.uf.union(this.virtualBottom, indexOf(row, col));
        }

        union(row, col);
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("index must be positive value");
        }
        return id[indexOf(row, col)] != -1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("index must be positive value");
        }
        if (isOpen(row, col)){
            return this.uf.find(indexOf(row, col)) == this.uf.find(virtualTop);
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return this.openedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return this.uf.find(virtualTop) == this.uf.find(this.virtualBottom);
    }

    private void union(int row, int col) {
        if (indexOf(row, col) - n > 0){
            if(isOpen(row-1, col)){
                this.uf.union(indexOf(row, col), indexOf(row-1, col));
            }
        }
        if(indexOf(row, col) + n <= n*n){
            if(isOpen(row+1, col)){
                this.uf.union(indexOf(row, col), indexOf(row+1, col));
            }
        }
        if((indexOf(row, col)-1) % n != 0){
            if(isOpen(row, col-1)){
                this.uf.union(indexOf(row, col), indexOf(row, col-1));
            }
        }
        if(indexOf(row, col) % n != 0){
            if(isOpen(row, col+1)){
                this.uf.union(indexOf(row, col), indexOf(row, col+1));
            }
        }
    }


    // Translate from Coordinate indices to array indices
    private int indexOf(int row, int col) {
        return ((row-1) * this.n) + col;
    }

    // test client (optional)
    public static void main(String[] args) {
        // Unit Test for each function
        // Instance
        int n = 1;
        Percolation percolation = new Percolation(n);
         //Test open
       // System.out.println("#########open() Testing#########");

//        System.out.println("False; Before opening index(1,1): " + percolation.isOpen(1,1));
//        //assert (!percolation.isOpen(1,1)) : "isOpen should've returned false, but it returned true";
//        percolation.open(1,1);
//        System.out.println("True; After opening index(1,1): " + percolation.isOpen(1,1));
//
//        System.out.println();
        System.out.println("#########union() Testing#########");

        percolation.open(1, 1);
        System.out.println("True; index(1,1) after opening isFull: " + percolation.isFull(1, 1));

//        System.out.println("False; index(3,2) before opening isFull: " + percolation.isFull(3,2));
//        percolation.open(3, 2);
//        System.out.println("True; index (3,2) after opening isFull: " + percolation.isFull(3,2));
//
//        System.out.println();
//        System.out.println("True; index (2,2) connected to bottom? "
//                                   + (percolation.uf.find(percolation.indexOf(2,2)) ==
//                percolation.uf.find(percolation.virtualBottom)));
//
//
        System.out.println();
         //Test percolates()
        System.out.println("#########percolates() Testing#########");
        System.out.println("True; percolation.percolates(): " + percolation.percolates());

        // Entire Percolation Test
        /*//Instance
        int n = 4;
        Percolation percolation = new Percolation(n);

        percolation.testCreator(0, 0, 1, 1);
        percolation.testCreator(1, 0);
        percolation.testCreator(2, 0);
        percolation.testCreator(3, 0);
        System.out.println("VirtualBottom & (3,0) connected? " +
                                   percolation.uf.connected(percolation.virtualBottom,
                                                            percolation.indexOf(3, 0)));

            */

    }
}
