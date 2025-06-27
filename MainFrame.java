import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MainFrame extends JFrame {
    private ArrayList<Students> students;
    private ArrayList<Course> courses;
    private JTextArea reportCardTextArea;


    private void showWelcomeDialog() {
        JDialog dialog = new JDialog(this, "Welcome", true);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 153, 204));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("🎓 Welcome to Student Grading System!");
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        JButton startButton = new JButton("Start");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> dialog.dispose());

        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(startButton);

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

    private void showDashboard() {
        int totalStudents = students.size();
        int totalCourses = courses.size();
        int totalEnrollments = 0;
        int fullCourses = 0;

        for (Course course : courses) {
            totalEnrollments += course.getEnrolledStudents().size();
            if (course.isFull()) {
                fullCourses++;
            }
        }

        double averageStudentsPerCourse = totalCourses == 0 ? 0 : (double) totalEnrollments / totalCourses;

        String dashboardMessage = String.format(
                "📊 Dashboard Summary:\n" +
                        "👨‍🎓 Total Students: %d\n" +
                        "📚 Total Courses: %d\n" +
                        "✅ Total Enrollments: %d\n" +
                        "🔒 Full Courses: %d\n" +
                        "📈 Average Students per Course: %.2f",
                totalStudents, totalCourses, totalEnrollments, fullCourses, averageStudentsPerCourse
        );

        JOptionPane.showMessageDialog(null, dashboardMessage, "System Dashboard", JOptionPane.INFORMATION_MESSAGE);
    }

    public MainFrame() {
        students = new ArrayList<>();
        courses = new ArrayList<>();

        Speech.speak(" ");

        Speech.speak("Welcome to the Student Grading System");
        showWelcomeDialog();

        setTitle("📘 Student Management Dashboard");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Student Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(new Color(33, 150, 243));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(4, 4, 12, 12));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnAddStudent = createStyledButton("➕ Add Student");
        JButton btnAddCourse = createStyledButton("📚 Add Course");
        JButton btnEnroll = createStyledButton("📝 Enroll Student");
        JButton btnAssignGrade = createStyledButton("✏ Assign Grade");
        JButton btnViewStudents = createStyledButton("👥 View Students");
        JButton btnViewCourses = createStyledButton("📖 View Courses");
        JButton btnCalculateGPA = createStyledButton("📊 Calculate GPA");
        JButton btnDisplayEnrolledCourses = createStyledButton("📈Display Enrolled Courses");
        JButton btnSearchStudent = createStyledButton("🔍 Search Student");
        JButton reportCardButton = createStyledButton("👥Display Report Card");
        JButton btnSearchCourse = createStyledButton("🔎 Search Course");
        JButton btnExit = createStyledButton("❌ Exit");
        JButton btnDashboard = createStyledButton("✅ Dashboard");

        buttonPanel.add(btnAddStudent);
        buttonPanel.add(btnAddCourse);
        buttonPanel.add(btnEnroll);
        buttonPanel.add(btnAssignGrade);
        buttonPanel.add(btnViewStudents);
        buttonPanel.add(btnViewCourses);
        buttonPanel.add(btnCalculateGPA);
        buttonPanel.add(btnDisplayEnrolledCourses);
        buttonPanel.add(btnSearchStudent);
        buttonPanel.add(btnSearchCourse);
        buttonPanel.add(reportCardButton);
        buttonPanel.add(btnDashboard);
        buttonPanel.add(btnExit);

        reportCardTextArea = new JTextArea(15, 40);
        reportCardTextArea.setEditable(false);
        reportCardTextArea.setLineWrap(true);
        reportCardTextArea.setWrapStyleWord(true);

        add(buttonPanel, BorderLayout.CENTER);

        btnAddStudent.addActionListener(e -> openAddStudentWindow());
        btnAddCourse.addActionListener(e -> openAddCourseWindow());
        btnEnroll.addActionListener(e -> openEnrollStudentWindow());
        btnAssignGrade.addActionListener(e -> openAssignGradeWindow(null, null));
        btnViewStudents.addActionListener(e -> openViewStudentsWindow());
        btnViewCourses.addActionListener(e -> openViewCoursesWindow());
        btnCalculateGPA.addActionListener(e -> openCalculateGPAWindow());
        btnDisplayEnrolledCourses.addActionListener(e -> openDisplayEnrolledCoursesWindow());
        btnSearchStudent.addActionListener(e -> openSearchStudentWindow());
        reportCardButton.addActionListener(e -> displayReportCard());
        btnSearchCourse.addActionListener(e -> openSearchCourseWindow());
        btnExit.addActionListener(e -> System.exit(0));

        btnDashboard.addActionListener(e -> showDashboard());

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(33, 150, 243));
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(30, 136, 229)),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)));
        return button;
    }

    private void openSearchStudentWindow() {
        String input = JOptionPane.showInputDialog(this, "Enter Student ID:");
        if (input != null && !input.trim().isEmpty()) {
            try {
                int id = Integer.parseInt(input.trim());
                for (Students student : students) {
                    if (student.getID() == id) {
                        JOptionPane.showMessageDialog(this, student.toString(), "Student Found", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                Speech.speak("Student not found");
                JOptionPane.showMessageDialog(this, "❌ Student not found.", "Search Result", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException ex) {
                Speech.speak("Please enter a valid numeric ID");
                JOptionPane.showMessageDialog(this, "⚠️ Please enter a valid numeric ID.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openSearchCourseWindow() {
        String input = JOptionPane.showInputDialog(this, "Enter Course ID:");
        if (input != null && !input.trim().isEmpty()) {
            for (Course course : courses) {
                if (course.getCourseID().equalsIgnoreCase(input.trim())) {
                    JOptionPane.showMessageDialog(this, course.toString(), "Course Found", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }
            Speech.speak("Course not found");
            JOptionPane.showMessageDialog(this, "❌ Course not found.", "Search Result", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openAddStudentWindow() {
        JFrame frame = new JFrame("Add Student");
        frame.setSize(300, 200);
        frame.setLayout(new GridLayout(3, 2));
        frame.setLocationRelativeTo(null);

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JButton addButton = new JButton("Add");

        frame.add(new JLabel("Student ID:"));
        frame.add(idField);
        frame.add(new JLabel("Student Name:"));
        frame.add(nameField);
        frame.add(new JLabel());
        frame.add(addButton);

        addButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();

                if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
                    Speech.speak("Invalid name. Only letters and spaces are allowed.");
                    JOptionPane.showMessageDialog(frame, "❌ Invalid name. Only letters and spaces are allowed.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean idExists = students.stream().anyMatch(s -> s.getID() == id);
                if (idExists) {
                    Speech.speak("A student with this ID already exists");
                    JOptionPane.showMessageDialog(frame, "⚠ A student with this ID already exists.", "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Students student = new Students(id, name);
                students.add(student);
                JOptionPane.showMessageDialog(frame, "✅ Student added successfully.");
                frame.dispose();

                int result = JOptionPane.showConfirmDialog(null, "Enroll this student in a course now?", "Next Step", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    if (courses.isEmpty()) {
                        Speech.speak("No courses available to enroll. Please add courses first.");
                        JOptionPane.showMessageDialog(null, "⚠ No courses available to enroll. Please add courses first.",
                                "No Courses", JOptionPane.WARNING_MESSAGE);
                    } else {
                        openEnrollStudentWindow();
                    }
                }

            } catch (NumberFormatException ex) {
                Speech.speak("Invalid ID. Please enter a numeric value.");
                JOptionPane.showMessageDialog(frame, "❌ Invalid ID. Please enter a numeric value.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }

    private void openAddCourseWindow() {
        JFrame frame = new JFrame("Add Course");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2, 10, 10));
        frame.setLocationRelativeTo(null);

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField creditField = new JTextField();
        JTextField capacityField = new JTextField();
        JButton addButton = new JButton("Add Course");

        frame.add(new JLabel("Course ID:"));
        frame.add(idField);
        frame.add(new JLabel("Course Name:"));
        frame.add(nameField);
        frame.add(new JLabel("Credit Hours:"));
        frame.add(creditField);
        frame.add(new JLabel("Capacity:"));
        frame.add(capacityField);
        frame.add(new JLabel());
        frame.add(addButton);

        addButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                int credits = Integer.parseInt(creditField.getText().trim());
                int capacity = Integer.parseInt(capacityField.getText().trim());

                if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
                    Speech.speak("Invalid course name. Only letters and spaces are allowed.");
                    JOptionPane.showMessageDialog(frame, "❌ Invalid course name. Only letters and spaces are allowed.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (credits <= 0 || capacity <= 0) {
                    Speech.speak("Credit hours and capacity must be positive numbers.");
                    JOptionPane.showMessageDialog(frame, "❌ Credit hours and capacity must be positive numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                boolean idExists = courses.stream().anyMatch(c -> c.getCourseID().equals(id));
                if (idExists) {
                    Speech.speak("A course with this ID already exists");
                    JOptionPane.showMessageDialog(frame, "⚠ A course with this ID already exists.", "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean nameExists = courses.stream().anyMatch(c -> c.getCourseName().equalsIgnoreCase(name));
                if (nameExists) {
                    Speech.speak("A course with this name already exists");
                    JOptionPane.showMessageDialog(frame, "⚠ A course with this name already exists.", "Duplicate Name", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                courses.add(new Course(id, name, credits, capacity));
                JOptionPane.showMessageDialog(frame, "✅ Course added successfully.");
                frame.dispose();

            } catch (NumberFormatException ex) {
                Speech.speak("Invalid input. Credit hours and capacity must be numbers.");
                JOptionPane.showMessageDialog(frame, "❌ Invalid input. Credit hours and capacity must be numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        frame.setVisible(true);
    }

    private void openEnrollStudentWindow() {
        JFrame frame = new JFrame("Enroll Student in Course");
        frame.setSize(350, 200);
        frame.setLayout(new GridLayout(3, 2, 10, 10));
        frame.setLocationRelativeTo(null);

        JComboBox<Students> studentBox = new JComboBox<>(students.toArray(new Students[0]));
        JComboBox<Course> courseBox = new JComboBox<>(courses.toArray(new Course[0]));
        JButton enrollButton = new JButton("Enroll");

        frame.add(new JLabel("Select Student:"));
        frame.add(studentBox);
        frame.add(new JLabel("Select Course:"));
        frame.add(courseBox);
        frame.add(new JLabel());
        frame.add(enrollButton);

        if (students.isEmpty()) {
            Speech.speak("No students available. Please add a student first.");
            JOptionPane.showMessageDialog(null, "⚠ No students available. Please add a student first.", "No Students", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (courses.isEmpty()) {
            Speech.speak("No courses available. Please add a course first.");
            JOptionPane.showMessageDialog(null, "⚠ No courses available. Please add a course first.", "No Courses", JOptionPane.WARNING_MESSAGE);
            return;
        }

        enrollButton.addActionListener(e -> {
            Students student = (Students) studentBox.getSelectedItem();
            Course course = (Course) courseBox.getSelectedItem();
            if (student != null && course != null) {
                if (course.isFull()) {
                    Speech.speak("The course is already full. Cannot enroll this student.");
                    JOptionPane.showMessageDialog(frame, "⚠ The course is already full. Cannot enroll this student.");
                } else {
                    boolean enrolled = course.enrollStudent(student);
                    if (enrolled) {
                        student.enrollCourse(course);
                        JOptionPane.showMessageDialog(frame, "✅ Student enrolled successfully.");
                        frame.dispose();
                    } else {
                        Speech.speak("Enrollment failed");
                        JOptionPane.showMessageDialog(frame, "❌ Enrollment failed.");
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    private void displayReportCard() {
        if (students == null || students.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No students available to display report cards.");
            return;
        }

        JFrame frame = new JFrame("Report Card");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JComboBox<Students> studentBox = new JComboBox<>(students.toArray(new Students[0]));
        JTextArea reportTextArea = new JTextArea();
        reportTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportTextArea);

        JButton showButton = new JButton("Show Report");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Student:"));
        topPanel.add(studentBox);
        topPanel.add(showButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        GenerateReportCard reportGenerator = new GenerateReportCard();

        showButton.addActionListener(e -> {
            Students selectedStudent = (Students) studentBox.getSelectedItem();
            if (selectedStudent != null) {
                String reportContent = reportGenerator.createReport(selectedStudent);

                reportTextArea.setText(reportContent);

                String filename = selectedStudent.getName().replaceAll("\\s+", "_") + "_ReportCard.txt";
                try (FileWriter writer = new FileWriter(filename)) {
                    writer.write(reportContent);
                    System.out.println("Report card saved to " + filename);
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error saving report card: " + ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }

    private void openDisplayEnrolledCoursesWindow() {
        JFrame frame = new JFrame("Enrolled Courses");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JComboBox<Students> studentBox = new JComboBox<>(students.toArray(new Students[0]));
        JButton showButton = new JButton("Show Courses");

        // Table columns
        String[] columnNames = {"Course ID", "Course Name", "Credit Hours", "Capacity"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        // Style the table
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(173, 216, 230)); // Light blue
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(224, 255, 255)); // Light cyan

        JScrollPane scrollPane = new JScrollPane(table);

        // Panel at the top
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Select Student:"));
        topPanel.add(studentBox);
        topPanel.add(showButton);

        // Add to frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Button action
        showButton.addActionListener(e -> {
            Students selectedStudent = (Students) studentBox.getSelectedItem();
            tableModel.setRowCount(0); // Clear previous rows

            if (selectedStudent != null) {
                List<Course> enrolledCourses = selectedStudent.getEnrolledCourses();
                if (enrolledCourses.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No courses enrolled.");
                    Speech.speak("No courses enrolled");
                } else {
                    for (Course c : enrolledCourses) {
                        Object[] row = {c.getCourseID(), c.getCourseName(), c.getCreditHours(), c.getCapacity()};
                        tableModel.addRow(row);
                    }
                }
            }
        });

        frame.setVisible(true);
    }

    private void openAssignGradeWindow(Students preStudent, Course preCourse) {
        JFrame frame = new JFrame("Assign Grade");
        frame.setSize(350, 200);
        frame.setLayout(new GridLayout(4, 2));
        frame.setLocationRelativeTo(null);

        JComboBox<Students> studentBox = new JComboBox<>(students.toArray(new Students[0]));
        JComboBox<Course> courseBox = new JComboBox<>(courses.toArray(new Course[0]));
        JTextField gradeField = new JTextField();
        JButton assignButton = new JButton("Assign");

        if (preStudent != null) studentBox.setSelectedItem(preStudent);
        if (preCourse != null) courseBox.setSelectedItem(preCourse);

        frame.add(new JLabel("Select Student:"));
        frame.add(studentBox);
        frame.add(new JLabel("Select Course:"));
        frame.add(courseBox);
        frame.add(new JLabel("Grade:"));
        frame.add(gradeField);
        frame.add(new JLabel());
        frame.add(assignButton);

        assignButton.addActionListener(e -> {
            if (students.isEmpty() || courses.isEmpty()) {
                Speech.speak("No students or courses found. Please add and enroll first.");
                JOptionPane.showMessageDialog(null, "⚠ No students or courses found. Please add and enroll first.",
                        "Missing Data", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                Students selectedStudent = (Students) studentBox.getSelectedItem();
                Course selectedCourse = (Course) courseBox.getSelectedItem();

                if (selectedStudent == null || selectedCourse == null) {
                    Speech.speak("Please select both student and course");
                    JOptionPane.showMessageDialog(frame, "Please select both student and course.");
                    return;
                }

                double grade = Double.parseDouble(gradeField.getText().trim());
                selectedStudent.gradeAssign(selectedCourse, grade);

                JOptionPane.showMessageDialog(frame, "Grade assigned.");
                frame.dispose();
            } catch (NumberFormatException ex) {
                Speech.speak("Invalid grade input Please enter a valid number");
                JOptionPane.showMessageDialog(frame, "⚠ Invalid grade input. Please enter a valid number.");
            }
        });

        frame.setVisible(true);
    }

    private void openViewStudentsWindow() {
        JFrame frame = new JFrame("View Students");
        frame.setSize(500, 300);

        if (students.isEmpty()) {
            Speech.speak("No students found");
            JOptionPane.showMessageDialog(null, "⚠ No students found.");
            return;
        }

        String[] columnNames = { "ID", "Name" };

        String[][] data = new String[students.size()][2];
        for (int i = 0; i < students.size(); i++) {
            Students student = students.get(i);
            data[i][0] = String.valueOf(student.getID());
            data[i][1] = student.getName();
        }

        JTable table = new JTable(data, columnNames);
        table.setEnabled(false);
        table.setFillsViewportHeight(true);

        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(173, 216, 230));
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(224, 255, 255));

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void openViewCoursesWindow() {
        JFrame frame = new JFrame("View Courses");
        frame.setSize(500, 300);

        if (courses.isEmpty()) {
            Speech.speak("No courses found.");
            JOptionPane.showMessageDialog(null, "⚠ No courses found.");
            return;
        }

        // Column names for the table
        String[] columnNames = {"Course ID", "Course Name", "Credit Hours", "Capacity"};

        // Table data from the courses list
        Object[][] data = new Object[courses.size()][4];
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            data[i][0] = course.getCourseID();            // assuming getId()
            data[i][1] = course.getCourseName();          // assuming getName()
            data[i][2] = course.getCreditHours();   // assuming getCreditHours()
            data[i][3] = course.getCapacity();      // assuming getCapacity()
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane);

        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(173, 216, 230));
        table.setGridColor(Color.LIGHT_GRAY);
        table.setShowGrid(true);
        table.setSelectionBackground(new Color(224, 255, 255));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    

    private void openCalculateGPAWindow() {
        JFrame frame = new JFrame("Calculate GPA");
        frame.setSize(300, 150);
        frame.setLayout(new GridLayout(2, 2));
        frame.setLocationRelativeTo(null);

        JComboBox<Students> studentBox = new JComboBox<>(students.toArray(new Students[0]));
        JButton calculateButton = new JButton("Calculate");

        frame.add(new JLabel("Select Student:"));
        frame.add(studentBox);
        frame.add(new JLabel());
        frame.add(calculateButton);

        calculateButton.addActionListener(e -> {
            Students student = (Students) studentBox.getSelectedItem();
            if (student != null) {
                if (student.getGradesEntries().isEmpty()) {
                    Speech.speak("No grades found for this student. Please assign grades first.");
                    JOptionPane.showMessageDialog(frame, "⚠ No grades found for this student. Please assign grades first.");
                } else {
                    double gpa = student.calculateGPA();
                    String letterGrade = student.convertToGPA(gpa) == 4.0 ? "A"
                            : student.convertToGPA(gpa) == 3.7 ? "A-"
                            : student.convertToGPA(gpa) == 3.3 ? "B+"
                            : student.convertToGPA(gpa) == 3.0 ? "B"
                            : student.convertToGPA(gpa) == 2.7 ? "B-"
                            : student.convertToGPA(gpa) == 2.3 ? "C+"
                            : student.convertToGPA(gpa) == 2.0 ? "C"
                            : student.convertToGPA(gpa) == 1.7 ? "C-"
                            : student.convertToGPA(gpa) == 1.3 ? "D+"
                            : student.convertToGPA(gpa) == 1.0 ? "D"
                            : "F";  // Map GPA to letter grade

                    JOptionPane.showMessageDialog(frame,
                            "GPA: " + String.format("%.2f", gpa) +
                                    "\nLetter Grade: " + letterGrade);
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}