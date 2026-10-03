package project1;

import java.util.ArrayList;

public class BookAvailability {

    void showAvailability(ArrayList<Book> books) {

        System.out.println("\n===== BOOK AVAILABILITY =====");

        for (Book b : books) {

            System.out.println("Book ID : " + b.id);
            System.out.println("Book Name : " + b.name);
            System.out.println("Author : " + b.author);

            if (b.available) {
                System.out.println("Status : Available");
            } else {
                System.out.println("Status : Issued");
            }

            System.out.println();
        }
    }
}