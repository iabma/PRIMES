import java.io.File;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Scanner;

/* PROBLEM 5
   Java 1.8
   IntelliJ IDEA CE
   MacOS

   HOW TO RUN: If using a console, compile the file by entering "javac CS1.java" while in the
   correct directory, then entering "java CS1". The program will prompt you for input and output
   file names.

   Test data file: CS5_TestData.txt
   Test data generator: CS5_NumGen.java

   REFERENCES:
   Counting Sort: https://www.geeksforgeeks.org/counting-sort/
   Heap Sort: https://www.geeksforgeeks.org/heap-sort/
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
 */
public class CS5 {
    private static long[][] data;
    private static long[][] tempData;
    private static long[] primes;
    private static long[] newPrimes;
    private static int[] setsAtIndex;
    private static int[] indexes;
    private static int numSets;

    /*
    Reads the desired data file and output file, then reads the data. The timer is started, and
    the necessary pre-calculations are made. Then, the three sorting steps are executed. The timer
    is stopped, and the ordered set is written to the desired output file.
     */
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        File input;
        File output;

        do {
            System.out.print("Input file name: ");
            input = new File(in.next());
        } while (!input.exists());

        System.out.print("Output file name: ");
        output = new File(in.next());

        in.close();

        Scanner inputFile = new Scanner(input);
        read(inputFile);
        inputFile.close();

        long startTime = System.nanoTime();

        index();

        sortByIndex();

        sortByPrimes();

        sortByOther();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write(output, netTime);
    }

    /*
    If the number to check is 4 digits, it is referred to smallNumPrimality(). Otherwise, it is
    converted to a BigInteger so the BPSW probabilistic primality test built into the BigInteger
    Java library can be utilized.
     */
    private static boolean isPrime(long num) {
        if (num < 1000000) return smallNumPrimality(num);
        BigInteger numToCheck = BigInteger.valueOf(num);
        return numToCheck.isProbablePrime(3);
    }

    /*
    Simply iterates through every number from 2 to the square root of the number (since any
    factors above the square root would be factor pairs of numbers already tested) and checks if
    it is a factor.
     */
    private static boolean smallNumPrimality(long num) {
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
    Used by the heapSort() method in two ways: it can either compare the size of two prime
    numbers, or it can compare two number sets by iterating through their composite numbers until
    a difference is discovered.
     */
    private static boolean isGreater(int one, int two, boolean single) {
        if (single) {
            return newPrimes[one] > newPrimes[two];
        } else {
            for (int i = 0; i < 5; i++) {
                if (data[one][i] > data[two][i]) return true;
                else if (data[one][i] < data[two][i]) return false;
            }
            return false;
        }
    }

    /*
    Reads the given number file and creates an array using the input.
     */
    private static void read(Scanner input) {
        numSets = input.nextInt();
        primes = new long[numSets];
        newPrimes = new long[numSets];
        data = new long[numSets][5];
        tempData = new long[numSets][5];
        indexes = new int[numSets];
        setsAtIndex = new int[5];

        for (int i = 0; i < numSets; i++) {
            for (int j = 0; j < 5; j++) {
                tempData[i][j] = input.nextLong();
            }
        }
    }

    /*
    Iterates through the data array, determining the index and the value of the prime number by
    using the previously explained isPrime() method. Given that BPSW is probabilistic, a backup
    system is in place with a deterministic, but slower, primality test.
     */
    private static void index() {
        int index = 0;

        for (int i = 0; i < numSets; i++) {
            boolean primeFound = false;
            for (int j = 0; j < 5; j++) {
                if (isPrime(tempData[i][j])) {
                    if (primeFound) {
                        primeFound = false;
                        break;
                    }
                    index = j;
                    primeFound = true;
                }
            }
            if (!primeFound) {
                for (int j = 0; j < 5; j++) {
                    if (smallNumPrimality(tempData[i][j])) {
                        index = j;
                    }
                }
            }

            setsAtIndex[index]++;
            indexes[i] = index;
            primes[i] = tempData[i][index];
            newPrimes[i] = tempData[i][index];
        }
    }

    /*
    A conventional heap sort method. Uses the prime number array and the data array.
     */
    private static void heapSort(boolean single, int length, int startIndex) {
        long[] input = newPrimes;

        for (int i = length / 2 - 1; i >= 0; i--)
            heap(input, length, i, startIndex, single);

        for (int i = length - 1; i >= 0; i--) {
            if (single) {
                long tempPrimes = input[startIndex];
                input[startIndex] = input[startIndex + i];
                input[startIndex + i] = tempPrimes;
            }

            long[] tempData = data[startIndex];
            data[startIndex] = data[startIndex + i];
            data[startIndex + i] = tempData;

            heap(input, i, 0, startIndex, single);
        }
    }

    /*
    A conventional complementary "heapify" method tweaked to accommodate sorting by both prime
    numbers and their respective number sets.
     */
    private static void heap(long[] input, int n, int i, int startIndex, boolean single) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child is larger than root
        if (left < n && isGreater(startIndex + left, startIndex + largest, single)) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && isGreater(startIndex + right, startIndex + largest, single)) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            if (single) {
                long swapPrimes = input[startIndex + i];
                input[startIndex + i] = input[startIndex + largest];
                input[startIndex + largest] = swapPrimes;
            }

            long[] swapData = data[startIndex + i];
            data[startIndex + i] = data[startIndex + largest];
            data[startIndex + largest] = swapData;

            heap(input, n, largest, startIndex, single);
        }
    }

    /*
    The first step in the sorting process. A simple counting sort (given such a small k) suffices
    to efficiently sort the numbers by their indexes.
     */
    private static void sortByIndex() {
        int[] countArray = new int[5];

        for (int i = 0; i < numSets; i++) {
            countArray[indexes[i]]++;
        }

        for (int i = 1; i < 5; i++) {
            countArray[i] += countArray[i - 1];
        }

        for (int i = numSets - 1; i >= 0; i--) {
            data[countArray[indexes[i]] - 1] = tempData[i];
            newPrimes[countArray[indexes[i]] - 1] = primes[i];
            countArray[indexes[i]]--;
        }
    }

    /*
    Executing the heapSort() method using a start index calculated with the help of a setsAtIndex[]
    array set up in the index() method.
     */
    private static void sortByPrimes() {
        for (int j = 0; j < 5; j++) {
            //System.out.print(j + " : " + setsAtIndex[j] + " : ");
            if (setsAtIndex[j] > 0) {
                int startIndex = 0;
                for (int k = 0; k < j; k++) {
                    startIndex += setsAtIndex[k];
                }
                //System.out.print(startIndex + " : ");

                heapSort(true, setsAtIndex[j], startIndex);

                /*for (int i = 0; i < setsAtIndex[j]; i++) {
                    System.out.print(newPrimes[startIndex + i] + " ");
                }*/
            }
            //System.out.println();
        }
    }

    /*
    Determines if this step is necessary, and executes the heapSort() method with number set
    comparison, not prime number (hence single = false).
     */
    private static void sortByOther() {
        for (int j = 0; j <= numSets; j++) {
            int i = j;
            int startIndex = -1;
            while (j < numSets - 1 && newPrimes[j] == newPrimes[j + 1]) {
                startIndex = i;
                j++;
            }
            if (startIndex != -1) {
                int length = j - startIndex + 1;
                //System.out.println(startIndex + " " + length);
                heapSort(false, length, startIndex);
            }
        }
    }

    /*
    Writes the ordered number sets in a determined output file, followed by the time (in
    milliseconds) it took to run.
     */
    private static void write(File output, long netTime) throws Exception {
        PrintWriter out = new PrintWriter(output);
        for (int i = 0; i < numSets; i++) {
            for (int j = 0; j < 5; j++) {
                out.print(data[i][j] + " ");
            }
            out.println();
        }
        out.printf("%.2f", (double) netTime / 1000000);
        out.close();
    }
}