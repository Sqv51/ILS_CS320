package src.repository;

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
