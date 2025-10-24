package Banking_System;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/banking_system";
    private static final String USER = "your_mysql_username";
    private static final String PASSWORD = "your_mysql_password";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver Loaded Successfully!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load JDBC Driver!", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
