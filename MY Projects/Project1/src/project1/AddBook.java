package project1;

import java.util.ArrayList;
import java.util.Scanner;

public class AddBook {

    void addBook(ArrayList<Book> books, Scanner sc) {

        System.out.print("Enter Book ID: ");
        String id = sc.nextLine();

        System.out.print("Enter Book Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Author Name: ");
        String author = sc.nextLine();

        Book b = new Book(id, name, author);

        books.add(b);

        System.out.println("Book added successfully");
    }
}