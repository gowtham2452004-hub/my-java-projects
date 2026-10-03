package project1;

import java.time.LocalDate;
import java.util.Map;

public class DueDateReminder {

    void checkDueDateReminder(Map<String, LocalDate> dueDates) {

        LocalDate today = LocalDate.now();

        System.out.println("\n===== DUE DATE REMINDER =====");

        for (Map.Entry<String, LocalDate> entry : dueDates.entrySet()) {

            String bookId = entry.getKey();
            LocalDate dueDate = entry.getValue();

            if (today.isAfter(dueDate)) {

                System.out.println("Book ID : " + bookId);
                System.out.println("Status : Overdue");
                System.out.println("Due Date : " + dueDate);

            } else if (today.isEqual(dueDate)) {

                System.out.println("Book ID : " + bookId);
                System.out.println("Status : Due Today");
                System.out.println("Due Date : " + dueDate);

            } else {

                System.out.println("Book ID : " + bookId);
                System.out.println("Status : Not Due");
                System.out.println("Due Date : " + dueDate);
            }
        }
    }
}