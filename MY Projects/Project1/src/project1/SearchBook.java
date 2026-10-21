package project1;

import java.util.ArrayList;
import java.util.Scanner;

public class SearchBook {

    void searchBook(ArrayList<Book> books, Scanner sc) {

        System.out.print("Enter Book ID: ");

        String id = sc.nextLine();

        for (Book b : books) {

            if (b.id.equals(id)) {

                System.out.println("Book Found");

                b.display();

                return;
            }
        }

        System.out.println("Book not found");
    }
}