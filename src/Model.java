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


    public void addBook(Book book) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Books (title, author, isOverdue, isAvailable) VALUES (?,?,?,?)");
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setBoolean(3, book.getisOverdue());
            ps.setBoolean(4, book.getisAvailable());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE Books SET title = ?, author = ?, isOverdue = ?, isAvailable = ? WHERE bookID = ?");
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setBoolean(3, book.getisOverdue());
            ps.setBoolean(4, book.getisAvailable());
            ps.setInt(5, book.getBookID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean[] Notification(int ID){
        //sql query to check if the user has any book that is overdue (if return date is passed)
        boolean result[]=new boolean[2];
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Books WHERE userID = ? AND returnDate < CURDATE()");
            ps.setInt(1,ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                result[0]=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //check if the user has any book reserved and available
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Books WHERE userID = ? AND isAvailable = true");
            ps.setInt(1,ID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                result[1]=true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return new boolean[2];

    }

}
