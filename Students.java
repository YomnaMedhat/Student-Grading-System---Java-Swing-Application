import java.util.ArrayList;
import java.util.List;

public class Students {
    private int ID;
    private String name;
    private List<Course> coursesList;
    private ArrayList<GradesEntry> gradesEntries;
    private List<Course> EnrolledCourses;

    public Students (int ID, String name) {
        this.ID = ID;
        this.name = name;
        this.coursesList = new ArrayList<>();
        this.gradesEntries = new ArrayList<>();
        this.EnrolledCourses = new ArrayList<>();
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getEnrolledCourses() {
        return EnrolledCourses;
    }

    public List<Course> getCourses() {
        return coursesList;
    }

    public ArrayList<GradesEntry> getGradesEntries() {
        return gradesEntries;
    }

    // Ensure the course is added to EnrolledCourses when enrolling
    public void enrollCourse(Course course) {
        if (!EnrolledCourses.contains(course)) {
            EnrolledCourses.add(course);  // Add to enrolled courses
            System.out.println("Successfully enrolled in: " + course.getCourseName());
        } else {
            System.out.println("Already enrolled in: " + course.getCourseName());
        }
    }

    public void gradeAssign(Course course, double grade){
        // Make sure the student is enrolled in the course
        if (EnrolledCourses.contains(course)){
            gradesEntries.add(new GradesEntry(course, grade));
        } else {
            System.out.println("Student is not enrolled in " + course.getCourseName());
        }
    }

    public double calculateGPA(){
        double totalPoints = 0;
        int totalCreditHours = 0;

        for (GradesEntry gradesEntry : gradesEntries){
            double grade = gradesEntry.getGrade();
            int creditHours = gradesEntry.getCourse().getCreditHours();
            totalPoints += grade * creditHours;
            totalCreditHours += creditHours;
        }

        if (totalCreditHours != 0){
            return totalPoints / totalCreditHours;
        } else {
            return 0;  // Return 0 if no credits assigned
        }
    }

    public double convertToGPA(double grade) {
        if (grade >= 93) return 4.0;
        else if (grade >= 90) return 3.7;
        else if (grade >= 87) return 3.3;
        else if (grade >= 83) return 3.0;
        else if (grade >= 80) return 2.7;
        else if (grade >= 77) return 2.3;
        else if (grade >= 73) return 2.0;
        else if (grade >= 70) return 1.7;
        else if (grade >= 67) return 1.3;
        else if (grade >= 65) return 1.0;
        else return 0.0;
    }

    @Override
    public String toString() {
        return "ID: " + ID + "\nName: " + name;
    }
}
