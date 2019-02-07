/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double IKURO = 1.96;
    private final int trials;
    private double[] opList;
    private double mean;
    private double stddev;

    public PercolationStats(int nn, int tri) {
        trials = tri;
        opList = new double[trials];
    }

    public double mean() {
        mean = StdStats.mean(opList);
        return mean;
    }

    public double stddev() {
        stddev = StdStats.stddev(opList);
        return stddev;
    }

    public double confidenceLo() {
        return mean - IKURO*Math.sqrt(stddev)/Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean + IKURO*Math.sqrt(stddev)/Math.sqrt(trials);
    }

    public static void main(String[] args) {
        int nn = Integer.parseInt(args[0]);
        int tri = Integer.parseInt(args[1]);
        PercolationStats perSta = new PercolationStats(nn, tri);

        for (int i = 0; i < tri; i++) {
            Percolation perco = new Percolation(nn);
            while (!perco.percolates()) {
                int row = StdRandom.uniform(1, nn+1);
                int col = StdRandom.uniform(1, nn+1);
                perco.open(row, col);
            }
            double sum = perco.numberOfOpenSites();
            perSta.opList[i] = sum/(nn*nn);
        }
        double mean = perSta.mean();
        double dev = perSta.stddev();
        double[] con = new double[2];
        con[0] = perSta.confidenceLo();
        con[1] = perSta.confidenceHi();
        System.out.println("mean                    = " + mean);
        System.out.println("stddev                  = " + dev);
        System.out.println("95% confidence interval = [" + con[0] + ", " + con[1] + "]");
    }
}

