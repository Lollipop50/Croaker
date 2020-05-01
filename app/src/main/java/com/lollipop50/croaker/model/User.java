package com.lollipop50.croaker.model;

public class User {

    private String avatar;
    private String username;
    private String bio;

    public User() {
        avatar = "";
        username = "User";
        bio = "";
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
