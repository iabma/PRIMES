import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class CS3_4 {
    private static String[] data;
    private static int[] id;
    private static int numStudents;
    private static final int EXP = 1000;

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

    static void countSort(int exp) {
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

    static void radixSort() {
        for (int exp = EXP; 1000000000 / exp > 0; exp *= EXP) {
            countSort(exp);
        }
    }

    private static void write(File output) throws Exception {
        PrintWriter out = new PrintWriter(output);

        for (int i = 0; i < numStudents; i++) {
            out.println(data[i]);
        }

        out.close();
    }

    public static void main(String[] args) throws Exception { //UNTRHOWWWW
        Scanner in = new Scanner(System.in);

        /*System.out.print("Input file name: ");
        File input = new File(in.next());
        System.out.print("Output file name: ");
        File output = new File(in.next());*/

        in.close();

        Scanner inputFile = new Scanner(new File("CS3_ProcedurallyGeneratedData.txt"));
        Scanner second = new Scanner(new File("CS3_ProcedurallyGeneratedData.txt"));

        read(inputFile, second);

        long startTime = System.nanoTime();

        radixSort();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        write(new File("CS3_OrderedData.txt"));

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}
