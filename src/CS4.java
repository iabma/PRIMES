import java.io.File;
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
   Heap Sort: https://www.geeksforgeeks.org/heap-sort/
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
 */
public class CS4 {
    private static int numStrings;
    private static String[] strings;

    private static void heapSort() {
        for (int i = numStrings / 2 - 1; i >= 0; i--)
            heap(numStrings, i);

        for (int i = numStrings - 1; i >= 0; i--) {
            String temp = strings[0];
            strings[0] = strings[i];
            strings[i] = temp;

            heap(i, 0);
        }
    }

    private static void heap(int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child is larger than root
        if (left < n && compare(left, largest)) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && compare(right, largest)) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            String swap = strings[i];
            strings[i] = strings[largest];
            strings[largest] = swap;

            heap(n, largest);
        }
    }

    private static boolean compare(int one, int two) {
        String mostFrequentOne = mostFrequent(strings[one]);
        String mostFrequentTwo = mostFrequent(strings[two]);
        if (Integer.parseInt(mostFrequentOne.substring(1)) > Integer.parseInt(mostFrequentTwo.substring(1))) {
            return true;
        } else if (Integer.parseInt(mostFrequentOne.substring(1)) ==
                Integer.parseInt(mostFrequentTwo.substring(1))) {
            if (isBefore(mostFrequentOne.charAt(0), mostFrequentTwo.charAt(0))) {
                return true;
            } else if (mostFrequentOne.charAt(0) == mostFrequentTwo.charAt(0)) {
                if (strings[one].compareTo(strings[two]) < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void read(Scanner input) {
        numStrings = Integer.parseInt(input.nextLine());
        strings = new String[numStrings];
        for (int i = 0; i < numStrings; i++) {
            strings[i] = input.nextLine();
        }
    }

    private static boolean isBefore(char a, char b) {
        return a < b;
    }

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

    public static void main(String[] args) throws Exception { //MAKE SURE YOU UNTHROW THAT SHIT
        Scanner input = new Scanner(new File("CS4Gen.txt"));

        read(input);

        input.close();

        long startTime = System.nanoTime();

        heapSort();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        for (int i = numStrings - 1; i >= 0; i--) {
            System.out.println(strings[i]);
        }

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}