package librarymanagementsystem;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class LibraryManagement {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        try (Connection con = DBConnection.getConnection()) {

            System.out.println("Database Connected Successfully!");

            int choice;

            do {
                System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
                System.out.println("1. Add Book");
                System.out.println("2. Search Books");
                System.out.println("3. View All Books");
                System.out.println("4. Issue Book");
                System.out.println("5. Return Book");
                System.out.println("6. Fine Calculation");
                System.out.println("7. Add Member");
                System.out.println("0. Exit");
                System.out.print("Enter your choice: ");

                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        addBook(con);
                        break;
                    case 2:
                        searchBooks(con);
                        break;
                    case 3:
                        viewBooks(con);
                        break;
                    case 4:
                        issueBook(con);
                        break;
                    case 5:
                        returnBook(con);
                        break;
                    case 6:
                        calculateFine(con);
                        break;
                    case 7:
                        addMember(con);
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

    static void addBook(Connection con) throws SQLException {

        System.out.print("Enter book name: ");
        String name = sc.nextLine();

        System.out.print("Enter author name: ");
        String author = sc.nextLine();

        String sql =
            "INSERT INTO books (book_name, author) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, author);
            ps.executeUpdate();

            System.out.println("Book added successfully!");
        }
    }


    static void searchBooks(Connection con) throws SQLException {

        System.out.print("Enter book name to search: ");
        String name = sc.nextLine();

        String sql =
            "SELECT * FROM books WHERE book_name LIKE ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;

                    System.out.println("Book ID: "
                        + rs.getInt("book_id"));
                    System.out.println("Name: "
                        + rs.getString("book_name"));
                    System.out.println("Author: "
                        + rs.getString("author"));
                    System.out.println("Available: "
                        + rs.getBoolean("available"));
                    System.out.println("------------------");
                }

                if (!found) {
                    System.out.println("No books found.");
                }
            }
        }
    }
    
    static void viewBooks(Connection con) throws SQLException {

        String sql = "SELECT * FROM books ORDER BY book_id";

        try (PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            System.out.println("\nID\tBook Name\tAuthor\t\tAvailable");

            while (rs.next()) {
                System.out.println(
                    rs.getInt("book_id") + "\t" +
                    rs.getString("book_name") + "\t" +
                    rs.getString("author") + "\t" +
                    rs.getBoolean("available")
                );
            }
        }
    }

    static void issueBook(Connection con) throws SQLException {

        System.out.print("Enter book ID: ");
        int bookId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter member ID: ");
        int memberId = Integer.parseInt(sc.nextLine());

        boolean oldAutoCommit = con.getAutoCommit();

        try {
            con.setAutoCommit(false);

            String checkBook =
                "SELECT available FROM books WHERE book_id = ? FOR UPDATE";

            try (PreparedStatement ps = con.prepareStatement(checkBook)) {
                ps.setInt(1, bookId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Book ID not found.");
                    }

                    if (!rs.getBoolean("available")) {
                        throw new SQLException("Book is already issued.");
                    }
                }
            }

            String checkMember =
                "SELECT member_id FROM members WHERE member_id = ?";

            try (PreparedStatement ps = con.prepareStatement(checkMember)) {
                ps.setInt(1, memberId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException("Member ID not found.");
                    }
                }
            }

            String issueSql =
                "INSERT INTO book_issues "
                + "(book_id, member_id, issue_date, due_date) "
                + "VALUES (?, ?, ?, ?)";

            LocalDate today = LocalDate.now();

            try (PreparedStatement ps = con.prepareStatement(issueSql)) {
                ps.setInt(1, bookId);
                ps.setInt(2, memberId);
                ps.setDate(3, Date.valueOf(today));
                ps.setDate(4, Date.valueOf(today.plusDays(14)));
                ps.executeUpdate();
            }

            String updateBook =
                "UPDATE books SET available = FALSE WHERE book_id = ?";

            try (PreparedStatement ps = con.prepareStatement(updateBook)) {
                ps.setInt(1, bookId);
                ps.executeUpdate();
            }

            con.commit();

            System.out.println("Book issued successfully!");
            System.out.println("Due date: " + today.plusDays(14));

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException rollbackError) {
                e.addSuppressed(rollbackError);
            }

            System.out.println("Issue failed: " + e.getMessage());

        } finally {
            con.setAutoCommit(oldAutoCommit);
        }
    }

    
    static void returnBook(Connection con) throws SQLException {

        System.out.print("Enter issue ID: ");
        int issueId = Integer.parseInt(sc.nextLine());

        boolean oldAutoCommit = con.getAutoCommit();

        try {
            con.setAutoCommit(false);

            String sql =
                "SELECT book_id, due_date FROM book_issues "
                + "WHERE issue_id = ? AND return_date IS NULL FOR UPDATE";

            int bookId;
            LocalDate dueDate;

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, issueId);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        throw new SQLException(
                            "Issue ID not found or book already returned."
                        );
                    }

                    bookId = rs.getInt("book_id");
                    dueDate = rs.getDate("due_date").toLocalDate();
                }
            }

            LocalDate today = LocalDate.now();

            long lateDays = Math.max(
                0, ChronoUnit.DAYS.between(dueDate, today)
            );

            double fine = lateDays * 5.0;

            String updateIssue =
                "UPDATE book_issues SET return_date = ?, fine_amount = ? "
                + "WHERE issue_id = ? AND return_date IS NULL";

            try (PreparedStatement ps = con.prepareStatement(updateIssue)) {
                ps.setDate(1, Date.valueOf(today));
                ps.setDouble(2, fine);
                ps.setInt(3, issueId);

                if (ps.executeUpdate() != 1) {
                    throw new SQLException("Return could not be recorded.");
                }
            }

            String updateBook =
                "UPDATE books SET available = TRUE WHERE book_id = ?";

            try (PreparedStatement ps = con.prepareStatement(updateBook)) {
                ps.setInt(1, bookId);
                ps.executeUpdate();
            }

            con.commit();

            System.out.println("Book returned successfully!");
            System.out.println("Late days: " + lateDays);
            System.out.println("Fine amount: Rs. " + fine);

        } catch (SQLException e) {
            try {
                con.rollback();
            } catch (SQLException rollbackError) {
                e.addSuppressed(rollbackError);
            }

            System.out.println("Return failed: " + e.getMessage());

        } finally {
            con.setAutoCommit(oldAutoCommit);
        }
    }

    static void calculateFine(Connection con) throws SQLException {

        System.out.print("Enter issue ID: ");
        int issueId = Integer.parseInt(sc.nextLine());

        String sql =
            "SELECT due_date, return_date, fine_amount "
            + "FROM book_issues WHERE issue_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, issueId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.out.println("Issue ID not found.");
                    return;
                }

                LocalDate dueDate =
                    rs.getDate("due_date").toLocalDate();

                Date returned = rs.getDate("return_date");

                LocalDate checkDate = returned == null
                    ? LocalDate.now()
                    : returned.toLocalDate();

                long lateDays = Math.max(
                    0, ChronoUnit.DAYS.between(dueDate, checkDate)
                );

                double fine;

                if (returned == null) {
                    fine = lateDays * 5.0;
                } else {
                    fine = rs.getDouble("fine_amount");
                }

                System.out.println("Due date: " + dueDate);
                System.out.println("Late days: " + lateDays);
                System.out.println("Fine amount: Rs. " + fine);
            }
        }
    }

    static void addMember(Connection con) throws SQLException {

        System.out.print("Enter member name: ");
        String name = sc.nextLine();

        System.out.print("Enter phone number: ");
        String phone = sc.nextLine();

        String sql =
            "INSERT INTO members (member_name, phone) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, phone);
            ps.executeUpdate();

            System.out.println("Member added successfully!");
        }
        
    }

static void viewIssueDetails(Connection con) throws SQLException {

    String sql =
        "SELECT bi.issue_id, b.book_name, m.member_name, "
        + "bi.issue_date, bi.due_date, bi.return_date "
        + "FROM book_issues bi "
        + "JOIN books b ON bi.book_id = b.book_id "
        + "JOIN members m ON bi.member_id = m.member_id "
        + "ORDER BY bi.issue_id";

    try (PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            System.out.println("Issue ID: " + rs.getInt("issue_id"));
            System.out.println("Book: " + rs.getString("book_name"));
            System.out.println("Member: " + rs.getString("member_name"));
            System.out.println("Issue date: " + rs.getDate("issue_date"));
            System.out.println("Due date: " + rs.getDate("due_date"));
            System.out.println("Return date: " + rs.getDate("return_date"));
            System.out.println("-------------------------");
        }
    }
}
}
