import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/* PROBLEM 5 TEST FILE GENERATOR
   Java 1.8
   IntelliJ IDEA CE
   MacOS

   HOW TO RUN: If using a console, compile the file by entering "javac CS5_NumGen.java" while in
   the correct directory, then entering "java CS5_NumGen".

   REFERENCES:
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
 */
public class CS5_NumGen {
    /*
    Returns a randomly generate long given an upper bound.
     */
    private static long Long(long bound) {
        return ThreadLocalRandom.current().nextLong(1, bound);
    }

    /*
    Repeatedly produces a long with the given bound until it finds one which is prime, then
    returns it.
     */
    private static long findPrime(long bound) {
        while (true) {
            long num = Long(bound);
            if (isPrime(num)) {
                return num;
            }
        }
    }

    /*
    Repeatedly produces a long with the given bound until it finds one which is composite, then
    returns it.
     */
    private static long findComposite(long bound) {
        while (true) {
            long num = Long(bound);
            if (!isPrime(num)) {
                return num;
            }
        }
    }

    /*
    A slow yet very simple deterministic primality test.
     */
    private static boolean isPrime(long num) {
        if (num == 1) {
            return false;
        }

        for (int i = 2; i <= (int) Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    /*
    Prompts the user for the desired number of number sets, then randomly generates them. In
    order to ensure diversity and large prime numbers, an order of magnitude from 1 to 17 is
    selected, then the number is generated within or less than that order of magnitude.
     */
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter("CS5_TestData.txt");

        System.out.print("Number of sets: ");
        long numSets = Long.parseLong(in.next());
        out.println(numSets);

        in.close();

        for (int i = 0; i < numSets; i++) {
            int primeIndex = ThreadLocalRandom.current().nextInt(5);

            for (int j = 0; j < 5; j++) {
                int orderOfMagnitude = ThreadLocalRandom.current().nextInt(1, 18);
                if (j == primeIndex) {
                    out.print(findPrime((long) Math.pow(10, orderOfMagnitude)) + " ");
                } else {
                    out.print(findComposite((long) Math.pow(10, orderOfMagnitude)) + " ");
                }
            }
            System.out.printf("%.2f%% done.\n", (double) (i + 1) / numSets * 100);
            out.println();
        }

        out.close();
    }
}
