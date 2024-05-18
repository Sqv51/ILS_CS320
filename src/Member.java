package src;

import src.repository.Book;

import java.util.List;

public class Member {
    private String name;
    private int memberId;
    private List<Book> borrowedBooks;
    private double penaltyAmount;

    protected String getName(){
        return name;
    }
    protected int getMemberId(){
        return memberId;
    }
    protected List getBorrowedBooks(){
        return borrowedBooks;
    }
    protected double getPenaltyAmount(){
        return penaltyAmount;
    }
    protected void setName(String name){
        this.name=name;
    }
    protected void setMemberId(int memberId){
        this.memberId=memberId;
    }
    protected void setBorrowedBooks(List borrowedBooks){
        this.borrowedBooks=borrowedBooks;
    }
    protected void setPenaltyAmount(double penaltyAmount){
        this.penaltyAmount = penaltyAmount;
    }
    protected void rate(Book book, int score) {
    	Rating rating = new Rating(this, score);
    	book.addRating(rating);
    }
    public void notifyWhenAvailable(Book book) {
    	System.out.println("The book \"" + book.getTitle() + "\" is now available for borrowing.");
    }
}
