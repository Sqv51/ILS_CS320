package src;

import java.util.List;
import java.util.Date;

public class Model {

    public class Book {

    private String title;
    private String author;
    private boolean isOverdue;

    protected String getTitle(){
        return title;
    }
    protected String getAuthor(){
        return author;
    }
    protected boolean getisOverdue(){
        return isOverdue;
    }
    protected void setTitle(String title){
        this.title=title;
    }
    protected void setAuthor(String author){
        this.author=author;
    }
    protected void setisOverdue(boolean isOverdue){
        this.isOverdue=isOverdue;
    }
}

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
}

public class BorrowingHistory {
    private Member member;
    private List<Book> borrowedBooks;
    private Date startDate;
    private Date endDate;

    protected Member getMember(){
        return member;
    }
    protected List getBorrowedBooks(){
        return borrowedBooks;
    }
    protected Date getStartDate(){
        return startDate;
    }
    protected Date getEndDate(){
        return startDate;
    }
    protected void setMember(Member member){
        this.member=member;
    }
    protected void setBorrowedBooks(List borrowedBooks){
        this.borrowedBooks=borrowedBooks;
    }
    protected void setStartDate(Date startdate){
        this.startDate=startdate;
    }
    protected void setEndDate(Date endDate){
        this.endDate=endDate;
    }
  }
}
