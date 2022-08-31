/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    /*
    TODO:
    1. Restructure project files
    2. upload in github
     */
    // TODO: Revise your entire code because I have seen a lot of bugs that i didn't fix and left them fore you
    private int n;
    private int[][] grid;
    private int openedSites = 0;
    WeightedQuickUnionUF uf;
    private final int virtualTop; // TODO: check from the HW what they should be
    private final int virtualBottom;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        uf = new WeightedQuickUnionUF(n);
        this.n = n;
        grid = new int[n + 1][n + 1];

        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive value");
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                grid[i][j] = -1;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("n must be positive value");
        }

        if (grid[row][col] == -1) {
            grid[row][col] = 0;
            openedSites++;
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("index must be positive value");
        }
        if (grid[row][col] != -1)
            return true;
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || col <= 0) {
            throw new IllegalArgumentException("index must be positive value");
        }
        if (grid[row][col] == 1)
            return true;
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openedSites;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int j = 1; j <= n; j++) {
            if (isFull(n, j)) {
                return true;
            }
        }
        return false;
    }

    void union(int row, int col) {
        int up = row - 1 == 0 ? 1 : row - 1;
        int down = row + 1 == n + 1 ? n : row + 1;
        int left = col - 1 == 0 ? 1 : col - 1;
        int right = col + 1 == n + 1 ? n : col + 1;

        if (isFull(up, col) || isFull(down, col) || isFull(row, left) || isFull(row, right)) {
            grid[row][col] = 1;
            if (isOpen(up, col) && !isFull(up, col)) {
                union(up, col);
            }
            if (isOpen(down, col) && !isFull(down, col)) {
                union(down, col);
            }
            if (isOpen(row, left) && !isFull(row, left)) {
                union(row, left);
            }
            if (isOpen(row, right) && !isFull(row, right)) {
                union(row, right);
            }

        }
    }


    // Translate from Coordinate indices to array indices
    // TODO: Implement function indexOf

    // test client (optional)
    public static void main(String[] args) {
        // Unit Test for each function
        // Instance
        int n = 2;
        Percolation percolation = new Percolation(n);
        // TODO: Verify your code running using each provided test below
        // Test open
        System.out.println("#########open() Testing#########");

        System.out.println("False; Before opening index(0,0): " + percolation.isOpen(0, 0));
        assert (!percolation.isOpen(0, 0)) : "isOpen should've returned false but it returnde true";
        percolation.open(0, 0);
        System.out.println("True; After opening index(0,0): " + percolation.isOpen(0, 0));

        System.out.println();

        /* TODO: to open functions as soon as The above code runs successfully
        // Test union (within open)
        System.out.println("#########union() Testing#########");

        percolation.open(0, 1);
        System.out.println("True; index(0,1) after opening isFull: " + percolation.isFull(0, 1));

        System.out.println("False; index(1,0) before opening isFull: " + percolation.isFull(1, 1));
        percolation.open(1, 0);
        System.out.println("True; index (1,0) after opening isFull: " + percolation.isFull(1, 0));

        System.out.println();
        System.out.println("True; index (1,0) connected to bottom? "
                                   + (percolation.uf.find(percolation.indexOf(1, 0)) ==
                percolation.uf.find(percolation.virtualBottom)));
        System.out.println("True; index (1,0) connected to bottom? "
                                   + percolation.uf.connected(percolation.indexOf(1, 0),
                                                              percolation.virtualBottom));
        System.out.println();

        // Test percolates()
        System.out.println("#########percolates() Testing#########");
        System.out.println("True; percolation.percolates(): " + percolation.percolates());
        percolation.open(1, 1);
        */
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



        /* Hema's Code
        int n = 10;
        Percolation percolation = new Percolation(n);
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, n + 1);
            int col = StdRandom.uniform(1, n + 1);

            percolation.open(row, col);

            if (row == 1)
                percolation.grid[row][col] = 1;

            percolation.union(row, col);
        }
    */
    }
}
