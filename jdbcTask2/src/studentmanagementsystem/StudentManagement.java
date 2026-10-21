package studentmanagementsystem;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class StudentManagement {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try (Connection con = DBConnection.getConnection()) {

            System.out.println("Database Connected Successfully!");

            int choice;

            do {
                System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
                System.out.println("1. Student Registration");
                System.out.println("2. View Students");
                System.out.println("3. Course Enrollment");
                System.out.println("4. Attendance Management");
                System.out.println("5. Marks Entry");
                System.out.println("6. Result Generation");
                System.out.println("7. Update Student");
                System.out.println("8. Delete Student");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        registerStudent(con);
                        break;
                    case 2:
                        viewStudents(con);
                        break;
                    case 3:
                        enrollCourse(con);
                        break;
                    case 4:
                        markAttendance(con);
                        break;
                    case 5:
                        enterMarks(con);
                        break;
                    case 6:
                        generateResult(con);
                        break;
                    case 7:
                        updateStudent(con);
                        break;
                    case 8:
                        deleteStudent(con);
                        break;
                    case 0:
                        System.out.println("Thank you!");
                        break;
                    default:
                        System.out.println("Invalid choice!");
                }

            } while (choice != 0);

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    static void registerStudent(Connection con) throws SQLException {

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        System.out.print("Enter phone: ");
        String phone = sc.nextLine();

        String sql = "INSERT INTO students "
                   + "(student_name, email, phone) VALUES (?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Student registered successfully!");
            }
        }
    }
    
    static void viewStudents(Connection con) throws SQLException {

        String sql = "SELECT * FROM students";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nID\tName\t\tEmail\t\tPhone");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("student_id") + "\t" +
                    rs.getString("student_name") + "\t" +
                    rs.getString("email") + "\t" +
                    rs.getString("phone")
                );
            }
        }
    }

    static void enrollCourse(Connection con) throws SQLException {

        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter course ID: ");
        int courseId = Integer.parseInt(sc.nextLine());

        String check = "SELECT student_id FROM students WHERE student_id = ?";

        try (PreparedStatement ps = con.prepareStatement(check)) {
            ps.setInt(1, studentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Student ID not found!");
                    return;
                }
            }
        }

        String courseCheck = "SELECT course_id FROM courses WHERE course_id = ?";

        try (PreparedStatement ps = con.prepareStatement(courseCheck)) {
            ps.setInt(1, courseId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Course ID not found!");
                    return;
                }
            }
        }

        boolean oldAutoCommit = con.getAutoCommit();

        try {
            con.setAutoCommit(false);

            String sql = "INSERT INTO enrollments "
                       + "(student_id, course_id, enrollment_date) "
                       + "VALUES (?, ?, ?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, studentId);
                ps.setInt(2, courseId);
                ps.setDate(3, Date.valueOf(LocalDate.now()));
                ps.executeUpdate();
            }

            con.commit();
            System.out.println("Course enrollment successful!");

        } catch (SQLException e) {
            con.rollback();
            System.out.println("Enrollment failed: " + e.getMessage());

        } finally {
            con.setAutoCommit(oldAutoCommit);
        }
    }

    static void markAttendance(Connection con) throws SQLException {

        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter course ID: ");
        int courseId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter status (Present/Absent): ");
        String status = sc.nextLine();

        if (!status.equalsIgnoreCase("Present")
                && !status.equalsIgnoreCase("Absent")) {
            System.out.println("Invalid attendance status!");
            return;
        }

        String sql = "INSERT INTO attendance "
                   + "(student_id, course_id, attendance_date, status) "
                   + "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setDate(3, Date.valueOf(LocalDate.now()));

            String attendanceStatus =
                status.substring(0, 1).toUpperCase()
                + status.substring(1).toLowerCase();

            ps.setString(4, attendanceStatus);

            ps.executeUpdate();
            System.out.println("Attendance recorded successfully!");
        }
    }

    static void enterMarks(Connection con) throws SQLException {

        System.out.print("Enter student ID: ");
        int studentId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter course ID: ");
        int courseId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter marks obtained: ");
        double marks = Double.parseDouble(sc.nextLine());

        System.out.print("Enter maximum marks: ");
        double maxMarks = Double.parseDouble(sc.nextLine());

        if (maxMarks <= 0 || marks < 0 || marks > maxMarks) {
            System.out.println("Invalid marks!");
            return;
        }

        String sql = "INSERT INTO marks "
                   + "(student_id, course_id, marks_obtained, max_marks) "
                   + "VALUES (?, ?, ?, ?) "
                   + "ON DUPLICATE KEY UPDATE "
                   + "marks_obtained = VALUES(marks_obtained), "
                   + "max_marks = VALUES(max_marks)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);
            ps.setDouble(3, marks);
            ps.setDouble(4, maxMarks);

            ps.executeUpdate();
            System.out.println("Marks saved successfully!");
        }
    }

    static void generateResult(Connection con) throws SQLException {

        String sql =
            "SELECT s.student_id, s.student_name, c.course_name, "
          + "m.marks_obtained, m.max_marks "
          + "FROM marks m "
          + "JOIN students s ON m.student_id = s.student_id "
          + "JOIN courses c ON m.course_id = c.course_id "
          + "ORDER BY s.student_id";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\n===== STUDENT RESULTS =====");

            while (rs.next()) {
                double marks = rs.getDouble("marks_obtained");
                double max = rs.getDouble("max_marks");
                double percentage = marks * 100.0 / max;

                String result = percentage >= 40 ? "PASS" : "FAIL";

                System.out.println("Student ID: "
                    + rs.getInt("student_id"));
                System.out.println("Name: "
                    + rs.getString("student_name"));
                System.out.println("Course: "
                    + rs.getString("course_name"));
                System.out.println("Marks: " + marks + "/" + max);
                System.out.printf("Percentage: %.2f%%%n", percentage);
                System.out.println("Result: " + result);
                System.out.println("-------------------------");
            }
        }
    }

    static void updateStudent(Connection con) throws SQLException {

        System.out.print("Enter student ID: ");
        int id = Integer.parseInt(sc.nextLine());

        System.out.print("Enter new student name: ");
        String name = sc.nextLine();

        System.out.print("Enter new email: ");
        String email = sc.nextLine();

        System.out.print("Enter new phone: ");
        String phone = sc.nextLine();

        String sql = "UPDATE students SET student_name = ?, "
                   + "email = ?, phone = ? WHERE student_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();

            System.out.println(rows > 0
                ? "Student updated successfully!"
                : "Student ID not found!");
        }
    }

    static void deleteStudent(Connection con) throws SQLException {

        System.out.print("Enter student ID to delete: ");
        int id = Integer.parseInt(sc.nextLine());

        String sql = "DELETE FROM students WHERE student_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);

            int rows = ps.executeUpdate();

            System.out.println(rows > 0
                ? "Student deleted successfully!"
                : "Student ID not found!");
        } catch (SQLException e) {
            System.out.println(
                "Cannot delete student. Remove related enrollment, "
              + "attendance and marks records first."
            );
        }
    }
}
