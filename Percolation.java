import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] cells;
    private final int n;
    private final WeightedQuickUnionUF uf;

    public Percolation(int nn) {
        n = nn;
        cells = new int[n*n];
        for (int i = 0; i < n*n; i++) {
            cells[i] = 0;
        }
        uf = new WeightedQuickUnionUF(n*n+2);
    }

    public void open(int row, int col) {
        cells[n*(row-1) + (col-1)] = 1;
        if (n == 1) {
            uf.union(0, 1);
            uf.union(1, 2);
        }
        else {
            if (row == 1) {
                uf.union(n * (row - 1) + col, 0);
                if (col == 1) {
                    if (isOpen(1, 2)) uf.union(1, 2);
                    if (isOpen(2, 1)) uf.union(1, n + 1);
                }
                else {
                    if (col == n) {
                        if (isOpen(1, n - 1)) uf.union(n - 1, n);
                        if (isOpen(2, n)) uf.union(n, 2 * n);
                    }
                    else {
                        if (isOpen(1, col - 1))
                            uf.union(n * (row - 1) + col, n * (row - 1) + col - 1);
                        if (isOpen(1, col + 1))
                            uf.union(n * (row - 1) + col, n * (row - 1) + col + 1);
                        if (isOpen(2, col)) uf.union(n * (row - 1) + col, n * row + col);
                    }
                }
            }
            else {
                if (row == n) {
                    if (col == 1) {
                        if (isOpen(n, 2)) uf.union(n * (row - 1) + 1, n * (row - 1) + 2);
                        if (isOpen(n - 1, 1)) uf.union(n * (row - 1) + 1, n * (row - 2) + 1);
                    }
                    else {
                        if (col == n) {
                            if (isOpen(n, n - 1)) uf.union(n * n, n * n - 1);
                            if (isOpen(n - 1, n)) uf.union(n * n, n * (n - 1));
                        }
                        else {
                            if (isOpen(n, col - 1))
                                uf.union(n * (row - 1) + col, n * (row - 1) + col - 1);
                            if (isOpen(n, col + 1))
                                uf.union(n * (row - 1) + col, n * (row - 1) + col + 1);
                            if (isOpen(n - 1, col))
                                uf.union(n * (row - 1) + col, n * (n - 2) + col);
                        }
                    }
                }
                else {
                    if (col == 1) {
                        if (isOpen(row - 1, 1)) uf.union(n * (row - 1) + col, n * (row - 2) + col);
                        if (isOpen(row + 1, 1)) uf.union(n * (row - 1) + col, n * row + col);
                        if (isOpen(row, 2)) uf.union(n * (row - 1) + col, n * (row - 1) + col + 1);
                    }
                    else {
                        if (col == n) {
                            if (isOpen(row - 1, n))
                                uf.union(n * (row - 1) + col, n * (row - 2) + col);
                            if (isOpen(row + 1, n)) uf.union(n * (row - 1) + col, n * row + col);
                            if (isOpen(row, n - 1))
                                uf.union(n * (row - 1) + col, n * (row - 1) + col - 1);
                        }
                        else {
                            if (isOpen(row - 1, col))
                                uf.union(n * (row - 1) + col, n * (row - 2) + col);
                            if (isOpen(row + 1, col)) uf.union(n * (row - 1) + col, n * row + col);
                            if (isOpen(row, col - 1))
                                uf.union(n * (row - 1) + col, n * (row - 1) + col - 1);
                            if (isOpen(row, col + 1))
                                uf.union(n * (row - 1) + col, n * (row - 1) + col + 1);
                        }
                    }
                }
            }
            if (isFull(n, col) == true) {
                uf.union(n*(n-1) + col, n*n+1);
            }    
        }
    }

    public boolean isOpen(int row, int col) {
        return cells[n*(row-1) + (col-1)] == 1;
    }

    public boolean isFull(int row, int col) {
        return uf.connected(0, n*(row-1) + col);
    }

    public int numberOfOpenSites() {
        int sum = 0;
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == 1) { sum++; }
        }
        return sum;
    }

    public boolean percolates() {
        return uf.connected(0, n*n+1);
    }

    public static void main(String[] args) {
        // empty
    }
}
