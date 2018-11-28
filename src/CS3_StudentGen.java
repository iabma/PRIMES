import java.io.File;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/* PROBLEM 3 TEST FILE GENERATOR
   Java 1.8
   IntelliJ IDEA CE
   MacOS

   HOW TO RUN: If using a console, compile the file by entering "javac CS1.java" while in the
   correct directory, then entering "java CS1". The program will prompt you for input and output
   file names.

   REFERENCES:
   Java 8 API: https://docs.oracle.com/javase/8/docs/api/
   Name Databases: https://github.com/smashew/NameDatabases
 */
public class CS3_StudentGen {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter("CS3_TestData.txt");
        Random rand = new Random();
        File firstName = new File("FirstNames.txt");
        File lastName = new File("LastNames.txt");
        Scanner first = new Scanner(firstName);
        Scanner last = new Scanner(lastName);
        long numStudents;

        System.out.print("Number of students: ");
        numStudents = Long.parseLong(in.next());

        in.close();

        String[] firstNames = new String[19948];
        int firstNameCounter = 0;
        while (first.hasNextLine()) {
            firstNames[firstNameCounter] = first.nextLine();
            firstNameCounter++;
        }

        String[] lastNames = new String[88800];
        int lastNameCounter = 0;
        while (last.hasNextLine()) {
            lastNames[lastNameCounter] = last.nextLine();
            lastNameCounter++;
        }

        out.print(numStudents);
        for (long i = 0; i < numStudents; i++) {
            String id = "";
            for (int j = 0; j < 9; j++) {
                id += rand.nextInt(10);
            }

            out.printf("\n%s %s, %s %.2f %d",
                    id,
                    lastNames[rand.nextInt(88800)],
                    firstNames[rand.nextInt(19948)],
                    rand.nextDouble() * 5,
                    rand.nextInt(5) + 2018);
        }

        out.close();
    }
}
