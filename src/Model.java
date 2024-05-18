package src;

import src.repository.Book;

import java.sql.*;

public class Model {
    Connection conn;
    public Model(){
        conn = SqlConnection.getConnection();
    }
    public String signIn(int ID, String password) throws SQLException {

            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE userID = ? AND password =?");
            ps.setInt(1,ID);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                boolean isStaff = rs.getBoolean(5);
                return "" + rs.getInt(1) +"," + rs.getString(2) + "," +isStaff;
            }


        return "Invalid Input";
    }
    public Book getBookByID(int ID){
        return null;
    }

}
