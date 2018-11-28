import java.util.Random;
import java.util.Scanner;

/* MATH PROBLEM 3 PROCEDURAL ANSWER
   Java 1.8
   IntelliJ IDEA CE
   MacOS

   HOW TO RUN: If using a console, compile the file by entering "javac CS1.java" while in the
   correct directory, then entering "java CS1". The program will prompt you for input and output
   file names.

   REFERENCES:
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
 */
public class G3_AnswerCheck {
    private static final long NUM_ITERATIONS = 100000l;

    private static void checkA() {
        Random gen = new Random();
        long netTime = 0;

        for (long i = 0; i < NUM_ITERATIONS; i++) {
            int pos = 1;
            int numMoves = 0;
            while (pos != 6) {
                int move = gen.nextInt(4);
                if (pos == 1) {
                    pos = move + 2;
                } else if (pos == 2 || pos == 3) {
                    pos = move == 0 ? 1 : move + 3;
                } else {
                    pos = move == 3 ? 6 : move + 1;
                }
                netTime++;
                numMoves++;
            }
        }

        System.out.printf("%d minutes", (int) Math.round(netTime / (double) NUM_ITERATIONS));
    }

    private static void checkB() {
        Random gen = new Random();
        long netTime = 0;

        for (long i = 0; i < NUM_ITERATIONS; i++) {
            int pos = 1;
            int numMoves = 0;
            while (pos != 8) {
                int move = gen.nextInt(3);
                switch (pos) {
                    case 1: pos = move == 0 ? 2 : move + 3; break;
                    case 2: pos = move == 1 ? 7 : move + 1; break;
                    case 3: pos = move == 1 ? 8 : move + 2; break;
                    case 4: pos = move == 1 ? 6 : move + 1; break;
                    case 5: pos = move == 0 ? 1 : move + 5; break;
                    case 6: pos = move == 2 ? 8 : move + 4; break;
                    case 7: pos = move == 0 ? 5 : move == 1 ? 8 : move; break;
                }
                netTime++;
                numMoves++;
            }
        }

        System.out.printf("%d minutes", (int) Math.round(netTime / (double) NUM_ITERATIONS));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input;

        do {
            System.out.print("Octahedron or Cube? ");
            input = in.nextLine();
        } while (!input.equalsIgnoreCase("octahedron") && !input.equalsIgnoreCase("cube"));
        if (input.equalsIgnoreCase("octahedron")) {
            checkA();
        } else {
            checkB();
        }
    }
}
