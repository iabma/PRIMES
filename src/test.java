/**
 ** Java Program to Implement Miller Rabin Primality Test Algorithm
 **/

import java.util.Scanner;
import java.util.Random;
import java.math.BigInteger;
import java.util.concurrent.ThreadLocalRandom;

/** Class MillerRabin **/
public class test
{
    private static BigInteger num;
    private static final BigInteger ONE = new BigInteger("1");
    private static final BigInteger TWO = new BigInteger("2");

    private static boolean passesMillerRabin(int iterations, Random rnd) {
        // Find a and m such that m is odd and num == 1 + 2**a * m
        BigInteger numMinusOne = num.subtract(ONE);
        BigInteger m = numMinusOne;
        int a = m.getLowestSetBit();
        m = m.shiftRight(a);

        // Do the tests
        if (rnd == null) {
            rnd = ThreadLocalRandom.current();
        }
        for (int i=0; i < iterations; i++) {
            // Generate a uniform random on (1, num)
            BigInteger b;
            do {
                b = new BigInteger(num.bitLength(), rnd);
            } while (b.compareTo(ONE) <= 0 || b.compareTo(num) >= 0);

            int j = 0;
            BigInteger z = b.modPow(m, num);
            while (!((j == 0 && z.equals(ONE)) || z.equals(numMinusOne))) {
                if (j > 0 && z.equals(ONE) || ++j == a)
                    return false;
                z = z.modPow(TWO, num);
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Random rand = new Random();
        num = new BigInteger("99999988898898889");
        boolean bool = false;

        long startTime = System.nanoTime();
        bool = num.isProbablePrime(1);
        long endTime = System.nanoTime();
        long netTime = endTime - startTime;
        System.out.println(bool);
        System.out.println(netTime / 1000000.0);
    }
}