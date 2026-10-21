CREATE DATABASE bankingDB;
USE bankingDB;
CREATE TABLE customers (customer_id INT PRIMARY KEY, name VARCHAR(50), city VARCHAR(50));

CREATE TABLE accounts (account_id INT PRIMARY KEY, customer_id INT, account_type VARCHAR(20), balance DECIMAL(10,2), FOREIGN KEY (customer_id) REFERENCES customers(customer_id));

INSERT INTO customers (customer_id, name, city)
VALUES
(1, 'Sathivel', 'Chennai'),
(2, 'Gowtham', 'Madurai'),
(3, 'Karthikeyan', 'Salem'),
(4, 'JayaPrakash', 'Chennai'),
(5, 'Ravikumar', 'Pondicherry'),
(6, 'Arun', 'Chennai'),
(7, 'Kavitha', 'Salem'),
(8, 'Manoj', 'Madurai'),
(9, 'Divya', 'Coimbatore'),
(10, 'Joseph', 'Chennai');
INSERT INTO accounts (account_id, customer_id, account_type, balance)
VALUES
(101, 1, 'Savings', 25000),
(102, 2, 'Current', 45000),
(103, 3, 'Savings', 36000),
(104, 4, 'Current', 20000),
(105, 5, 'Savings', 8000),
(106, 6, 'Current', 25000),
(107, 7, 'Savings', 45000),
(108, 8, 'Current', 15000),
(109, 9, 'Savings', 35000),
(110, 10, 'Current', 50000);

SELECT * FROM accounts WHERE balance > 20000;

SELECT * FROM customers WHERE city = 'Chennai';

SELECT * FROM accounts WHERE balance BETWEEN 20000 AND 50000;

SELECT * FROM customers WHERE name LIKE 'J%';

SELECT * FROM accounts WHERE account_type IN ('Savings', 'Current');

SELECT * FROM accounts WHERE account_type <> 'Savings';

SELECT * FROM customers WHERE name LIKE '%a%';

SELECT * FROM accounts WHERE balance <= 30000;

SELECT * FROM customers WHERE city <> 'Madurai';

SELECT * FROM accounts WHERE balance NOT BETWEEN 10000 AND 40000;

SELECT * FROM customers WHERE name LIKE '%i';

SELECT * FROM accounts WHERE balance = 50000;

SELECT * FROM customers WHERE city IN ('Chennai', 'Salem');

SELECT * FROM accounts WHERE balance > 10000 AND balance < 40000;

SELECT * FROM accounts WHERE account_type NOT IN ('Current');

SELECT * FROM accounts  ORDER BY BALANCE DESC;

SELECT * FROM customers ORDER BY name ASC;

SELECT * FROM accounts ORDER BY account_type ASC, balance DESC;

SELECT SUM(balance) AS total_balance FROM accounts;

SELECT (balance) AS average_balance FROM accounts;

SELECT MAX(balance) AS maximum_balance FROM accounts;

SELECT MIN(balance) AS minimum_balance FROM accounts;

SELECT COUNT(*) AS total_customers FROM customers;

SELECT account_type, SUM(balance) AS total_balance FROM accounts GROUP BY account_type;

SELECT account_type, AVG(balance) AS average_balance FROM accounts GROUP BY account_type;

SELECT account_type, AVG(balance) AS average_balance FROM accounts GROUP BY account_type HAVING AVG(balance) > 20000;

SELECT customer_id, COUNT(*) AS total_accounts FROM accounts GROUP BY customer_id;

SELECT customer_id, COUNT(*) AS total_accounts FROM accounts GROUP BY customer_id HAVING COUNT(*) > 1;

SELECT c.name, a.balance FROM customers c JOIN accounts a ON c.customer_id = a.customer_id;

SELECT c.*, a.* FROM customers c LEFT JOIN accounts a ON c.customer_id = a.customer_id;

SELECT a.*, c.name, c.city FROM accounts a LEFT JOIN customers c ON a.customer_id = c.customer_id;

SELECT c.name, a.account_type FROM customers c JOIN accounts a ON c.customer_id = a.customer_id WHERE a.balance > 20000;

SELECT c.customer_id, c.name, SUM(a.balance) AS total_balance FROM customers c JOIN accounts a ON c.customer_id = a.customer_id GROUP BY c.customer_id, c.name;

SELECT c.name, a.balance FROM customers c JOIN accounts a ON c.customer_id = a.customer_id ORDER BY a.balance ASC;

SELECT c.city, COUNT(a.account_id) AS total_accounts FROM customers c LEFT JOIN accounts a ON c.custo.mer_id = a.customer_id GROUP BY c.city;

SELECT * FROM accounts WHERE balance > ( SELECT AVG(balance) FROM accounts);

SELECT DISTINCT c.* FROM customers c JOIN accounts a ON c.customer_id = a.customer_id;

SELECT * FROM customers c WHERE NOT EXISTS ( SELECT 1 FROM accounts a WHERE a.customer_id = c.customer_id);

SELECT * FROM accounts WHERE balance = ( SELECT MAX(balance) FROM accounts);

SELECT c.customer_id, c.name, SUM(a.balance) AS total_balance FROM customers c JOIN accounts a ON c.customer_id = a.customer_id GROUP BY c.customer_id, c.name HAVING SUM(a.balance) > 40000;

-- Railway 

CREATE DATABASE railway;
USE railway;

CREATE TABLE passengers (
    passenger_id INT PRIMARY KEY,
    name VARCHAR(50),
    age INT,
    gender VARCHAR(10)
);

CREATE TABLE trains (
    train_id INT PRIMARY KEY,
    train_name VARCHAR(50),
    starting_station VARCHAR(50),
    designation VARCHAR(50)
);

CREATE TABLE bookings (
    booking_id INT PRIMARY KEY,
    passenger_id INT,
    train_id INT,
    fare DECIMAL(10,2),
    status VARCHAR(20),
    FOREIGN KEY (passenger_id)
        REFERENCES passengers(passenger_id),
    FOREIGN KEY (train_id)
        REFERENCES trains(train_id)
);

INSERT INTO passengers VALUES
(1, 'Arun', 25, 'Male'),
(2, 'Anu', 22, 'Female'),
(3, 'Bala', 30, 'Male'),
(4, 'Ajay', 28, 'Male'),
(5, 'Priya', 24, 'Female'),
(6, 'Asha', 27, 'Female'),
(7, 'Rahul', 32, 'Male'),
(8, 'Amit', 29, 'Male');

INSERT INTO trains VALUES
(101, 'Chennai Express', 'Chennai', 'Bangalore'),
(102, 'Kerala Express', 'Kochi', 'Chennai'),
(103, 'Vande Bharat', 'Chennai', 'Coimbatore'),
(104, 'Malabar Express', 'Kozhikode', 'Trivandrum'),
(105, 'Rajdhani Express', 'Delhi', 'Mumbai');

INSERT INTO bookings VALUES
(1001, 1, 101, 450.00, 'Confirmed'),
(1002, 2, 102, 300.00, 'Pending'),
(1003, 3, 103, 500.00, 'Cancelled'),
(1004, 4, 101, 250.00, 'Confirmed'),
(1005, 5, 104, 400.00, 'Pending'),
(1006, 6, 105, 650.00, 'Confirmed'),
(1007, 7, 103, 350.00, 'Cancelled'),
(1008, 8, 102, 480.00, 'Pending');

SELECT * FROM bookings WHERE fare > 400;

SELECT * FROM bookings WHERE status <> 'Confirmed';

SELECT * FROM trains WHERE starting_station = 'Chennai';

SELECT * FROM passengers WHERE name LIKE 'A%';

SELECT t.train_name, p.name AS passenger_name FROM bookings b JOIN passengers p ON b.passenger_id = p.passenger_id JOIN trains t ON b.train_id = t.train_id ORDER BY t.train_name;

SELECT t.train_name, COUNT(b.booking_id) AS total_bookings FROM trains t LEFT JOIN bookings b ON t.train_id = b.train_id GROUP BY t.train_id, t.train_name ORDER BY total_bookings DESC;

SELECT t.train_name, COALESCE(SUM(b.fare), 0) AS total_fare FROM trains t LEFT JOIN bookings b ON t.train_id = b.train_id GROUP BY t.train_id, t.train_name ORDER BY total_fare DESC;

SELECT * FROM bookings WHERE fare = (SELECT MAX(fare) FROM bookings);

SELECT * FROM trains WHERE train_id IN (SELECT train_id FROM bookings GROUP BY train_id HAVING COUNT(*) > 1);

-- Employee management 

CREATE DATABASE employee_management;
USE employee_management;

CREATE TABLE Employee (
    emp_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_name VARCHAR(50),
    department VARCHAR(30),
    salary DECIMAL(10,2),
    city VARCHAR(30),
    joining_date DATE
);

INSERT INTO Employee
(emp_name, department, salary, city, joining_date)
VALUES
('John', 'IT', 60000, 'Chennai', '2022-01-15'),
('David', 'HR', 45000, 'Bangalore', '2021-03-10'),
('Smith', 'IT', 70000, 'Chennai', '2020-07-12'),
('Mary', 'Finance', 55000, 'Mumbai', '2023-01-20'),
('James', 'HR', 48000, 'Delhi', '2022-05-05'),
('Linda', 'Finance', 65000, 'Mumbai', '2021-08-18');

CREATE TABLE Students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(50),
    department VARCHAR(30),
    marks INT
);

INSERT INTO Students
(student_name, department, marks)
VALUES
('Arun', 'CSE', 85),
('Priya', 'IT', 90),
('Rahul', 'ECE', 78),
('Anu', 'CSE', 92),
('Kiran', 'IT', 75);

CREATE TABLE Customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(50),
    city VARCHAR(30)
);

INSERT INTO Customers (customer_name, city)
VALUES
('Ravi', 'Chennai'),
('Meena', 'Bangalore'),
('Kumar', 'Mumbai'),
('Divya', 'Delhi'),
('Ajay', 'Chennai');

CREATE TABLE Bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    passenger_id INT,
    train_id INT,
    fare DECIMAL(10,2),
    status VARCHAR(20)
);

INSERT INTO Bookings
(passenger_id, train_id, fare, status)
VALUES
(1, 101, 450, 'Confirmed'),
(2, 102, 300, 'Pending'),
(3, 103, 500, 'Cancelled'),
(4, 101, 250, 'Confirmed'),
(5, 104, 400, 'Pending');

SELECT department, COUNT(*) AS total_employees FROM Employee GROUP BY department;

SELECT department, AVG(salary) AS average_salary FROM Employee GROUP BY department;

SELECT department, COUNT(*) AS total_employees FROM Employee GROUP BY department HAVING COUNT(*) > 1;

SELECT department, MAX(salary) AS highest_salary FROM Employee GROUP BY department;

SELECT department, MIN(salary) AS lowest_salary FROM Employee GROUP BY department;

SELECT department, AVG(salary) AS average_salary FROM Employee GROUP BY department HAVING AVG(salary) > 50000;

SELECT department, SUM(salary) AS total_salary FROM Employee GROUP BY department;

SELECT * FROM Employee ORDER BY salary DESC;

SELECT * FROM Employee ORDER BY department ASC, salary DESC;

SELECT city, COUNT(*) AS total_employees FROM Employee GROUP BY city HAVING COUNT(*) > 1;

SELECT city, SUM(salary) AS total_salary FROM Employee GROUP BY city;

SELECT department, SUM(salary) AS total_salary FROM Employee GROUP BY department ORDER BY total_salary DESC;

SELECT department, COUNT(*) AS total_employees FROM Employee WHERE salary > 50000 GROUP BY department;

SELECT department, MAX(salary) - MIN(salary) AS salary_difference FROM Employee GROUP BY department;

SELECT department, MAX(salary) - MIN(salary) AS salary_difference FROM Employee GROUP BY department;

SELECT c.customer_name, SUM(o.amount) AS total_amount FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name;

SELECT c.customer_name, COUNT(o.order_id) AS total_orders FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name HAVING COUNT(o.order_id) > 3;

SELECT c.customer_name, AVG(o.amount) AS average_amount FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name;

SELECT c.customer_name, MAX(o.amount) AS highest_order FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name;

SELECT c.customer_name, SUM(o.amount) AS total_purchase FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name ORDER BY total_purchase DESC;

SELECT c.customer_name, SUM(o.amount) AS total_purchase FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name HAVING SUM(o.amount) > 10000;

SELECT c.customer_name, COUNT(o.order_id) AS total_ordersm FROM Customers c LEFT JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name;

SELECT c.customer_name, SUM(o.amount) AS total_spent FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name ORDER BY total_spent DESC LIMIT 1;

SELECT c.customer_name, COUNT(o.order_id) AS total_orders FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id ORDER BY total_orders DESC LIMIT 1;

SELECT c.customer_name, AVG(o.amount) AS average_amount FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name HAVING AVG(o.amount) > 2000;

SELECT c.customer_name, SUM(o.amount) AS total_purchase FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name ORDER BY total_purchase DESC LIMIT 5;

SELECT c.customer_name, MIN(o.amount) AS minimum_order FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name;

SELECT c.customer_name, SUM(o.amount) AS total_purchase FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name HAVING SUM(o.amount) > 5000;

SELECT c.customer_name, COUNT(o.order_id) AS total_orders, COALESCE(SUM(o.amount), 0) AS total_purchase FROM Customers c LEFT JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name;

SELECT c.customer_name, COUNT(o.order_id) AS total_orders, SUM(o.amount) AS total_purchase FROM Customers c JOIN Orders o ON c.customer_id = o.customer_id GROUP BY c.customer_id, c.customer_name HAVING COUNT(o.order_id) > 2 AND SUM(o.amount) > 8000;

SELECT department, AVG(marks) AS average_marks FROM Students GROUP BY department;

SELECT department, AVG(marks) AS average_marks FROM Students GROUP BY department HAVING AVG(marks) > 75;

SELECT department, MAX(marks) AS highest_marks FROM Students GROUP BY department;

SELECT department, COUNT(*) AS total_students FROM Students GROUP BY department;

SELECT department, COUNT(*) AS total_students FROM Students GROUP BY department HAVING COUNT(*) > 5;

SELECT department, AVG(marks) AS average_marks FROM Students GROUP BY department ORDER BY average_marks DESC;

SELECT department, AVG(marks) AS average_marks FROM Students GROUP BY department ORDER BY average_marks DESC LIMIT 3;

SELECT department, AVG(marks) AS average_marks FROM Students GROUP BY department HAVING AVG(marks) BETWEEN 70 AND 90;

SELECT department, SUM(marks) AS total_marks FROM Students GROUP BY department;

SELECT department, COUNT(*) AS total_students FROM Students GROUP BY department ORDER BY total_students DESC;

SELECT department, MIN(marks) AS lowest_marks FROM Students GROUP BY department;

SELECT department, MAX(marks) AS highest_marks FROM Students GROUP BY department HAVING MAX(marks) > 90;

SELECT department, COUNT(*) AS students_above_80 FROM Students WHERE marks > 80 GROUP BY department;

SELECT department, COUNT(*) AS students_above_75 FROM Students WHERE marks > 75 GROUP BY department HAVING COUNT(*) > 3;

SELECT department, MAX(marks) AS highest_marks FROM Students GROUP BY department ORDER BY highest_marks DESC;


   


