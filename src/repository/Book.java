package src.repository;

import src.Member;

import java.util.List;

public class Book {
    private String title;
    private String author;
    private int bookID;
    private boolean isOverdue;
    private boolean isAvailable;
    private List<Rating> ratings;
    private List<Member> reservationList;

    public String getTitle(){
        return title;
    }
    public String getAuthor(){
        return author;
    }
    public boolean getisOverdue(){
        return isOverdue;
    }
    public void setTitle(String title){
        this.title=title;
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
