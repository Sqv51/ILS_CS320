package src.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlConnection {
    private Connection connection;
    private Statement statement;

    public SqlConnection(String url, String username, String password) throws SQLException {
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new SQLException("Unable to connect to the database.", e);
        }
    }

    public ResultSet executeQuery(String query) throws SQLException {
        try {
            return statement.executeQuery(query);
        } catch (SQLException e) {
            throw new SQLException("Unable to execute the query.", e);
        }
    }

    public void close() throws SQLException {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new SQLException("Unable to close the connection.", e);
        }
    }
}