import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/* PROBLEM 1
   Java 1.8
   IntelliJ IDEA CE
   MacOS

   HOW TO RUN: If using IntelliJ, click the green arrow next to the main method. If using a
   console, compile the file by entering "javac CS1.java" while in the correct directory, then
   entering "java CS1". The program will prompt you for input and output file names.

   Test data file: StudentTestData.txt
   Test data generator: StudentGen.java

   REFERENCES:
   Counting Sort: https://www.geeksforgeeks.org/counting-sort/
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
 */
class CS1 {
    private static int numStudents;
    private static String[] studentIDs;
    private static String[] orderedStudentIDs;
    private static int[] gradYears;

    /*
    Prompts the user for the necessary correct file names, then sorts the data found in the input
    file and writes the sorted data into the entered output file.
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
        String[] readInput = read(inputFile);
        inputFile.close();

        long startTime = System.nanoTime();

        sort(readInput);

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write(output, netTime);
    }

    /*
    Reads the given number file and creates an array using the input.
     */
    private static String[] read(Scanner input) {
        numStudents = input.nextInt();
        studentIDs = new String[numStudents];
        gradYears = new int[numStudents];
        String[] info = new String[numStudents];

        input.nextLine();
        for (int i = 0; i < numStudents; i++) {
            info[i] = input.nextLine();
            studentIDs[i] = info[i];
        }

        return info;
    }

    /*
    Simple counting sort, uses a modified version of the graduations years in order to save storage.
     */
    private static void sort(String[] input) {
        int[] countArray = new int[5];
        orderedStudentIDs = new String[numStudents];

        for (int i = 0; i < numStudents; i++) {
            gradYears[i] = Integer.parseInt(input[i].substring(input[i].length() - 4)) - 2018;
            countArray[gradYears[i]]++;
        }

        for (int i = 1; i < 5; i++) {
            countArray[i] += countArray[i - 1];
        }

        for (int i = numStudents - 1; i >= 0; i--) {
            countArray[gradYears[i]]--;
            orderedStudentIDs[countArray[gradYears[i]]] = studentIDs[i];
        }
    }

    /*
    Writes the ordered students in a determined output file, followed by the time (in
    milliseconds) it took to run.
     */
    private static void write(File output, long netTime) throws Exception {
        PrintWriter out = new PrintWriter(output);
        for (int i = 0; i < numStudents; i++) {
            out.println(orderedStudentIDs[i]);
        }
        out.printf("%.2f", (double) netTime / 1000000);
        out.close();
    }
}