import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CS5 {
    private static int[] primes;

    private static void sieve(long limit) {
        boolean[] numIsPrime = new boolean[(int) limit - 1];

        for (int i = 0; i < limit - 1; i++) {
            numIsPrime[i] = true;
        }

        long numPrimes = limit - 1;

        for (int i = 2; i*i < limit; i++) {
            if (numIsPrime[i]) {
                for (int j = i * 2; j < limit - 1; j += i) {
                    numPrimes = !numIsPrime[j] ? numPrimes : numPrimes - 1;
                    numIsPrime[j] = false;
                }
            }
        }

        primes = new int[(int) numPrimes];
        long index = 0;
        for (int i = 0; i < limit - 1; i++) {
            if (numIsPrime[i]) {
                primes[(int) index] = i + 2;
                index++;
            }
        }
    }

    private static boolean isPrime(long num) {
        if (num == 1) {
            return false;
        }

        sieve((long) Math.sqrt(num));

        for (int i = 0; i < primes.length; i++) {
            if (num % primes[i] == 0) {
                return false;
            }
        }

        /*for (int i = 2; i < (num < 50 ? num : (int) Math.sqrt(num)); i++) {
            if (num % i == 0) {
                return false;
            }
        }*/

        return true;
    }

    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(new File("CS5EDataN.txt"));
        long[] primeAtLength = {7, 97, 997, 9973, 99929, 999983, 9999991, 99999989, 999999937,
                9999999929L, 99999999977L, 999999000001L, 9999999998987L, 99999999944441L,
                999998727899999L, 9999999900000001L, 99999988898898889L};
        long[] nonPrimeAtLength = {8, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999,
                9999999999L, 99999999999L, 999999999999L, 9999999999999L, 99999999999999L,
                999999999999999L, 9999999999999999L, 99999999999999999L};
        for (long i: nonPrimeAtLength) {
            long startTime = System.nanoTime();

            boolean prime = isPrime(i);

            long endTime = System.nanoTime();
            long netTime = endTime - startTime;

            out.printf("%b\t%.3f\n", prime, (double) netTime / 1000000);
        }
        out.close();
    }
}
