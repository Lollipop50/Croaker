package com.lollipop50.croaker.model;

import java.util.UUID;

public class Post {

    private String avatar;
    private String username;
    private UUID id;
    private String postText;
    private String postPicture;
    private boolean isLiked;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getPostPicture() {
        return postPicture;
    }

    public void setPostPicture(String postPicture) {
        this.postPicture = postPicture;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }
}
