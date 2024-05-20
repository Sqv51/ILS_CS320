package src.repository;

import src.Member;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private int bookID;

    public Book(String bookName, String author, String genre, int year,String description) {
        this.bookName = bookName;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.rating =0.0;
        this.description = description;
        this.isAvailable = true;
        this.isOverdue = false;
        this.reservationList = new ArrayList<>();
    }

    private String bookName;
    private String author;
    private String genre;
    private int year;
    private double rating;
    private String description;
    private boolean isOverdue;
    private boolean isAvailable;
    private List<Rating> ratings;
    private List<Member> reservationList;

    public Book() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public boolean isOverdue() {
        return isOverdue;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setOverdue(boolean overdue) {
        isOverdue = overdue;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
    public String getBookName(){
        return bookName;
    }
    public String getAuthor(){
        return author;
    }
    public boolean getisOverdue(){
        return isOverdue;
    }
    public void setBookName(String bookName){
        this.bookName = bookName;
    }
    protected void setAuthor(String author){
        this.author=author;
    }
    protected void setisOverdue(boolean isOverdue){
        this.isOverdue=isOverdue;
    }
    protected List<Rating> getRatings(){
    	return this.ratings;
    }
    public void addRating(Rating rating) {
    	this.ratings.add(rating);
    }
    protected double getAverageRating() {
    	if(this.ratings.isEmpty()) {
    	    return 0;
    	}else {
    	    double sum = 0;
    	    for (Rating rating : this.ratings) {
    		sum += rating.getScore();
	    }
    	    return sum/this.ratings.size();
    	}
    }
    protected void reserve(Member member) {
    	if(!reservationList.contains(member)) {
    	    reservationList.add(member);
    	}
    }
    protected void notifyMembers() {
    	for (Member member : reservationList) {
	    member.notifyWhenAvailable(this);
	}
    	reservationList.clear();
    }
    protected void setAvailable(boolean available) {
    	if(this.isAvailable) {
    	    notifyMembers();
    	}
    }

    public boolean getisAvailable() {
        return isAvailable;
    }

    public int getBookID() {
        return bookID;
    }
	
    public void setBookID(int bookID) {
    	this.bookID = bookID;
    }
}
