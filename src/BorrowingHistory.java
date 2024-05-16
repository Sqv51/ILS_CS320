package src;

import java.util.Date;
import java.util.List;

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
