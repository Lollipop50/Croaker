package com.lollipop50.croaker.repository.room;

import com.lollipop50.croaker.model.Post;

import java.util.UUID;

public class Converter {

    public static PostEntity convert(Post post) {
        PostEntity postEntity = new PostEntity();

        postEntity.id = post.getPostId().toString();
        postEntity.avatar = post.getAvatar();
        postEntity.username = post.getUsername();
        postEntity.postText = post.getPostText();
        postEntity.postPicture = post.getPostPicture();
        postEntity.isLiked = post.isLiked();

        return postEntity;
    }

    public static Post convert(PostEntity postEntity) {
        Post post = null;

        if (postEntity != null) {
            post = new Post();

            post.setPostId(UUID.fromString(postEntity.id));
            post.setAvatar(postEntity.avatar);
            post.setUsername(postEntity.username);
            post.setPostText(postEntity.postText);
            post.setPostPicture(postEntity.postPicture);
            post.setLiked(postEntity.isLiked);
        }

        return post;
    }
}
