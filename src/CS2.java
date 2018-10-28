import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

class CS2 {
    private static int numStudents;
    private static int earliestGradYear;
    private static Student[] students;
    private static Student[] studentsTemp;

    private static void parse(Scanner input) {
        numStudents = Integer.parseInt(input.nextLine());
        students = new Student[numStudents];
        studentsTemp = new Student[numStudents];

        for (int i = 0; i < numStudents; i++) {
            String info = input.nextLine();
            int gradYear = Integer.parseInt(info.substring(info.length() - 4));

            if (i == 0) {
                earliestGradYear = gradYear;
            } else if (gradYear < earliestGradYear) {
                earliestGradYear = gradYear;
            }
            students[i] = new Student(info);
            studentsTemp[i] = students[i];
        }

        for (int i = 0; i < numStudents; i++) {
            studentsTemp[i].setModifiedGradYear(earliestGradYear);
            students[i].setModifiedGradYear(earliestGradYear);
        }
    }

    private static void countingSort() {
        int[] countArray = new int[5];

        for (int i = 0; i < numStudents; i++) {
            countArray[studentsTemp[i].modifiedGradYear]++;
        }

        for (int i = 1; i < 5; i++) {
            countArray[i] += countArray[i - 1];
        }

        for (int i = numStudents - 1; i >= 0; i--) {
            students[countArray[studentsTemp[i].modifiedGradYear] - 1] = studentsTemp[i];
            countArray[studentsTemp[i].modifiedGradYear]--;
        }
    }

    private static void write(File output) throws Exception {
        PrintWriter out = new PrintWriter(output);

        for (int i = 0; i < numStudents; i++) {
            out.println(students[i]);
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

        //quickSort(students, 0, numStudents - 1);

        countingSort();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write(output);

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}
