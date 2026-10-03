package project1;

import java.util.Scanner;

public class Librarian {

    boolean approveBook(Scanner sc) {

        System.out.print("Librarian approve the issue? (yes/no): ");

        String answer = sc.nextLine();

        if (answer.equalsIgnoreCase("yes")) {

            System.out.println("Librarian approved the book");

            return true;

        } else {

            System.out.println("Librarian rejected the book");

            return false;
        }
    }
}