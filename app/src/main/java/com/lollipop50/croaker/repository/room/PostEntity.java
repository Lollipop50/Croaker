package com.lollipop50.croaker.repository.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class PostEntity {

    @PrimaryKey
    @NonNull
    public String id;
    public String avatar;
    public String username;
    public String postText;
    public String postPicture;
    public boolean isLiked;
    public boolean isBookmarked;
}
