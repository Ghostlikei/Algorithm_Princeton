package Introduction;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Scanner;

public class HelloGoodbye {
    public static void main(String[] argv){


        StdOut.print("Hello, ");
        for(int i = 0; i < argv.length - 1; i++){
            StdOut.print(argv[i] + " and ");
        }

        StdOut.print(argv[argv.length - 1] + ".\n" + "Goodbye, ");

        for(int i = argv.length-1; i > 0; i--){
            StdOut.print(argv[i] + " and ");
        }
        StdOut.print(argv[0] + ".");
    }
}
