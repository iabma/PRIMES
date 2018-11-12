import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

class CS2 {
    private static int numStudents;
    private static String[] studentIDs;
    private static int[] gradYears;
    private static double[] GPAs;

    private static String[] read(Scanner input) {
        numStudents = Integer.parseInt(input.nextLine());
        studentIDs = new String[numStudents];
        gradYears = new int[numStudents];
        GPAs = new double[numStudents];
        String[] info = new String[numStudents];

        for (int i = 0; i < numStudents; i++) {
            info[i] = input.nextLine();
            studentIDs[i] = info[i];
        }

        return info;
    }

    private static void parse(String[] info) {
        for (int i = 0; i < numStudents; i++) {
            gradYears[i] = Integer.parseInt(info[i].substring(info[i].length() - 4)) - 2018;
            GPAs[i] = Double.parseDouble(info[i].substring(info[i].length() - 9,
                    info[i].length() - 5));
        }
    }

    private static boolean isGreater(int one, int two) {
        if (gradYears[one] > gradYears[two]) {
            //System.out.println("lesser grad year");
            return true;
        }
        if (gradYears[one] == gradYears[two] && GPAs[one] > GPAs[two]) {
            //System.out.println("greater gpa same grad year");
            return true;
        }
        //System.out.println("Greater grad/gpa");
        return false;
    }

    private static void heapSort() {
        for (int i = numStudents / 2 - 1; i >= 0; i--)
            heapify(numStudents, i);

        for (int i = numStudents - 1; i >= 0; i--) {
            String tempData = studentIDs[0];
            studentIDs[0] = studentIDs[i];
            studentIDs[i] = tempData;

            int tempGrad = gradYears[0];
            gradYears[0] = gradYears[i];
            gradYears[i] = tempGrad;

            double tempGPA = GPAs[0];
            GPAs[0] = GPAs[i];
            GPAs[i] = tempGPA;

            heapify(i, 0);
            for (int j = 0; j < numStudents; j++) {

            }
        }
    }

    private static void heapify(int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // If left child is larger than root
        if (left < n && isGreater(left, largest)) {
            largest = left;
        }

        // If right child is larger than largest so far
        if (right < n && isGreater(right, largest)) {
            largest = right;
        }

        // If largest is not root
        if (largest != i) {
            String swapData = studentIDs[i];
            studentIDs[i] = studentIDs[largest];
            studentIDs[largest] = swapData;

            int tempGrad = gradYears[i];
            gradYears[i] = gradYears[largest];
            gradYears[largest] = tempGrad;

            double tempGPA = GPAs[i];
            GPAs[i] = GPAs[largest];
            GPAs[largest] = tempGPA;

            heapify(n, largest);
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

        /*System.out.print("Input file name: ");
        File input = new File(in.next());
        System.out.print("Output file name: ");
        File output = new File(in.next());*/

        in.close();

        Scanner inputFile = new Scanner(new File("Gen.txt"));

        String[] info = read(inputFile);

        inputFile.close();

        long startTime = System.nanoTime();

        parse(info);

        heapSort();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write(new File("Output.txt"));

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}
