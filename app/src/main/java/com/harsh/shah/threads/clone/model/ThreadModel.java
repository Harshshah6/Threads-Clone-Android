package com.harsh.shah.threads.clone.model;

import androidx.annotation.NonNull;

import java.util.List;

public class ThreadModel {
	private List<String> images;
	private List<Object> comments;
	private boolean allowedComments;
	private boolean isGif;
	private boolean isPoll;
	private List<String> shares;
	private String uID;
	private String gifUrl;
	private String iD;
	private String text;
	private String time;
	private PollOptions pollOptions;
	private int likes;

	public ThreadModel(List<String> images, List<Object> comments, boolean allowedComments, boolean isGif, boolean isPoll, List<String> shares, String uID, String gifUrl, String iD, String text, String time, PollOptions pollOptions, int likes) {
		this.images = images;
		this.comments = comments;
		this.allowedComments = allowedComments;
		this.isGif = isGif;
		this.isPoll = isPoll;
		this.shares = shares;
		this.uID = uID;
		this.gifUrl = gifUrl;
		this.iD = iD;
		this.text = text;
		this.time = time;
		this.pollOptions = pollOptions;
		this.likes = likes;
	}

	public ThreadModel() {
	}

	public void setImages(List<String> images){
		this.images = images;
	}

	public List<String> getImages(){
		return images;
	}

	public void setComments(List<Object> comments){
		this.comments = comments;
	}

	public List<Object> getComments(){
		return comments;
	}

	public void setAllowedComments(boolean allowedComments){
		this.allowedComments = allowedComments;
	}

	public boolean isAllowedComments(){
		return allowedComments;
	}

	public void setIsGif(boolean isGif){
		this.isGif = isGif;
	}

	public boolean isIsGif(){
		return isGif;
	}

	public void setIsPoll(boolean isPoll){
		this.isPoll = isPoll;
	}

	public boolean isIsPoll(){
		return isPoll;
	}

	public void setShares(List<String> shares){
		this.shares = shares;
	}

	public List<String> getShares(){
		return shares;
	}

	public void setUID(String uID){
		this.uID = uID;
	}

	public String getUID(){
		return uID;
	}

	public void setGifUrl(String gifUrl){
		this.gifUrl = gifUrl;
	}

	public String getGifUrl(){
		return gifUrl;
	}

	public void setID(String iD){
		this.iD = iD;
	}

	public String getID(){
		return iD;
	}

	public void setText(String text){
		this.text = text;
	}

	public String getText(){
		return text;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getTime(){
		return time;
	}

	public void setPollOptions(PollOptions pollOptions){
		this.pollOptions = pollOptions;
	}

	public PollOptions getPollOptions(){
		return pollOptions;
	}

	public void setLikes(int likes){
		this.likes = likes;
	}

	public int getLikes(){
		return likes;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"ThreadsPostedItem{" + 
			"images = '" + images + '\'' + 
			",comments = '" + comments + '\'' + 
			",allowedComments = '" + allowedComments + '\'' + 
			",isGif = '" + isGif + '\'' + 
			",isPoll = '" + isPoll + '\'' + 
			",shares = '" + shares + '\'' + 
			",uID = '" + uID + '\'' + 
			",gifUrl = '" + gifUrl + '\'' + 
			",iD = '" + iD + '\'' + 
			",text = '" + text + '\'' + 
			",time = '" + time + '\'' + 
			",pollOptions = '" + pollOptions + '\'' + 
			",likes = '" + likes + '\'' + 
			"}";
		}
}