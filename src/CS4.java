import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/* PROBLEM 4
   Java 1.8
   IntelliJ IDEA CE
   MacOS

   HOW TO RUN: If using a console, compile the file by entering "javac CS1.java" while in the
   correct directory, then entering "java CS1". The program will prompt you for input and output
   file names.

   Test data file: CS4_TestData.txt
   Test data generator: CS4_StringGen.java

   REFERENCES:
   Counting Sort: https://www.geeksforgeeks.org/counting-sort/
   Heap Sort: https://www.geeksforgeeks.org/heap-sort/
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
 */
public class CS4 {
    private static final int NUM_LETTERS = 26;
    private static final int STRING_MAX_LENGTH = 40;

    private static int numStrings;
    private static String[] strings;
    private static String[] tempStrings;
    private static char[] mostFreqLetter;
    private static char[] tempMostFreqLetter;
    private static int[] stringsWithOcc;

    /*
    Reads the desired data file and output file, then reads the data. The timer is started, and
    the necessary pre-calculations are made. Then, the three sorting steps are executed: the
    strings are sorted by number of occurrences of the most frequent letter, the most frequent
    letter (alphabetically), and finally the strings themselves (alphabetically). The timer
    is then stopped, and the ordered set is written to the desired output file.
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

        sortByNumOcc();

        sortByLetter();

        sortByCompare();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write(output, netTime);
    }

    /*
    Determines the most frequent letter in a string and its frequency. If any letters have the
    same frequency, the alphabetically first one takes precedence. Returns a string in which the
    first character is the letter and the remaining characters are the digits of the frequency.
     */
    private static String mostFrequent(String in) {
        int[] count = new int[26];
        for (int i = 0; i < in.length(); i++) {
            count[in.charAt(i) - 65]++;
        }

        int highestIndex = 0;

        for (int i = 1; i < count.length; i++) {
            if (count[i] > count[highestIndex] || count[i] == count[highestIndex] && i < highestIndex)
                highestIndex = i;
        }

        return (char) (highestIndex + 65) + "" + count[highestIndex];
    }

    /*
    Compares two inputted strings alphabetically.
     */
    private static boolean compare(int one, int two) {
        return strings[one].compareTo(strings[two]) > 0;
    }

    /*
    Reads the given number file and creates an array using the input.
     */
    private static void read(Scanner input) {
        numStrings = Integer.parseInt(input.nextLine());
        strings = new String[numStrings];
        for (int i = 0; i < numStrings; i++) {
            strings[i] = input.nextLine();
        }
    }

    /*
    Basic heap sort method used to sort strings by their alphabetical value.
     */
    private static void heapSort(int length, int startIndex) {
        for (int i = length / 2 - 1; i >= 0; i--)
            heap(length, i, startIndex);

        for (int i = length - 1; i >= 0; i--) {
            String temp = strings[startIndex];
            strings[startIndex] = strings[startIndex + i];
            strings[startIndex + i] = temp;

            heap(i, 0, startIndex);
        }
    }

    /*
    Complementary "heapify" method, where the comparison is alphabetical.
     */
    private static void heap(int heapSize, int root, int startIndex) {
        int largest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < heapSize && compare(startIndex + left, startIndex + largest)) {
            largest = left;
        }

        if (right < heapSize && compare(startIndex + right, startIndex + largest)) {
            largest = right;
        }

        if (largest != root) {
            String swap = strings[startIndex + root];
            strings[startIndex + root] = strings[startIndex + largest];
            strings[startIndex + largest] = swap;

            heap(heapSize, largest, startIndex);
        }
    }

    /*
    Initializes certain variables then sorts strings[] by the number of occurrences of the most
    frequent letter using a counting sort. If multiple letters have the same occurrence in a
    string, the alphabetically first one takes precedence.
     */
    private static void sortByNumOcc() {
        int[] countArray = new int[STRING_MAX_LENGTH];
        int[] numOcc = new int[numStrings];
        tempStrings = new String[numStrings];
        mostFreqLetter = new char[numStrings];
        tempMostFreqLetter = new char[numStrings];
        stringsWithOcc = new int[STRING_MAX_LENGTH];

        for (int i = 0; i < numStrings; i++) {
            String mostFrequent = mostFrequent(strings[i]);
            mostFreqLetter[i] = mostFrequent.charAt(0);
            numOcc[i] = Integer.parseInt(mostFrequent.substring(1));
            stringsWithOcc[numOcc[i]]++;
            countArray[numOcc[i]]++;
        }

        for (int i = 1; i < STRING_MAX_LENGTH; i++) {
            countArray[i] += countArray[i - 1];
        }

        for (int i = numStrings - 1; i >= 0; i--) {
            countArray[numOcc[i]]--;
            tempStrings[countArray[numOcc[i]]] = strings[i];
            tempMostFreqLetter[countArray[numOcc[i]]] = mostFreqLetter[i];
        }
    }

    /*
    Within every occurrence level, the strings are sorted based on the alphabetical location of
    their most frequent letter using a counting sort.
     */
    private static void sortByLetter() {
        for (int j = 1; j < STRING_MAX_LENGTH; j++) {
            if (stringsWithOcc[j] > 0) {
                int startIndex = 0;
                for (int k = 1; k < j; k++) {
                    startIndex += stringsWithOcc[k];
                }

                int[] countArray = new int[NUM_LETTERS];

                for (int i = startIndex; i < startIndex + stringsWithOcc[j]; i++) {
                    countArray[tempMostFreqLetter[i] - 65]++;
                }

                for (int i = 1; i < NUM_LETTERS; i++) {
                    countArray[i] += countArray[i - 1];
                }

                for (int i = startIndex + stringsWithOcc[j] - 1; i >= startIndex; i--) {
                    countArray[tempMostFreqLetter[i] - 65]--;
                    strings[startIndex + countArray[tempMostFreqLetter[i] - 65]] = tempStrings[i];
                    mostFreqLetter[startIndex + countArray[tempMostFreqLetter[i] - 65]] = tempMostFreqLetter[i];
                }
            }
        }
    }

    /*
    Wherever there are any points at which the most frequent letter has the same number of
    occurrences in a group of strings, a heap sort method is used to arrange the strings
    alphabetically.
     */
    private static void sortByCompare() {
        for (int j = 0; j <= numStrings; j++) {
            int i = j;
            int startIndex = -1;
            while (j < numStrings - 1 && mostFreqLetter[j] == mostFreqLetter[j + 1]) {
                startIndex = i;
                j++;
            }
            if (startIndex != -1) {
                int length = j - startIndex + 1;

                heapSort(length, startIndex);
            }
        }
    }

    /*
    Writes the ordered students in a determined output file, followed by the time (in
    milliseconds) it took to run.
     */
    private static void write(File output, long netTime) throws Exception {
        PrintWriter out = new PrintWriter(output);
        for (int i = 0; i < numStrings; i++) {
            out.println(strings[i]);
        }
        out.printf("%.2f", (double) netTime / 1000000);
        out.close();
    }
}