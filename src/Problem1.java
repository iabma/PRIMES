import java.io.PrintWriter;
import java.util.Scanner;
import java.io.File;

class Problem1 {
    private static int numStudents;
    private static int earliestGradYear;
    private static String[] studentIDs;
    private static String[] orderedStudentIDs;
    private static int[] gradYears;

    private static void parse(Scanner input) {
        numStudents = Integer.parseInt(input.nextLine());
        studentIDs = new String[numStudents];
        gradYears = new int[numStudents];

        for (int i = 0; i < numStudents; i++) {
            String info = input.nextLine();
            int gradYear = Integer.parseInt(info.substring(info.length() - 4));

            if (i == 0) {
                earliestGradYear = gradYear;
            } else if (gradYear < earliestGradYear) {
                earliestGradYear = gradYear;
            }
            studentIDs[i] = info;
            gradYears[i] = gradYear;
        }
    }

    private static void sort() {
        int[] countArray = new int[numStudents];
        orderedStudentIDs = new String[numStudents];

        for (int i = 0; i < numStudents; i++) {
            countArray[gradYears[i] - earliestGradYear]++;
        }

        for (int i = 1; i < numStudents; i++) {
            countArray[i] += countArray[i - 1];
        }

        for (int i = numStudents - 1; i >= 0; i--) {
            orderedStudentIDs[countArray[gradYears[i] - earliestGradYear] - 1] = studentIDs[i];
            countArray[gradYears[i] - earliestGradYear]--;
        }
    }

    private static void write(File output) throws Exception {
        PrintWriter out = new PrintWriter(output);

        for (int i = 0; i < numStudents; i++) {
            out.println(orderedStudentIDs[i]);
        }

        out.close();
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);

        System.out.print("Input file name: ");
        File input = new File(in.next());
        System.out.print("Output file name: ");
        File output = new File(in.next());

        in.close();

        Scanner inputFile = new Scanner(input);

        parse(inputFile);

        long startTime = System.nanoTime();

        sort();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write(output);

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}