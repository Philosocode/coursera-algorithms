import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int numTrials;
    private double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials <= 0) {
            throw new IllegalArgumentException("Trials must be > 0");
        }

        thresholds = new double[trials];
        numTrials = trials;

        // execute `trials` trials
        for (int i = 0; i < numTrials; i++) {
            Percolation percolation = new Percolation(n);

            int numRounds = 0;
            while (!percolation.percolates()) {
                // choose a site uniformly at random among all blocked sites
                int randomRow = StdRandom.uniform(1, n+1);
                int randomCol = StdRandom.uniform(1, n+1);

                while (percolation.isOpen(randomRow, randomCol)) {
                    randomRow = StdRandom.uniform(1, n+1);
                    randomCol = StdRandom.uniform(1, n+1);
                }

                // open the site
                percolation.open(randomRow, randomCol);
                numRounds++;
            }

            double totalSites = n * n;
            double estimate = percolation.numberOfOpenSites() / totalSites;

            thresholds[i] = estimate;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(numTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(numTrials));
    }

    // test client (see below)
    public static void main(String[] args) {
        if (args.length == 2) {
            int n = Integer.parseInt(args[0]);
            int trials = Integer.parseInt(args[1]);

            PercolationStats stats = new PercolationStats(n, trials);

            StdOut.printf("mean                    = %f\n", stats.mean());
            StdOut.printf("stddev                  = %f\n", stats.stddev());
            StdOut.printf("95%% confidence interval = [%f, %f]\n", stats.confidenceLo(), stats.confidenceHi());
        }
    }
}