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
        setsAtIndex = new int[numSets];
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
                }
            }
        }
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
            for (int i = 0; i < setsAtIndex[j]; i++) {
                int startIndex = 0;
                int currentJ = j;
                while (currentJ > 0) {
                    currentJ--;
                    startIndex += setsAtIndex[currentJ];
                }


            }
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

        long startTime = System.nanoTime();

        index();

        sortByIndex();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write();

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}
