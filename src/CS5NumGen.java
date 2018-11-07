import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class CS5NumGen {
    private static long Long() {
        return ThreadLocalRandom.current().nextLong(1, 100000000000000000L);
    }

    private static long findPrime() {
        while (true) {
            long num = Long();
            if (isPrime(num)) {
                return num;
            }
        }
    }

    private static long findComposite() {
        while (true) {
            long num = Long();
            if (!isPrime(num)) {
                return num;
            }
        }
    }

    private static boolean isPrime(long num) {
        BigInteger numToCheck = BigInteger.valueOf(num);
        return numToCheck.isProbablePrime(1);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter("NumSets.txt");

        System.out.print("Number of sets: ");
        long numSets = Long.parseLong(in.next());
        out.println(numSets);

        in.close();

        for (int i = 0; i < numSets; i++) {
            int primeIndex = ThreadLocalRandom.current().nextInt(5);

            for (int j = 0; j < 5; j++) {
                if (j == primeIndex) {
                    out.print(findPrime() + " ");
                } else {
                    out.print(findComposite() + " ");
                }
            }
            System.out.printf("%.2f%% done.\n", (double) (i + 1) / numSets * 100);
            out.println();
        }

        out.close();
    }
}
