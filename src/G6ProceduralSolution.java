public class G6ProceduralSolution {
    public static int[] genPerfectPowers(int extent, int powerLim) {
        int[] holder = new int[extent * powerLim];
        for (int i = 2; i <= powerLim; i++) {
            for (int j = 1; j <= extent; j++) {
                holder[extent * (i - 1) + (j - 1)] = (int) Math.pow(j, i);
            }
        }
        return holder;
    }

    public static void main(String[] args) {
        int[] perfectPowers = genPerfectPowers(100, 5);

        System.out.println("Array generated. Crunching numbers...");

        for (int i = 0; i < perfectPowers.length; i++) {
            for (int j = 0; j < perfectPowers.length; j++) {
                for (int k = 0; k < perfectPowers.length; k++) {
                    for (int l = 0; l < perfectPowers.length; l++) {
                        if (perfectPowers[i] * perfectPowers[j] - perfectPowers[k] *
                                perfectPowers[l] == 2019 || perfectPowers[i] * perfectPowers[j] +
                                perfectPowers[k] * perfectPowers[l] == 2019) {
                            System.out.printf("a %d b %d c %d d %d\n", perfectPowers[i],
                                    perfectPowers[j], perfectPowers[k], perfectPowers[l]);
                        }
                    }
                }
            }
            System.out.printf("%.1f%%\n", (double) (i + 1) / perfectPowers.length * 100);
        }
    }
}
