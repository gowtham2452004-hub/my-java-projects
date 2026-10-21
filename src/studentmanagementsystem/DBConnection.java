package studentmanagementsystem;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/student_management_db";
        String username = "root";
        String password = "gowtham";

        return DriverManager.getConnection(url, username, password);
    }
}

