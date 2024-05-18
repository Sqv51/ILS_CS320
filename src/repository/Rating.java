package src.repository;

import src.Member;

import java.util.List;

public class Rating{

    private Member member;
    private int score;
	
    public Rating(Member member, int score) {}
	
    protected Member getMember() {
	return this.member;
    }
    public int getScore() {
	return this.score;
    }
    protected void setScore(int rating) {
	this.score = rating;
    }
}
