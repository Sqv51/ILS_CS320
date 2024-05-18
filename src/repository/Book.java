package src.repository;

public class Book {
    private String title;
    private String author;
    private boolean isOverdue;
    private boolean isAvailable;
    private List<Rating> ratings;
    private List<Member> reservationList;

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
    protected List<Rating> getRatings(){
    	return this.ratings;
    }
    protected void addRating(Rating rating) {
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
}
