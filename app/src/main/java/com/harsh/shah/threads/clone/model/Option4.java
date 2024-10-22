package com.harsh.shah.threads.clone.model;

public class Option4{
	private int votes;
	private String text;

	public Option4(int votes, String text) {
		this.votes = votes;
		this.text = text;
	}

	public Option4() {
	}

	public void setVotes(int votes){
		this.votes = votes;
	}

	public int getVotes(){
		return votes;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	@Override
 	public String toString(){
		return 
			"Option4{" + 
			"votes = '" + votes + '\'' + 
			",text = '" + text + '\'' + 
			"}";
		}
}
