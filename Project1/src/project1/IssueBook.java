package project1;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.time.LocalDate;

public class IssueBook {

    void issueBook(ArrayList<Book> books,
                   Map<String, Book> issuedBooks,
                   Map<String, LocalDate> dueDates,
                   Scanner sc) {

        System.out.print("Enter Book ID: ");

        String id = sc.nextLine();

        for (Book b : books) {

            if (b.id.equals(id)) {

                if (b.available) {

                    b.available = false;

                    issuedBooks.put(id, b);

                    LocalDate issueDate = LocalDate.now();

                    LocalDate dueDate = issueDate.plusDays(7);

                    dueDates.put(id, dueDate);

                    System.out.println("Book issued successfully");
                    System.out.println("Issue Date : " + issueDate);
                    System.out.println("Due Date : " + dueDate);

                } else {

                    System.out.println("Book is already issued");
                }

                return;
            }
        }

        System.out.println("Book not found");
    }
}