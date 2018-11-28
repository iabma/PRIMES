import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/* PROBLEM 4 TEST FILE GENERATOR
   Java 1.8
   IntelliJ IDEA CE
   MacOS

   HOW TO RUN: If using a console, compile the file by entering "javac CS4_StringGen.java" while in
   the correct directory, then entering "java CS4_StringGen".

   REFERENCES:
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
 */
public class CS4_StringGen {
    /*
    Prompts the user to enter the desire number of strings, then randomly produces strings
    varying from 1 to 40 letters in length.
     */
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter("CS4_TestData.txt");

        System.out.print("Number of strings: ");
        long numSets = Long.parseLong(in.next());
        out.println(numSets);

        in.close();

        for (int i = 0; i < numSets; i++) {
            int length = ThreadLocalRandom.current().nextInt(1, 41);
            String str = "";
            for (int j = 0; j < length; j++) {
                int charIndex = ThreadLocalRandom.current().nextInt(0, 26);
                str += (char) (charIndex + 65);
            }
            System.out.printf("%.2f%% done.\n", (double) (i + 1) / numSets * 100);
            out.println(str);
        }

        out.close();
    }
}
