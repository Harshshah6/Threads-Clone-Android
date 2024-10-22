package com.harsh.shah.threads.clone.model;

public class PollOptions{
	private Option3 option3;
	private Option4 option4;
	private Option1 option1;
	private Option2 option2;

	public PollOptions() {
	}

	public PollOptions(Option3 option3, Option4 option4, Option1 option1, Option2 option2) {
		this.option3 = option3;
		this.option4 = option4;
		this.option1 = option1;
		this.option2 = option2;
	}

	public void setOption3(Option3 option3){
		this.option3 = option3;
	}

	public Option3 getOption3(){
		return option3;
	}

	public void setOption4(Option4 option4){
		this.option4 = option4;
	}

	public Option4 getOption4(){
		return option4;
	}

	public void setOption1(Option1 option1){
		this.option1 = option1;
	}

	public Option1 getOption1(){
		return option1;
	}

	public void setOption2(Option2 option2){
		this.option2 = option2;
	}

	public Option2 getOption2(){
		return option2;
	}

	@Override
 	public String toString(){
		return 
			"PollOptions{" + 
			"option3 = '" + option3 + '\'' + 
			",option4 = '" + option4 + '\'' + 
			",option1 = '" + option1 + '\'' + 
			",option2 = '" + option2 + '\'' + 
			"}";
		}
}
