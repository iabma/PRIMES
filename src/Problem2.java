import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

class Problem2 {
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

    private static int partition(Student[] input, int startIndex,
                                 int endIndex) {
        int pivot = input[endIndex].modifiedGradYear;
        int index = (startIndex - 1);

        for (int i = startIndex; i < endIndex; i++) {
            if (input[i].modifiedGradYear <= pivot) {
                index++;

                Student indexTemp = input[index];
                input[index] = input[i];
                input[i] = indexTemp;
            }
        }

        Student endIndexTemp = input[index + 1];
        input[index + 1] = input[endIndex];
        input[endIndex] = endIndexTemp;

        return index + 1;
    }

    private static void countingSort() {
        int[] countArray = new int[numStudents];

        for (int i = 0; i < numStudents; i++) {
            countArray[studentsTemp[i].modifiedGradYear]++;
        }

        for (int i = 1; i < numStudents; i++) {
            countArray[i] += countArray[i - 1];
        }

        for (int i = numStudents - 1; i >= 0; i--) {
            students[countArray[studentsTemp[i].modifiedGradYear] - 1] = studentsTemp[i];
            countArray[studentsTemp[i].modifiedGradYear]--;
        }
    }

    private static void quickSort(Student[] input, int startIndex, int endIndex) {
        if (startIndex < endIndex) {
            int partitionIndex = partition(input, startIndex, endIndex);

            quickSort(input, startIndex, partitionIndex - 1);
            quickSort(input, partitionIndex + 1, endIndex);
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
