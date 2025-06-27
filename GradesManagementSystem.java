import java.util.ArrayList;
import java.util.List;

public class GradesManagementSystem {
    private List<Students> students;
    private List<Course> Courses;

    public GradesManagementSystem() {
        students = new ArrayList<>();
        Courses = new ArrayList<>();
    }

    public void addStudent(int ID, String name) {
        students.add(new Students(ID, name));
        System.out.println("The added Student:\nID = " + ID + "\nName = " + name);
    }

    public void addCourse(String id, String name, int creditHours, int capacity) {
        Course course = new Course(id, name, creditHours, capacity);
        Courses.add(course);
        System.out.println("Course added: " + course);
    }

    public List<Students> filterStudentsByName(String name) {
        List<Students> found = new ArrayList<>();
        for (Students student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                found.add(student);
            }
        }
        return found;
    }

    public List<Course> filterCoursesByCode(String courseId) {
        List<Course> found = new ArrayList<>();
        for (Course course : Courses) {
            if (course.getCourseID().equalsIgnoreCase(courseId)) {
                found.add(course);
            }
        }
        return found;
    }

    public void assigningGrades(int studentID, String courseID, double grade) {
        boolean assigned = false;

        // Find the student and course
        Students student = null;
        Course course = null;
        for (Students s : students) {
            if (s.getID() == studentID) {
                student = s;
                break;
            }
        }

        for (Course c : Courses) {
            if (c.getCourseID().equalsIgnoreCase(courseID)) {
                course = c;
                break;
            }
        }

        // If both student and course are found
        if (student != null && course != null) {
            // Check if the student is enrolled in the course
            if (course.enrollStudent(student)) {
                student.gradeAssign(course, grade);
                System.out.println("Assigned grade " + grade + " to student " + student.getName() + " for course " + course.getCourseName());
                assigned = true;
            } else {
                System.out.println("Student " + student.getName() + " could not be enrolled in " + course.getCourseName() + " due to capacity.");
            }
        }

        // If not assigned, print error message
        if (!assigned) {
            System.out.println("Student or course ID not found.");
        }
    }

    public double getStudentGPA(int studentID) {
        for (Students student : students) {
            if (student.getID() == studentID) {
                return student.calculateGPA();
            }
        }
        System.out.println("Student ID not found.");
        return 0;
    }

    public List<Students> getAllStudents() {
        return students;
    }

    public List<Course> getAllCourses() {
        return Courses;
    }
}
