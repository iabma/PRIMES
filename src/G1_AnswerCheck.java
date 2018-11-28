import java.util.Random;
import java.util.Scanner;

/* MATH PROBLEM 1 PROCEDURAL ANSWER
   Java 1.8
   IntelliJ IDEA CE
   MacOS

   HOW TO RUN: If using a console, compile the file by entering "javac G1_AnswerCheck.java" while in
   the correct directory, then entering "java G1_AnswerCheck". The program will prompt you for
   input and output file names.

   REFERENCES:
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
 */
public class G1_AnswerCheck {
    private static final int NUM_ITERATIONS = 10000000;

    /*
    Given an inputted number of pairs of consecutive digits, the program runs 10 million times in
    order to get as accurate of an answer as possible for the probability.
     */
    public static void main(String[] args) {
        Random gen = new Random();
        Scanner in = new Scanner(System.in);
        int numSuccesses = 0;

        System.out.print("Number of pairs: ");
        int pairAmt = in.nextInt();

        for (int i = 0; i < NUM_ITERATIONS; i++) {
            int[] set = new int[10];
            boolean amtReached = false;
            int numPairs = 0;
            for (int j = 0; j < 10; j++) {
                int val = gen.nextInt(2);
                set[j] = val;
                if (j > 0 && set[j] == set[j - 1]) numPairs++;
                amtReached = numPairs == pairAmt;
            }
            if (amtReached) numSuccesses++;
        }

        System.out.printf("~%.3f%%", numSuccesses / (double) NUM_ITERATIONS * 100.0);
    }
}
