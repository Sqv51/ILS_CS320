package src;

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
}
