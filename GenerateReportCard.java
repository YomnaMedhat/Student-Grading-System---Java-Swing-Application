import java.util.List;

public class GenerateReportCard {

    public static String createReport(Students student) {
        StringBuilder sb = new StringBuilder();

        sb.append("===== REPORT CARD =====\n");
        sb.append("Student ID: ").append(student.getID()).append("\n");
        sb.append("Student Name: ").append(student.getName()).append("\n");
        sb.append("Enrolled Courses:\n");

        double totalGradePoints = 0;
        double totalCreditHours = 0;

        for (GradesEntry entry : student.getGradesEntries()) {
            Course course = entry.getCourse();
            double grade = entry.getGrade();
            double creditHours = course.getCreditHours();

            String letterGrade = convertToLetterGrade(grade);
            double gradePoint = convertToGPA(grade);

            sb.append(String.format("Course: %s | Grade: %.2f (%s) | Credit Hours: %.1f\n",
                    course.getCourseName(), grade, letterGrade, creditHours));

            totalGradePoints += gradePoint * creditHours;
            totalCreditHours += creditHours;
        }

        double GPA = (totalCreditHours == 0) ? 0 : totalGradePoints / totalCreditHours;
        sb.append(String.format("GPA: %.2f\n", GPA));
        sb.append("========================\n");

        return sb.toString();
    }


    public static String convertToLetterGrade(double grade) {
        if (grade >= 90) return "A";
        else if (grade >= 80) return "B";
        else if (grade >= 70) return "C";
        else if (grade >= 60) return "D";
        else return "F";
    }

    public static double convertToGPA(double grade) {
        if (grade >= 90) return 4.0;
        else if (grade >= 80) return 3.0;
        else if (grade >= 70) return 2.0;
        else if (grade >= 60) return 1.0;
        else return 0.0;
    }

    public Students findStudentByName(String name, List<Students> students) {
        for (Students s : students) {
            if (s.getName().equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    public void filterStudentsByCourse(String courseName, List<Students> students) {
        System.out.println("Students enrolled in course: " + courseName);
        for (Students student : students) {
            for (Course course : student.getCourses()) {
                if (course.getCourseName().equalsIgnoreCase(courseName)) {
                    System.out.println("- " + student.getName() + " (ID: " + student.getID() + ")");
                    break;
                }
            }
        }
    }
}