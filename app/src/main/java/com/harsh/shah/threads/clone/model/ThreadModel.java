package com.harsh.shah.threads.clone.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ThreadModel implements Parcelable {
    private List<String> images = new ArrayList<>();
    private ArrayList<CommentsModel> comments = new ArrayList<>();
    private List<String> shares = new ArrayList<>();
    private List<String> likes = new ArrayList<>();
    private List<String> reposts = new ArrayList<>();
    private boolean allowedComments = false;
    private boolean isGif = false;
    private boolean isPoll = false;
    private String uID = "";
    private String gifUrl = "";
    private String iD = "";
    private String text = "";
    private String time = "";
    private String username = "";
    private String profileImage = "";
    private PollOptions pollOptions = new PollOptions(new PollOptions.PollOptionsItem(), new PollOptions.PollOptionsItem(), new PollOptions.PollOptionsItem(), new PollOptions.PollOptionsItem());

    public ThreadModel(List<String> images, ArrayList<CommentsModel> comments, boolean allowedComments, boolean isGif, boolean isPoll, List<String> shares, String uID, String gifUrl, String iD, String text, String time, PollOptions pollOptions, List<String> likes, String profileImage, String username, List<String> reposts) {
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
        this.profileImage = profileImage;
        this.username = username;
        this.reposts = reposts;
    }

    public ThreadModel() {
    }

    public ThreadModel(Parcel in) {
        images = in.createStringArrayList();
        comments = in.readParcelable(CommentsModel.class.getClassLoader());
        allowedComments = in.readByte() != 0;
        isGif = in.readByte() != 0;
        isPoll = in.readByte() != 0;
        shares = in.createStringArrayList();
        uID = in.readString();
        gifUrl = in.readString();
        iD = in.readString();
        text = in.readString();
        time = in.readString();
        pollOptions = in.readParcelable(PollOptions.class.getClassLoader());
        likes = in.createStringArrayList();
        profileImage = in.readString();
        username = in.readString();
        reposts = in.createStringArrayList();
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public ArrayList<CommentsModel> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentsModel> comments) {
        this.comments = comments;
    }

    public boolean isAllowedComments() {
        return allowedComments;
    }

    public void setAllowedComments(boolean allowedComments) {
        this.allowedComments = allowedComments;
    }

    public boolean isIsGif() {
        return isGif;
    }

    public void setIsGif(boolean isGif) {
        this.isGif = isGif;
    }

    public boolean isIsPoll() {
        return isPoll;
    }

    public void setIsPoll(boolean isPoll) {
        this.isPoll = isPoll;
    }

    public List<String> getShares() {
        return shares;
    }

    public void setShares(List<String> shares) {
        this.shares = shares;
    }

    public String getUID() {
        return uID;
    }

    public void setUID(String uID) {
        this.uID = uID;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public void setGifUrl(String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
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

    public PollOptions getPollOptions() {
        return pollOptions;
    }

    public void setPollOptions(PollOptions pollOptions) {
        this.pollOptions = pollOptions;
    }

    public List<String> getLikes() {
        return likes;
    }

    public void setLikes(List<String> likes) {
        this.likes = likes;
    }

    public String getUsername() {
        return username;
    }

    public ThreadModel setUsername(String username) {
        this.username = username;
        return this;
    }

    public String profileImage() {
        return profileImage;
    }

    public ThreadModel setProfileImage(String profileImage) {
        this.profileImage = profileImage;
        return this;
    }

    public List<String> getReposts() {
        return reposts;
    }

    public void setReposts(List<String> reposts) {
        this.reposts = reposts;
    }


    @NonNull
    @Override
    public String toString() {
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
                        ",profileImage = '" + profileImage + '\'' +
                        ",username = '" + username + '\'' +
                        ",reposts = '" + reposts + '\'' +
                        "}";
    }

    public static final Creator<ThreadModel> CREATOR = new Creator<ThreadModel>() {
        @Override
        public ThreadModel createFromParcel(Parcel in) {
            return new ThreadModel(in);
        }

        @Override
        public ThreadModel[] newArray(int size) {
            return new ThreadModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(images);
        dest.writeList(comments);
        dest.writeByte((byte) (allowedComments ? 1 : 0));
        dest.writeByte((byte) (isGif ? 1 : 0));
        dest.writeByte((byte) (isPoll ? 1 : 0));
        dest.writeStringList(shares);
        dest.writeString(uID);
        dest.writeString(gifUrl);
        dest.writeString(iD);
        dest.writeString(text);
        dest.writeString(time);
        dest.writeParcelable(pollOptions, flags);
        dest.writeStringList(likes);
        dest.writeString(profileImage);
        dest.writeString(username);
        dest.writeStringList(reposts);
    }

}