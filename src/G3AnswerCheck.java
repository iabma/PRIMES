import java.util.Random;

public class G3AnswerCheck {
    private static final long NUM_ITERATIONS = 1000000000l;

    public static void main(String[] args) {
        Random gen = new Random();
        long netTime = 0;



        for (long i = 0; i < NUM_ITERATIONS; i++) {
            int pos = 1;
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
            }
        }

        System.out.printf("%.5f minutes" , netTime / (double) NUM_ITERATIONS * 100.0);
    }
}
