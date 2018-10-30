public class Student {
    private int GPA;
    private String info;

    public Student(String input) {
        info = input;
    }

    public void setInfo(String info) {
        this.info = info;
        GPA = Math.abs((int) (Double.parseDouble(info.substring(info.length() - 9,
                info.length() - 5)) * 100) - 500);
    }

    public void setGPA(int GPA) {
        this.GPA = GPA;
    }

    public int getGPA() {
        return GPA;
    }

    @Override
    public String toString() {
        return info;
    }
}
