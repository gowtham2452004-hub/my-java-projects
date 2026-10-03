package project1;

public class Book {

    String id;
    String name;
    String author;
    boolean available;

    public Book(String id, String name, String author) {

        this.id = id;
        this.name = name;
        this.author = author;
        this.available = true;
    }

    void display() {

        System.out.println("Book ID : " + id);
        System.out.println("Book Name : " + name);
        System.out.println("Author : " + author);

        if (available) {
            System.out.println("Status : Available");
        } else {
            System.out.println("Status : Issued");
        }
    }
}
