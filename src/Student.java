public class Student {
    private int graduationYear;
    public int modifiedGradYear;
    public double GPA;
    public String info;

    public Student(String input) {
        graduationYear = Integer.parseInt(input.substring(input.length() - 4));
        GPA = Double.parseDouble(input.substring(input.length() - 9,
                input.length() - 6));
        info = input;
    }

    public void setModifiedGradYear(int earliest) {
        modifiedGradYear = graduationYear - earliest;
    }

    @Override
    public String toString() {
        return info;
    }
}
