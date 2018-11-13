import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class CS4StringGen {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter("CS4Gen.txt");

        System.out.print("Number of strings: ");
        long numSets = Long.parseLong(in.next());
        out.println(numSets);

        in.close();

        for (int i = 0; i < numSets; i++) {
            int length = ThreadLocalRandom.current().nextInt(1,41);
            String str = "";
            for (int j = 0; j < length; j++) {
                int charIndex = ThreadLocalRandom.current().nextInt(0, 26);
                str += (char)(charIndex + 65);
            }
            System.out.printf("%.2f%% done.\n", (double) (i + 1) / numSets * 100);
            out.println(str);
        }

        out.close();
    }
}
