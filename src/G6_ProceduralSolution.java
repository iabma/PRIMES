/* MATH PROBLEM 6 PROCEDURAL ANSWER
   Java 1.8
   IntelliJ IDEA CE
   MacOS

   HOW TO RUN: If using a console, compile the file by entering "javac G6_ProceduralSolution.java"
   while in the correct directory, then entering "java G6_ProceduralSolution".

   REFERENCES:
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
 */
public class G6_ProceduralSolution {
    /*
    Rudimentary perfect power generator, using inputted base and power limits.
     */
    private static int[] genPerfectPowers(int extent, int powerLim) {
        int[] holder = new int[extent * powerLim];
        for (int i = 2; i <= powerLim; i++) {
            for (int j = 1; j <= extent; j++) {
                holder[extent * (i - 1) + (j - 1)] = (int) Math.pow(j, i);
            }
        }
        return holder;
    }

    /*
    Creates a small array with perfect powers up to 100^5, then iterates through every possible
    scenario, checking if the "good matrix" determinant equals 2019. If so, the matrix numbers
    are printed out.
     */
    public static void main(String[] args) {
        int[] perfectPowers = genPerfectPowers(100, 5);

        System.out.println("Array generated. Crunching numbers...");

        double completion = 0;
        for (int a : perfectPowers) {
            for (int b : perfectPowers) {
                for (int c : perfectPowers) {
                    for (int d : perfectPowers) {
                        if (a * d - c * b == 2019) {
                            System.out.printf("a| %d b| %d c| %d d| %d\n", a, b, c, d);
                        }
                    }
                }
            }
            completion++;
            System.out.printf("%.1f%%\n", completion / perfectPowers.length * 100);
        }
    }
}
