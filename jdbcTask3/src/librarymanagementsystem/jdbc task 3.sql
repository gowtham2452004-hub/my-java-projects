CREATE DATABASE IF NOT EXISTS library_management_db;

USE library_management_db;

CREATE TABLE IF NOT EXISTS books (
    book_id INT PRIMARY KEY AUTO_INCREMENT,
    book_name VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    available BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS members (
    member_id INT PRIMARY KEY AUTO_INCREMENT,
    member_name VARCHAR(100) NOT NULL,
    phone VARCHAR(15)
);

CREATE TABLE IF NOT EXISTS book_issues (
    issue_id INT PRIMARY KEY AUTO_INCREMENT,
    book_id INT NOT NULL,
    member_id INT NOT NULL,
    issue_date DATE NOT NULL,
    due_date DATE NOT NULL,
    return_date DATE DEFAULT NULL,
    fine_amount DECIMAL(10,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (book_id) REFERENCES books(book_id),
    FOREIGN KEY (member_id) REFERENCES members(member_id)
);

INSERT INTO books (book_name, author)
SELECT 'Java Programming', 'James Gosling'
WHERE NOT EXISTS (
    SELECT 1 FROM books WHERE book_name = 'Java Programming'
);

INSERT INTO books (book_name, author)
SELECT 'Clean Code', 'Robert C. Martin'
WHERE NOT EXISTS (
    SELECT 1 FROM books WHERE book_name = 'Clean Code'
);

INSERT INTO members (member_name, phone)
SELECT 'Gowtham', '6385225930'
WHERE NOT EXISTS (
    SELECT 1 FROM members WHERE member_name = 'Gowtham'
);

SHOW TABLES;
