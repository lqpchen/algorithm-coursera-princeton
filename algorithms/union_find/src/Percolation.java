
/*
This is an union-find application with the details described in the following link:
https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php
It makes use of the UnionFind class.
 */

public class Percolation {
    private final int n; // nrow, ncol
    private int cntOpen; // number of open nodes
    private boolean [] grid; // grid[i] == 1 if node i is open
    private final UnionFind uf;
    // excluding the virtual bottom node for backwash error
    private final UnionFind ufTop;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int num) {
        if (num <= 0) {
            throw new IllegalArgumentException("n must be greater than 0!");
        }

        n = num;
        cntOpen = 2; // the virtual top and bottom nodes are always open

        grid = new boolean[n*n + 2]; // virtual top and bottom node
        for (int i = 1; i < n*n + 1; ++i) {
            grid[i] = false;
        }
        grid[0] = true; // virtual top node is open
        grid[n*n+1] = true; // virtual bottom node is open

        uf = new UnionFind(n*n + 2);
        ufTop = new UnionFind(n*n + 1);
    }

    private int idx(int row, int col) {
        // note: can not explicitly access the virtual nodes
        return (row - 1)*n + col;
    }

    private void checkIdx(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException("row and col must be within [1, n]");
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        checkIdx(row, col);

        if (isOpen(row, col)) {
            return;
        } else {
            ++cntOpen;
            grid[idx(row, col)] = true;
        }

        // connect left-ward
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(idx(row, col), idx(row, col - 1));
            ufTop.union(idx(row, col), idx(row, col - 1));
        }
        // connect right-ward
        if (col < n && isOpen(row, col + 1)) {
            uf.union(idx(row, col), idx(row, col + 1));
            ufTop.union(idx(row, col), idx(row, col + 1));
        }
        // connect up-ward
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(idx(row - 1, col), idx(row, col));
            ufTop.union(idx(row - 1, col), idx(row, col));
        }
        // connect down-ward
        if (row < n && isOpen(row + 1, col)) {
            uf.union(idx(row + 1, col), idx(row, col));
            ufTop.union(idx(row + 1, col), idx(row, col));
        }
        // connect to the virtual top node
        if (row == 1) {
            uf.union(idx(row, col), 0);
            ufTop.union(idx(row, col), 0);
        }
        // connect to the virtual bottom node
        if (row == n) {
            uf.union(idx(row, col), n*n + 1);
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIdx(row, col);

        return grid[idx(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIdx(row, col);

        // note that we are not using uf here to avoid backwash error
        return ufTop.find(idx(row, col)) == ufTop.find(0);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return cntOpen - 2; // exclude virtual nodes at the top and bottom
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(0) == uf.find(n*n + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // nothing
    }
}

