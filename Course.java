import java.util.ArrayList;
import java.util.List;

public class Course {
    private String CourseID;
    private String CourseName;
    private int CreditHours;
    private int Capacity;
    private List<Students> enrolledStudents;

    // Constructor now includes Capacity
    public Course(String CourseID, String CourseName, int CreditHours, int Capacity) {
        this.CourseID = CourseID;
        this.CourseName = CourseName;
        this.CreditHours = CreditHours;
        this.Capacity = Capacity;
        this.enrolledStudents = new ArrayList<>();
    }

    public String getCourseID() {
        return CourseID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public int getCreditHours() {
        return CreditHours;
    }

    public int getCapacity() {
        return Capacity;
    }

    public void setCourseID(String CourseID) {
        this.CourseID = CourseID;
    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    public void setCreditHours(int CreditHours) {
        this.CreditHours = CreditHours;
    }

    public void setCapacity(int Capacity) {
        this.Capacity = Capacity;
    }

    public boolean isFull() {
        return enrolledStudents.size() >= Capacity; // adjust based on your class structure
    }

    public List<Students> getEnrolledStudents() {
        return enrolledStudents;
    }

    @Override
    public String toString() {
        return "Course ID: " + CourseID + ", Course Name: " + CourseName +
                ", Credit Hours: " + CreditHours + ", Capacity: " + Capacity;
    }

    // Enroll student if course has capacity
    public boolean enrollStudent(Students student) {
        if (enrolledStudents.size() < Capacity) {
            enrolledStudents.add(student);
            return true;
        } else {
            return false;
        }
    }
}
