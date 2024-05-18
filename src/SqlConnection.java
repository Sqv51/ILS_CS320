package src;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ils_db";
    private static final String USER = "root";
    private static final String PASSWORD = "ab123456";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
           //print error
            e.toString();
            System.out.println("Error: " + e);
            return null;
        }
    }
}