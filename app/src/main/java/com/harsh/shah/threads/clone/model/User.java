package com.harsh.shah.threads.clone.model;

import androidx.annotation.NonNull;

public class User {
    private String profilePicture;
    private String password;
    private String accountVisibility;
    private String displayName;
    private String signInProvider;
    private String bio;
    private String email;
    private String username;

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccountVisibility() {
        return accountVisibility;
    }

    public void setAccountVisibility(String accountVisibility) {
        this.accountVisibility = accountVisibility;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(String signInProvider) {
        this.signInProvider = signInProvider;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NonNull
    @Override
    public String toString() {
        return
                "User{" +
                        "profilePicture = '" + profilePicture + '\'' +
                        ",password = '" + password + '\'' +
                        ",accountVisibility = '" + accountVisibility + '\'' +
                        ",displayName = '" + displayName + '\'' +
                        ",signInProvider = '" + signInProvider + '\'' +
                        ",bio = '" + bio + '\'' +
                        ",email = '" + email + '\'' +
                        ",username = '" + username + '\'' +
                        "}";
    }
}
