import java.util.Comparator;

/*
A simple class to test the sorting algorithm
 */
public class Student implements Comparable<Student> {
    public static final Comparator<Student> BY_GPA = new ByGpa();
    public static final Comparator<Student> BY_HEIGHT = new ByHeight();

    public String name;
    public Double gpa;
    public Double height;

    private static class ByGpa implements Comparator<Student> {
        public int compare(Student u, Student v) {
            return u.gpa.compareTo(v.gpa);
        }
    }

    private static class ByHeight implements Comparator<Student> {
        public int compare(Student u, Student v) {
            return u.height.compareTo(v.height);
        }
    }

    public int compareTo(Student s) {
        return name.compareTo(s.name);
    }

    public void print() {
        System.out.println(name + " " + gpa + " " + height + "cm");
    }
}
