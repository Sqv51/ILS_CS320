package src;

import java.sql.*;

public class SqlConnection {
    final static String url = "jdbc:mysql://localhost:3306/HospitalManagement";

    final static String user = "root";

    final static String password = "qwerty123";

    public static Connection getConnection(){
        Connection myConn = null;

        try {
            myConn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println("\n>>>ERROR! The Program Can NOT Connect to the Database <<<");
        }
        return myConn;
    }
}
