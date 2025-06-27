public class GradesEntry {
    private Course course;
    private double grade;

    public GradesEntry(Course course, double grade){
        this.course = course;
        this.grade = grade;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }
}

