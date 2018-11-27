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
    private static int[] GPAs;
    private static int[] tempGPAs;

    /*
    Prompts the user for the necessary correct file names, then sorts the data found in the input
    file first by graduation year (counting sort), then by GPA (counting sort). Finally, the sorted
    data is written into the entered output file.
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
        String[] info = read(inputFile);
        inputFile.close();

        long startTime = System.nanoTime();

        sortGradYears(info);

        sortGPAs();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write(output, netTime);
    }

    /*
    Reads the given number file and creates an array using the input.
     */
    private static String[] read(Scanner input) {
        numStudents = Integer.parseInt(input.nextLine());
        studentIDs = new String[numStudents];
        tempStudentIDs = new String[numStudents];
        gradYears = new int[numStudents];
        numPerGradYear = new int[5];
        GPAs = new int[numStudents];
        tempGPAs = new int[numStudents];
        String[] info = new String[numStudents];

        for (int i = 0; i < numStudents; i++) {
            info[i] = input.nextLine();
            studentIDs[i] = info[i];
        }

        return info;
    }

    /*
    Pre-processes the data into useful information, then sorts the students by graduation year
    using a simple counting sort.
     */
    private static void sortGradYears(String[] info) {
        int[] countArray = new int[5];

        for (int i = 0; i < numStudents; i++) {
            gradYears[i] = Integer.parseInt(info[i].substring(info[i].length() - 4)) - 2018;
            GPAs[i] = (int)(Double.parseDouble(info[i].substring(info[i].length() - 9,
                    info[i].length() - 5)) * 100);
            tempGPAs[i] = GPAs[i];
            countArray[gradYears[i]]++;
        }

        for (int i = 0; i < 5; i++) {
            numPerGradYear[i] = countArray[i];
            if (i > 0)
                countArray[i] += countArray[i - 1];
        }

        for (int i = numStudents - 1; i >= 0; i--) {
            countArray[gradYears[i]]--;
            tempStudentIDs[countArray[gradYears[i]]] = studentIDs[i];
            GPAs[countArray[gradYears[i]]] = tempGPAs[i];
        }
    }

    /*
    Sorts the students within every graduation year by their GPAs using a modified counting sort.
     */
    private static void sortGPAs() {
        for (int i = 0; i < 5; i++) {
            if (numPerGradYear[i] > 0) {
                int startIndex = 0;
                for (int j = 0; j < i; j++) {
                    startIndex += numPerGradYear[j];
                }

                int[] count = new int[501];

                for (int k = startIndex; k < startIndex + numPerGradYear[i]; k++)
                    count[GPAs[k]]++;

                for (int k = 1; k < 501; k++)
                    count[k] += count[k - 1];

                for (int k = startIndex + numPerGradYear[i] - 1; k>= startIndex; k--) {
                    count[GPAs[k]]--;
                    studentIDs[startIndex + count[GPAs[k]]] = tempStudentIDs[k];
                }
            }
        }
    }

    /*
    Writes the ordered students in a determined output file, followed by the time (in
    milliseconds) it took to run.
     */
    private static void write(File output, long netTime) throws Exception {
        PrintWriter out = new PrintWriter(output);
        for (int i = 0; i < numStudents; i++) {
            out.println(studentIDs[i]);
        }
        out.printf("%.2f", (double) netTime / 1000000);
        out.close();
    }
}