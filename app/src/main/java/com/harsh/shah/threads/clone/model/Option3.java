package com.harsh.shah.threads.clone.model;

public class Option3{
	private int votes;
	private String text;

	public Option3(int votes, String text) {
		this.votes = votes;
		this.text = text;
	}

	public Option3() {
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
			"Option3{" + 
			"votes = '" + votes + '\'' + 
			",text = '" + text + '\'' + 
			"}";
		}
}
