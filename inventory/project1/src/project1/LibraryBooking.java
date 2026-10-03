package project1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;

public class LibraryBooking {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ArrayList<Book> books = new ArrayList<>();

        Map<String, Book> issuedBooks = new HashMap<>();

        Map<String, LocalDate> dueDates = new HashMap<>();

        AddBook add = new AddBook();
        SearchBook search = new SearchBook();
        IssueBook issue = new IssueBook();
        ReturnBook returnBook = new ReturnBook();
        BookAvailability availability = new BookAvailability();
        Librarian librarian = new Librarian();
        FineCalculator fine = new FineCalculator();
        DueDateReminder reminder = new DueDateReminder();
        LibraryReport report = new LibraryReport();

        while (true) {

            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Issue Book");
            System.out.println("4. Return Book");
            System.out.println("5. Book Availability");
            System.out.println("6. Librarian Approval");
            System.out.println("7. Fine Calculator");
            System.out.println("8. Due Date Reminder");
            System.out.println("9. Generate Report");
            System.out.println("10. Exit");

            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    add.addBook(books, sc);
                    break;

                case 2:
                    search.searchBook(books, sc);
                    break;

                case 3:
                    issue.issueBook(books, issuedBooks, dueDates, sc);
                    break;

                case 4:
                    returnBook.returnBook(issuedBooks, sc);
                    break;

                case 5:
                    availability.showAvailability(books);
                    break;

                case 6:
                    librarian.approveBook(sc);
                    break;

                case 7:
                    fine.calculateFine(sc);
                    break;

                case 8:
                    reminder.checkDueDateReminder(dueDates);
                    break;

                case 9:
                    report.generateReport(books, issuedBooks);
                    break;

                case 10:
                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}