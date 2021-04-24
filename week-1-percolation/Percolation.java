import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF sitesUF;
    private WeightedQuickUnionUF otherUF;
    private int numOpenSites;
    private boolean[] openSites;
    private int sideLength;
    private int gridSize;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be greater than 0");

        sideLength = n;
        gridSize = n*n;
        sitesUF = new WeightedQuickUnionUF(gridSize + 2);
        otherUF = new WeightedQuickUnionUF(gridSize + 1);

        openSites = new boolean[gridSize + 1];
        numOpenSites = 0;

        // initiate all sites to closed initially
        for (int i = 0; i < openSites.length; i++) {
            openSites[i] = false;
        }

        // connect top sites to top virtual site
        int topVirtualSite = 0;
        for (int i = 1; i <= sideLength; i++) {
            sitesUF.union(topVirtualSite, i);
            otherUF.union(topVirtualSite, i);
        }

        // connect bottom sites to bottom virtual site
        int bottomVirtualSite = gridSize + 1;
        for (int i = (gridSize - sideLength) + 1; i <= gridSize; i++) {
            sitesUF.union(bottomVirtualSite, i);
        }
    }

    // convert a coordinate to an index
    private int xyTo1D(int row, int col) {
        return ((row-1) * sideLength) + col;
    }

    // check if row and col are valid
    private boolean validIndices(int row, int col) {
        if (row < 1 || col < 1) {
            return false;
        }

        if (row > sideLength || col > sideLength) {
            return false;
        }

        return true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!validIndices(row, col)) {
            throw new IllegalArgumentException("Row or column out of bounds.");
        }

        if (isOpen(row, col)) {
            return;
        }

        // open the site
        int index = xyTo1D(row, col);
        openSites[index] = true;
        numOpenSites++;

        // connect to adjacent open sites
        // north
        if (row > 1) {
            if (isOpen(row-1, col)) {
                int northIndex = xyTo1D(row-1, col);
                sitesUF.union(index, northIndex);
                otherUF.union(index, northIndex);
            }
        }

        // south
        if (row < sideLength) {
            if (isOpen(row+1, col)) {
                int southIndex = xyTo1D(row+1, col);
                sitesUF.union(index, southIndex);
                otherUF.union(index, southIndex);
            }
        }

        // west
        if (col > 1) {
            if (isOpen(row, col-1)) {
                int westIndex = xyTo1D(row, col-1);
                sitesUF.union(index, westIndex);
                otherUF.union(index, westIndex);
            }
        }

        // east
        if (col < sideLength) {
            if (isOpen(row, col+1)) {
                int eastIndex = xyTo1D(row, col+1);
                sitesUF.union(index, eastIndex);
                otherUF.union(index, eastIndex);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!validIndices(row, col)) {
            throw new IllegalArgumentException("Row or column indices are invalid");
        }

        int index = xyTo1D(row, col);

        return openSites[index] == true;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!validIndices(row, col)) {
            throw new IllegalArgumentException("Row or column indices are invalid");
        }

        int index = xyTo1D(row, col);
        return isOpen(row, col) && (otherUF.find(0) == otherUF.find(index));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (numOpenSites == 0) return false;

        int topRoot = sitesUF.find(0);
        int bottomRoot = sitesUF.find(gridSize+1);

        return topRoot == bottomRoot;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(6);
        p.sitesUF.union(1,6);
        p.sitesUF.union(2,6);
        p.sitesUF.union(3,6);
        p.sitesUF.union(4,6);
        p.sitesUF.union(5,6);
        p.sitesUF.union(5,5);
    }
}