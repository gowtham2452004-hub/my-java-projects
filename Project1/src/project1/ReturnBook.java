package project1;

import java.util.Map;
import java.util.Scanner;

public class ReturnBook {

    void returnBook(Map<String, Book> issuedBooks, Scanner sc) {

        System.out.print("Enter Book ID: ");
        String id = sc.nextLine();

        if (issuedBooks.containsKey(id)) {

            Book b = issuedBooks.get(id);

            b.available = true;

            issuedBooks.remove(id);

            System.out.println("Book returned successfully");

        } else {

            System.out.println("Book is not issued");

        }
    }
}