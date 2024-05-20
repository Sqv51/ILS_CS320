package src;

import src.repository.Book;
import src.repository.Rating;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Model {
    private Connection conn;
    private int availableBookID;
    public Model() throws SQLException {
        conn = SqlConnection.getConnection();
        PreparedStatement psMaxBookID = conn.prepareStatement("SELECT max(bookID) FROM Book");
        ResultSet rsMaxBookID = psMaxBookID.executeQuery();
        rsMaxBookID.next();
        availableBookID = rsMaxBookID.getInt(1) + 1;
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
    public Book getBookByID(int bookID){
    	Book book = null;
    	try {
    		PreparedStatement ps = conn.prepareStatement("SELECT * FROM Members WHERE memberID = ?");
    		ps.setInt(1, bookID);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			book = new Book();
    			book.setBookID(rs.getInt("memberID"));
                book.setBookName("title");
    		}
    	}catch (SQLException e) {
            e.printStackTrace();
        }
        return book;
    }
    
    public Member getMemberByID(int memberID) {
    	Member member = null;
    	try {
    		PreparedStatement ps = conn.prepareStatement("SELECT * FROM Members WHERE memberID = ?");
    		ps.setInt(1, memberID);
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			member = new Member();
    			member.setMemberId(rs.getInt("memberID"));
                member.setName(rs.getString("name"));
    		}
    	}catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }


    public void addBook(Book book) {
        try {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Book (bookID, bookName, author, genre, year, rating, description) VALUES (?,?,?,?,?,?,?)");
            ps.setInt(1,availableBookID);
            ps.setString(2, book.getBookName());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getGenre());
            ps.setInt(5,book.getYear());
            ps.setDouble(6,book.getRating());
            ps.setString(7, book.getDescription());

            ps.executeUpdate();
            availableBookID++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateBook(Book book) {
        try {
            PreparedStatement ps = conn.prepareStatement("UPDATE Books SET title = ?, author = ?, isOverdue = ?, isAvailable = ? WHERE bookID = ?");
            ps.setString(1, book.getBookName());
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

    public void addRating(Book book, Rating rating) {
    	try {
    		PreparedStatement ps = conn.prepareStatement("INSERT INTO Ratings (bookID, memberID, rating) VALUES (?,?,?)");
    		ps.setInt(1, book.getBookID());
    		ps.setInt(2, rating.getMember().getMemberId());
    		ps.setInt(3, rating.getScore());
    		ps.executeUpdate();		
    		
        } catch (SQLException e) {
            e.printStackTrace();
    	}
    }
    
    public List<Rating> getRatings(Book book){
    	List<Rating> ratings = new ArrayList<>();
    	try {
    		PreparedStatement ps = conn.prepareStatement("SELECT memberID, score from Ratings where bookID = ?");
    		ps.setInt(1, book.getBookID());
    		ResultSet rs = ps.executeQuery();
    		
    		while(rs.next()) {
    			int memberID = rs.getInt("memberID");
                int score = rs.getInt("score");
                Member member = getMemberByID(memberID);
                Rating rating = new Rating(member, score);
                ratings.add(rating);                
    		}
    		
    	}catch (SQLException e) {
            e.printStackTrace();
        }
        return ratings;
    }
    
    public double getAverageRating(int bookID) {
    	double averageRating = 0;
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT average rating from Ratings where bookID = ?");
            ps.setInt(1, bookID);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                averageRating = rs.getDouble("averageRating");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return averageRating;
    }

}
