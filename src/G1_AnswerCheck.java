import java.util.Random;
import java.util.Scanner;

public class G1_AnswerCheck {
    private static final int NUM_ITERATIONS = 10000000;

    public static void main(String[] args) {
        Random gen = new Random();
        Scanner in = new Scanner(System.in);
        int numSuccesses = 0;

        System.out.print("Number of pairs: ");
        int pairAmt = in.nextInt();

        for (int i = 0; i < NUM_ITERATIONS; i++) {
            int[] set = new int[10];
            boolean amtReached = false;
            int numPairs = 0;
            for (int j = 0; j < 10; j++) {
                int val = gen.nextInt(2);
                set[j] = val;
                if (j > 0 && set[j] == set[j - 1]) numPairs++;
                amtReached = numPairs == pairAmt;
            }
            if (amtReached) numSuccesses++;
        }

        System.out.printf("~%.3f%%", numSuccesses / (double) NUM_ITERATIONS * 100.0);
    }
}
