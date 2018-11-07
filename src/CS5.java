import java.io.File;
import java.math.BigInteger;
import java.util.Scanner;

public class CS5 {
    private static long[][] data;
    private static long[][] tempData;
    private static long[][] equalPrimesAtIndex;
    private static int[] setsAtIndex;
    private static int[] indexes;
    private static int[] newIndexes;
    private static int[] primesNum;
    private static long[] primes;
    private static long[] newPrimes;
    private static PrimeSet[] primeSets;
    private static int numSets;
    private static long numPrimes;
    private static long highestNum;

    private static boolean isPrime(long num) {
        BigInteger numToCheck = BigInteger.valueOf(num);
        return numToCheck.isProbablePrime(1);
    }

    private static void read(Scanner input) {
        numSets = Integer.parseInt(input.nextLine());
        data = new long[numSets][5];
        tempData = new long[numSets][5];

        for (int i = 0; i < numSets; i++) {
            for (int j = 0; j < 5; j++) {
                data[i][j] = Long.parseLong(input.next());
                tempData[i][j] = data[i][j];
            }
        }
    }

    private static void index() {
        primeSets = new PrimeSet[numSets];
        indexes = new int[numSets];
        newIndexes = new int[numSets];
        setsAtIndex = new int[5];
        primes = new long[numSets];
        newPrimes = new long[numSets];
        highestNum = 0;

        for (int i = 0; i < numSets; i++) {
            for (int j = 0; j < 5; j++) {
                if (data[i][j] > highestNum) highestNum = data[i][j];
                if (isPrime(data[i][j])) {
                    setsAtIndex[j]++;
                    indexes[i] = j;
                    newIndexes[i] = j;
                    primes[i] = data[i][j];
                    newPrimes[i] = data[i][j];
                }
            }
            System.out.printf("%.2f%%\n", (double) (i + 1) / numSets * 100);
        }
    }

    private static void heapSort(boolean single, int length, int startIndex) {
        long[] input = newPrimes;

        for (int i = length / 2 - 1; i >= 0; i--)
            heapify(input, length, i, startIndex);

        for (int i = length - 1; i >= 0; i--) {
            long tempPrimes = input[startIndex];
            input[startIndex] = input[startIndex + i];
            input[startIndex + i] = tempPrimes;

            long[] tempData = data[startIndex];
            data[startIndex] = data[startIndex + i];
            data[startIndex + i] = tempData;

            heapify(input, i, 0, startIndex);
        }
    }

    private static void heapify(long[] input, int n, int i, int startIndex) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child is larger than root
        if (left < n && isGreater(input[startIndex + left], input[startIndex + largest])) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && isGreater(input[startIndex + right], input[startIndex + largest])) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            long swapPrimes = input[startIndex + i];
            input[startIndex + i] = input[startIndex + largest];
            input[startIndex + largest] = swapPrimes;

            long[] swapData = data[startIndex + i];
            data[startIndex + i] = data[startIndex + largest];
            data[startIndex + largest] = swapData;

            heapify(input, n, largest, startIndex);
        }
    }

    private static boolean isGreater(long[] one, long[] two) {
        for (int i = 0; i < 5; i++) {
            if (one[i] > two[i]) return true;
        }
        return false;
    }

    private static boolean isGreater(long one, long two) {
        return one > two;
    }

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
            newIndexes[countArray[indexes[i]] - 1] = indexes[i];
            countArray[indexes[i]]--;
        }
    }

    private static void sortByPrimes() {
        for (int j = 0; j < 5; j++) {
            System.out.print(j + " : " + setsAtIndex[j] + " : ");
            if (setsAtIndex[j] > 0) {
                int startIndex = 0;
                for (int k = 0; k < j; k++) {
                    startIndex += setsAtIndex[k];
                }
                System.out.print(startIndex + " : ");

                heapSort(true, setsAtIndex[j], startIndex);

                for (int i = 0; i < setsAtIndex[j]; i++) {
                    System.out.print(newPrimes[startIndex + i] + " ");

                }
            }
            System.out.println();
        }
    }

    private static void sortByOther() {
        for (int i = 0; i < numSets; i++) {

        }
    }

    private static void write() {
        for (int i = 0; i < numSets; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println(": " + newPrimes[i] + " " + (newIndexes[i] + 1));
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        //System.out.print("Input file name: ");
        File input = new File("NumSets.txt");//in.next());
        //System.out.print("Output file name: ");
        //File output = new File(in.next());

        in.close();

        Scanner inputFile = new Scanner(input);
        read(inputFile);
        inputFile.close();
        System.out.println("File read. Indexing...");

        long startTime = System.nanoTime();

        index();
        System.out.println("Indexed.");

        sortByIndex();
        System.out.println("Sorted by prime number index.");

        sortByPrimes();
        System.out.println("Sorted by prime number value");

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write();

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}