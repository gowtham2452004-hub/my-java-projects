package project1;

import java.util.Scanner;

public class FineCalculator {

    int calculateFine(Scanner sc) {

        System.out.print("Enter late days: ");

        int days = sc.nextInt();

        int fine = days * 5;

        System.out.println("Late Days : " + days);
        System.out.println("Fine : Rs." + fine);

        return fine;
    }
}