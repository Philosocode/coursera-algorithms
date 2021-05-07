import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        if (args.length == 0) return;

        RandomizedQueue<String> rq = new RandomizedQueue<>();

        int numItems = 0;

        try {
            numItems = Integer.parseInt(args[0]);
        } catch (NumberFormatException exception) {
            StdOut.println("First argument must be an integer");
        }

        while (!StdIn.isEmpty()) {
            rq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < numItems; i++) {
            StdOut.println(rq.dequeue());
        }
    }
}
