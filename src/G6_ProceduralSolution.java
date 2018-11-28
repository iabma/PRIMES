/* MATH PROBLEM 6 PROCEDURAL ANSWER
   Java 1.8
   IntelliJ IDEA CE
   MacOS

   HOW TO RUN: If using a console, compile the file by entering "javac CS1.java" while in the
   correct directory, then entering "java CS1". The program will prompt you for input and output
   file names.

   REFERENCES:
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
 */
public class G6_ProceduralSolution {
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
                            System.out.printf("a %d d %d c %d b %d\n", perfectPowers[i],
                                    perfectPowers[j], perfectPowers[k], perfectPowers[l]);
                        }
                    }
                }
            }
            System.out.printf("%.1f%%\n", (double) (i + 1) / perfectPowers.length * 100);
        }
    }
}
