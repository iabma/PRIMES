import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/* PROBLEM 3.4
   Java 1.8
   IntelliJ IDEA CE
   MacOS

   HOW TO RUN: If using IntelliJ, click the green arrow next to the main method. If using a
   console, compile the file by entering "javac CS3_4.java" while in the correct directory, then
   entering "java CS3_4". The program will prompt you for input and output file names.

   Test data file: CS3_TestData.txt
   Test data generator: CS3_StudentGen.java

   REFERENCES:
   Counting Sort: https://www.geeksforgeeks.org/counting-sort/
   Radix Sort basics: https://www.geeksforgeeks.org/radix-sort/
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
 */
public class CS3_4 {
    private static String[] data;
    private static int[] id;
    private static int numStudents;
    private static final int EXP = 1000;

    /*
    Reads the given number file and creates a data array using the input.
     */
    private static void read(Scanner input, Scanner second) {
        numStudents = input.nextInt();
        data = new String[numStudents];
        id = new int[numStudents];

        input.nextLine();
        for (int i = 0; i < numStudents; i++) {
            id[i] = input.nextInt();
            input.nextLine();
        }

        second.nextLine();
        for (int i = 0; i < numStudents; i++) {
            data[i] = second.nextLine();
        }
    }

    /*
    Implements a simple counting sort which will be used as the foundation for the radix sort.
     */
    static void countingSort(int exp) {
        String[] tempData = new String[numStudents];
        int[] output = new int[numStudents];
        int[] count = new int[EXP];

        for (int i = 0; i < numStudents; i++)
            count[(id[i] / exp) % EXP]++;

        for (int i = 1; i < EXP; i++)
            count[i] += count[i - 1];

        for (int i = numStudents - 1; i >= 0; i--) {
            tempData[count[(id[i] / exp) % EXP] - 1] = data[i];
            output[count[(id[i] / exp) % EXP] - 1] = id[i];
            count[(id[i] / exp) % EXP]--;
        }

        System.arraycopy(output, 0, id, 0, numStudents);
        System.arraycopy(tempData, 0, data, 0, numStudents);
    }

    /*

     */
    static void radixSort() {
        for (int exp = EXP; 1000000000 / exp > 0; exp *= EXP) {
            countingSort(exp);
        }
    }

    /*
    Writes the ordered students in a determined output file, followed by the time (in
    milliseconds) it took to run.
     */
    private static void write(File output) throws Exception {
        PrintWriter out = new PrintWriter(output);

        for (int i = 0; i < numStudents; i++) {
            out.println(data[i]);
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
        Scanner second = new Scanner(input);

        read(inputFile, second);

        long startTime = System.nanoTime();

        radixSort();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write(output);

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}
