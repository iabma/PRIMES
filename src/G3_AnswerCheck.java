import java.util.Random;

public class G3_AnswerCheck {
    private static final long NUM_ITERATIONS = 10000l;

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
            System.out.println(numMoves + " moves");
        }

        System.out.printf("%.5f minutes", netTime / (double) NUM_ITERATIONS);
    }

    private static void checkB() {
        Random gen = new Random();
        long netTime = 0;

        for (long i = 0; i < NUM_ITERATIONS; i++) {
            int pos = 1;
            int numMoves = 0;
            while (pos != 8) {
                int move = gen.nextInt(3);
                if (pos == 1) pos = move == 0 ? 2 : move + 3;
                else if (pos == 2) pos = move == 1 ? 7 : move + 1;
                else if (pos == 3) pos = move == 1 ? 8 : move + 2;
                else if (pos == 4) pos = move == 1 ? 6 : move + 1;
                else if (pos == 5) pos = move == 0 ? 1 : move + 5;
                else if (pos == 6) pos = move == 2 ? 8 : move + 4;
                else if (pos == 7) pos = move == 0 ? 5 : move == 1 ? 8 : move;
                netTime++;
                numMoves++;
            }
            System.out.println(numMoves + " moves");
        }

        System.out.printf("%.5f minutes", netTime / (double) NUM_ITERATIONS);
    }

    public static void main(String[] args) {
        checkB();
    }
}
