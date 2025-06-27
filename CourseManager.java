import java.util.ArrayList;

public class CourseManager {
    private ArrayList<Course> courses = new ArrayList<>();

    public void addCourse(Course course) {
        courses.add(course);
    }

    public Course getCourseById(String id) {
        for (Course course : courses) {
            if (course.getCourseID().equals(id)) {
                return course;
            }
        }
        return null;
    }

    public boolean hasCourse(String id) {
        for (Course course : courses) {
            if (course.getCourseID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }
}
