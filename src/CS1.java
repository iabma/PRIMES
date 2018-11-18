import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

class CS1 {
    private static int numStudents;
    private static String[] studentIDs;
    private static String[] orderedStudentIDs;
    private static int[] gradYears;

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

    private static void sort(String[] input) {
        int[] countArray = new int[5];
        orderedStudentIDs = new String[numStudents];

        for (int i = 0; i < numStudents; i++) {
            gradYears[i] = Integer.parseInt(input[i].substring(input[i].length() - 4));
            countArray[gradYears[i] - 2018]++;
        }

        for (int i = 1; i < 5; i++) {
            countArray[i] += countArray[i - 1];
        }

        for (int i = numStudents - 1; i >= 0; i--) {
            orderedStudentIDs[countArray[gradYears[i] - 2018] - 1] = studentIDs[i];
            countArray[gradYears[i] - 2018]--;
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

        String[] readInput = read(inputFile);

        long startTime = System.nanoTime();

        sort(readInput);

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write(output);

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}