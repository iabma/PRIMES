import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/* PROBLEM 2
   Java 1.8
   IntelliJ IDEA CE
   MacOS

   HOW TO RUN: If using IntelliJ, click the green arrow next to the main method. If using a
   console, compile the file by entering "javac CS2.java" while in the correct directory, then
   entering "java CS2". The program will prompt you for input and output file names.

   Test data file: StudentTestData.txt
   Test data generator: StudentGen.java

   REFERENCES:
   Counting Sort: https://www.geeksforgeeks.org/counting-sort/
   Heap Sort: https://www.geeksforgeeks.org/heap-sort/
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
 */
class CS2 {
    private static int numStudents;
    private static String[] tempStudentIDs;
    private static String[] studentIDs;
    private static int[] gradYears;
    private static int[] numPerGradYear;
    private static double[] GPAs;
    private static double[] tempGPAs;

    private static String[] read(Scanner input) {
        numStudents = Integer.parseInt(input.nextLine());
        studentIDs = new String[numStudents];
        tempStudentIDs = new String[numStudents];
        gradYears = new int[numStudents];
        numPerGradYear = new int[5];
        GPAs = new double[numStudents];
        tempGPAs = new double[numStudents];
        String[] info = new String[numStudents];

        for (int i = 0; i < numStudents; i++) {
            info[i] = input.nextLine();
            studentIDs[i] = info[i];
            tempStudentIDs[i] = info[i];
        }

        return info;
    }

    private static void heapSort(int length, int startIndex) {
        ;
        for (int i = length / 2 - 1; i >= 0; i--)
            heap(length, i, startIndex);

        for (int i = length - 1; i >= 0; i--) {
            double temp = GPAs[startIndex];
            GPAs[startIndex] = GPAs[startIndex + i];
            GPAs[startIndex + i] = temp;

            String tempData = studentIDs[startIndex];
            studentIDs[startIndex] = studentIDs[startIndex + i];
            studentIDs[startIndex + i] = tempData;

            heap(i, 0, startIndex);
        }
    }

    private static void heap(int n, int i, int startIndex) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child is larger than root
        if (left < n && GPAs[startIndex + left] > GPAs[startIndex + largest]) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && GPAs[startIndex + right] > GPAs[startIndex + largest]) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            double swap = GPAs[startIndex + i];
            GPAs[startIndex + i] = GPAs[startIndex + largest];
            GPAs[startIndex + largest] = swap;

            String swapData = studentIDs[startIndex + i];
            studentIDs[startIndex + i] = studentIDs[startIndex + largest];
            studentIDs[startIndex + largest] = swapData;

            heap(n, largest, startIndex);
        }
    }


    private static void sortGradYears(String[] info) {
        int[] countArray = new int[5];

        for (int i = 0; i < numStudents; i++) {
            gradYears[i] = Integer.parseInt(info[i].substring(info[i].length() - 4)) - 2018;
            GPAs[i] = Double.parseDouble(info[i].substring(info[i].length() - 9,
                    info[i].length() - 5));
            tempGPAs[i] = GPAs[i];
            countArray[gradYears[i]]++;
        }

        for (int i = 0; i < 5; i++) {
            numPerGradYear[i] = countArray[i];
            if (i > 0) countArray[i] += countArray[i - 1];
        }

        for (int i = numStudents - 1; i >= 0; i--) {
            studentIDs[countArray[gradYears[i]] - 1] = tempStudentIDs[i];
            GPAs[countArray[gradYears[i]] - 1] = tempGPAs[i];
            countArray[gradYears[i]]--;
        }
    }

    private static void sortGPAs() {
        for (int i = 0; i < 5; i++) {
            if (numPerGradYear[i] > 0) {
                int startIndex = 0;
                for (int j = 0; j < i; j++) {
                    startIndex += numPerGradYear[j];
                }

                heapSort(numPerGradYear[i], startIndex);
            }
        }
    }

    private static void write(File output) throws Exception {
        PrintWriter out = new PrintWriter(output);

        for (int i = 0; i < numStudents; i++) {
            out.println(studentIDs[i]);
        }

        out.close();
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        File input;
        File output;

        do {
            System.out.print("Input file name: ");
            input = new File(in.next());
        } while (!input.exists());
        do {
            System.out.print("Output file name: ");
            output = new File(in.next());
        } while (!output.exists());

        in.close();

        Scanner inputFile = new Scanner(input);

        String[] info = read(inputFile);

        inputFile.close();

        long startTime = System.nanoTime();

        sortGradYears(info);

        sortGPAs();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write(output);

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}