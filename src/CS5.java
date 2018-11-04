public class CS5 {
    private static boolean isPrime(long num) {
        if (num == 1) {
            return false;
        }

        for (int i = 2; i < (num < 50 ? num : (int) Math.sqrt(num)); i++) {
            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }

    private static void index(int[][] data) {
        int[] indexes = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < 5; j++) {
                if (isPrime(data[i][j])) {
                    indexes[i] = j;
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        long startTime = System.nanoTime();

        //index();

        long endTime = System.nanoTime();
        long netTime = endTime - startTime;

        System.out.printf("Time elapsed (ms): %.2f\n",
                (double) netTime / 1000000);
    }
}
