package com.lollipop50.croaker.repository.test;

import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.ImageView;

import com.lollipop50.croaker.model.Post;
import com.lollipop50.croaker.repository.BaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestRepository extends BaseRepository {

    private final List<Post> allPosts = new ArrayList<>();

    @Override
    public List<Post> getAllPosts() {
        return allPosts;
    }

    @Override
    public Post getPostById(UUID id) {
        for (Post post : allPosts) {
            if (post.getPostId().equals(id)) {
                return post;
            }
        }
        return null;
    }

    @Override
    public void add(Post post) {
        allPosts.add(post);
        notifyListeners();
    }

    @Override
    public void delete(Post post) {
        allPosts.remove(post);
        notifyListeners();
    }

    @Override
    public void update(Post post) {
        notifyListeners();
    }

    public static Post generateDefaultPost() {
        Post defaultPost = new Post();
        defaultPost.setAvatar("/storage/emulated/0/Pics4MyApps/KBird.jpg");
        defaultPost.setUsername("Lollipop50");
        defaultPost.setPostText("Welcome to my blog!");
        defaultPost.setPostPicture("/storage/emulated/0/Pics4MyApps/Parrots.jpg");
        return defaultPost;
    }

    public static void setPictureFromFile(String filePath, ImageView imageView) {
        imageView.setImageBitmap(BitmapFactory.decodeFile(filePath));
    }
}
