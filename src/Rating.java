package src;

import java.util.List;

public class Rating{
	
	private Member member;
	private int score;
	
	private Rating(Member member, int score) {}
	
	protected Member getMember() {
		return this.member;
	}
	protected int getScore() {
		return this.score;
	}
	protected void setScore(int rating) {
		this.score = rating;
	}
}
