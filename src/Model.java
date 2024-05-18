package src;

import src.repository.Book;

import java.sql.*;

public class Model {
    Connection conn;
    public Model(){
        conn = SqlConnection.getConnection();
    }
    public String signIn(int ID, String password, String type) throws SQLException {

        if (type.equals("Student")){
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Student WHERE studentID = ? AND password =?");
            ps.setInt(1,ID);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return "" + rs.getInt(1) +"," + rs.getString(2);
            }
        } else if (type.equals("Staff")) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Staff WHERE staffID = ? AND password =?");
            ps.setInt(1,ID);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return "" + rs.getInt(1) +"," + rs.getString(2);
            }
        }
        return "Invalid Input";
    }
    public Book getBookByID(int ID){
        return null;
    }

}
