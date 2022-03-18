package Introduction;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] argv){
        StdOut.println(argv[StdRandom.uniform(argv.length)]);
    }
}
