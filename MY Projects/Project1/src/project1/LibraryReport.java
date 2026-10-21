package project1;

import java.util.ArrayList;
import java.util.Map;

public class LibraryReport {

    void generateReport(ArrayList<Book> books,
                        Map<String, Book> issuedBooks) {

        int total = books.size();

        int issued = issuedBooks.size();

        int available = total - issued;

        System.out.println("\n===== LIBRARY REPORT =====");

        System.out.println("Total Books : " + total);
        System.out.println("Available Books : " + available);
        System.out.println("Issued Books : " + issued);
    }
}