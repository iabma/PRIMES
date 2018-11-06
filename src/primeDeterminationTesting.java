import java.io.File;
import java.io.PrintWriter;

public class primeDeterminationTesting {
    private static int[] primes;
    private static long numPrimes;

    private static void sieve(long limit) {
        boolean[] numIsPrime = new boolean[(int) limit + 2];

        for (int i = 2; i < limit - 1; i++) {
            numIsPrime[i] = true;
        }

        numPrimes = limit - 3;
        long index = 0;
        primes = new int[(int) numPrimes];

        for (int i = 2; i < limit + 1; i++) {
            if (numIsPrime[i]) {
                for (int j = i * 2; j < limit + 1; j += i) {
                    numPrimes = !numIsPrime[j] ? numPrimes : numPrimes - 1;
                    numIsPrime[j] = false;
                }
                primes[(int) index] = i;
                index++;
            }
        }
    }

    private static boolean isPrimeWithSieve(long num) {
        if (num == 1) {
            return false;
        }

        for (int i = 0; i < numPrimes; i++) {
            if (num > primes[i] && num % primes[i] == 0) {
                return false;
            } else if (num < primes[i]) {
                return true;
            }
        }

        return true;
    }

    private static boolean isPrimeWithoutSieve(long num) {
        if (num == 1) {
            return false;
        }

        for (int i = 2; i <= (long) Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) throws Exception {
        PrintWriter out = new PrintWriter(new File("CS5SmallPrimes.txt"));
        long[] primeInputs = {77777777977777777L, 91521253335394951L, 74747474747474747L,
                97654321012345679L, 98765432101456789L, 98823199699242779L, 99194853094755497L,
                99999988898898889L};
        long[] compInputs = {573723274837192737L, 573849275437192737L, 5738492748371927387L,
                573849274837192737L, 573849274837162337L, 5725492748325436737L,
                573849274837145327L, 73845436437192737L};
        long[] smallPrimeInputs = {991, 983, 977, 101, 971, 967, 953, 997};
        long[] smallCompInputs = {990, 982, 976, 100, 970, 966, 952, 996};

        long totalTime = 0;

        long startSieve = System.nanoTime();

        sieve((long) Math.sqrt(smallPrimeInputs[smallPrimeInputs.length - 1]));

        long endSieve = System.nanoTime();
        long netSieve = endSieve - startSieve;
        totalTime += netSieve;

        out.printf("Sieve: %.3f\n",(double) netSieve / 1000000);

        System.out.println("Sieve");

        for (long i: smallPrimeInputs) {
            long startTime = System.nanoTime();

            boolean prime = isPrimeWithSieve(i);

            long endTime = System.nanoTime();
            long netTime = endTime - startTime;
            totalTime += netTime;

            out.printf("%b\t%.3f\n", prime, (double) netTime / 1000000);
        }

        out.printf("Total: %.5f seconds", (double) totalTime / 1000000000);
        out.close();
    }
}
