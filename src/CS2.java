import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

class CS2 {
    private static int numStudents;
    private static String[] studentIDs;
    private static Student[] students;
    private static Student[] studentsTemp;
    private static int[] gradYears;
    private static int[] numPerGradYear;
    private static int[] GPAs;

    private static String[] read(Scanner input) {
        numStudents = Integer.parseInt(input.nextLine());
        students = new Student[numStudents];
        studentsTemp = new Student[numStudents];
        studentIDs = new String[numStudents];
        gradYears = new int[numStudents];
        numPerGradYear = new int[5];
        GPAs = new int[numStudents];
        String[] info = new String[numStudents];

        for (int i = 0; i < numStudents; i++) {
            info[i] = input.nextLine();
            students[i] = new Student(info[i]);
            studentsTemp[i] = new Student(info[i]);
            studentIDs[i] = info[i];
        }

        return info;
    }

    private static void parse(String[] info) {
        for (int i = 0; i < numStudents; i++) {
            gradYears[i] = Integer.parseInt(info[i].substring(info[i].length() - 4)) - 2018;
        }
    }

    private static void heapSort(int arr[]) {
        int n = arr.length;

        // Build heap (rearrange array)
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (int i = n - 1; i >= 0; i--) {
            // Move current root to end
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    private static void heapify(int arr[], int n, int i) {
        int largest = i; // Initialize largest as root
        int l = 2 * i + 1; // left = 2*i + 1
        int r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest is not root
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }

    private static void sortGradYears() {
        int[] countArray = new int[5];

        for (int i = 0; i < numStudents; i++) {
            countArray[gradYears[i]]++;
        }

        for (int i = 1; i < 5; i++) {
            numPerGradYear[i] = countArray[i];
            countArray[i] += countArray[i - 1];
        }

        for (int i = numStudents - 1; i >= 0; i--) {
            studentsTemp[countArray[gradYears[i]] - 1].setInfo(studentIDs[i]);
            countArray[gradYears[i]]--;
        }
    }

    private static void sortGPAs() {
        for (int i = 0; i < 5; i++) {
            int[] countArray = new int[501];

            int low = i == 0 ?
                    0 : i == 1 ?
                    numPerGradYear[1] : i == 2 ?
                    numPerGradYear[1] + numPerGradYear[2] : i == 3 ?
                    numPerGradYear[1] + numPerGradYear[2] + numPerGradYear[3] :
                    numPerGradYear[1] + numPerGradYear[2] + numPerGradYear[3] + numPerGradYear[4];

            int high = i == 0 ?
                    numPerGradYear[1] - 1 : i == 1 ?
                    numPerGradYear[1] + numPerGradYear[2] - 2: i == 2 ?
                    numPerGradYear[1] + numPerGradYear[2] + numPerGradYear[3] - 3: i == 3 ?
                    numPerGradYear[1] + numPerGradYear[2] + numPerGradYear[3] + numPerGradYear[4] - 4 :
                    numPerGradYear[1] + numPerGradYear[2] + numPerGradYear[3] + numPerGradYear[4]
                    + numPerGradYear[5] - 5;

            System.out.println(low + " " + high);

            for (int j = low; j < high; j++) {
                countArray[studentsTemp[j].getGPA()]++;
            }

            for (int j = 1; j < 501; j++) {
                countArray[j] += countArray[j - 1];
            }

            for (int j = high - 1; j >= low; j--) {
                students[countArray[studentsTemp[j].getGPA()] - 1].setInfo(studentIDs[j]);
                countArray[studentsTemp[j].getGPA()]--;
            }
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

        String[] info = read(inputFile);

        inputFile.close();

        long startTime = System.nanoTime();

        parse(info);

        //countingSort(gradYears, true);

        sortGradYears();

        sortGPAs();

        //heapSort(GPAs);

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write(output);

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}
