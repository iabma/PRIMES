import java.io.FileNotFoundException;
import java.io.PrintWriter;
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
            out.println();
        }

        out.close();
    }
}
