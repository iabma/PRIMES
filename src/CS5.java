import java.io.File;
import java.util.Scanner;

public class CS5 {
    private static long[][] data;
    private static long[][] tempData;
    private static int[] setsAtIndex;
    private static int[] indexes;
    private static int[] newIndexes;
    private static long[] primes;
    private static long[] newPrimes;
    private static int numSets;

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
        indexes = new int[numSets];
        newIndexes = new int[numSets];
        setsAtIndex = new int[5];
        primes = new long[numSets];
        newPrimes = new long[numSets];
        for (int i = 0; i < numSets; i++) {
            for (int j = 0; j < 5; j++) {
                if (isPrime(data[i][j])) {
                    setsAtIndex[j]++;
                    indexes[i] = j;
                    newIndexes[i] = j;
                    primes[i] = data[i][j];
                    newPrimes[i] = data[i][j];
                    System.out.println(data[i][j] + " : " + j);
                }
            }
            System.out.printf("%.2f%%\n", (double) (i + 1) / numSets * 100);
        }
    }

    private static void heapSort(long[] input, int startIndex) {
        int length = input.length;

        for (int i = length / 2 - 1; i >= 0; i--)
            heapify(input, length, i, startIndex);

        for (int i = length - 1; i >= 0; i--) {
            long[] tempData = data[startIndex];
            long temp = input[0];
            long tempPrimes = newPrimes[startIndex];
            data[startIndex] = data[startIndex + i];
            input[0] = input[i];
            newPrimes[startIndex] = newPrimes[startIndex + i];
            data[startIndex] = tempData;
            input[i] = temp;
            newPrimes[startIndex] = tempPrimes;

            heapify(input, i, 0, startIndex);
        }
    }

    private static void heapify(long[] input, int n, int i, int startIndex) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child is larger than root
        if (left < n && input[left] > input[largest]) {
            System.out.println("l");
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && input[right] > input[largest]) {
            System.out.println("r");
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            long[] swapData = data[startIndex + i];
            long swap = input[i];
            long swapPrimes = newPrimes[startIndex + i];
            data[startIndex + i] = data[startIndex + largest];
            input[i] = input[largest];
            newPrimes[startIndex + i] = newPrimes[startIndex + largest];
            data[startIndex + largest] = swapData;
            input[largest] = swap;
            newPrimes[startIndex + largest] = swapPrimes;

            heapify(input, n, largest, startIndex);
        }
    }

    private static boolean isGreater(long[] one, long[] two) {
        for (int i = 0; i < 5; i++) {
            if (one[i] > two[i]) return true;
        }
        return false;
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
                long[] primesAtIndex = new long[setsAtIndex[j]];
                int startIndex = 0;
                for (int k = 0; k < j; k++) {
                    startIndex += setsAtIndex[k];
                }
                System.out.print(startIndex + " : ");

                heapSort(primesAtIndex, startIndex);

                for (int i = 0; i < setsAtIndex[j]; i++) {
                    System.out.print(newPrimes[startIndex + i] + " ");
                }
            }
            System.out.println();
        }
    }

    private static void write() {
        for (int i = 0; i < numSets; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(data[i][j] + " ");
            }
            System.out.println(newPrimes[i] + " " + (newIndexes[i] + 1));
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
        System.out.println("Sorted by index.");

        sortByPrimes();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write();

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}