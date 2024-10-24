package com.harsh.shah.threads.clone.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CommentsModel {
    private String UID;
    private String ID;
    private String text;
    private String time;
    private String profileImage;
    private String username;
    private ArrayList<String> likes;
    private ArrayList<String> comments;

    public CommentsModel() {

    }

    public CommentsModel(String UID, String ID, String text, String time, String profileImage, String username, ArrayList<String> likes, ArrayList<String> comments) {
        this.UID = UID;
        this.ID = ID;
        this.text = text;
        this.time = time;
        this.profileImage = profileImage;
        this.username = username;
        this.likes = likes;
        this.comments = comments;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<String> likes) {
        this.likes = likes;
    }

    public ArrayList<String> getComments() {
        return comments;
    }

    public void setComments(ArrayList<String> comments) {
        this.comments = comments;
    }

    @NonNull
    @Override
    public String toString() {
        return "CommentsModel{" +
                "UID='" + UID + '\'' +
                ", ID='" + ID + '\'' +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", username='" + username + '\'' +
                ", likes=" + likes +
                ", comments=" + comments +
                '}';
    }
}
