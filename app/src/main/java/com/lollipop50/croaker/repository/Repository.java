package com.lollipop50.croaker.repository;

import com.lollipop50.croaker.model.Post;

import java.util.List;
import java.util.UUID;

public interface Repository {

    List<Post> getAllPosts();

    Post getPostById(UUID id);

    void add(Post post);

    void delete(Post post);

    void update();

    void addListener(Listener listener);

    void removeListener(Listener listener);

    interface Listener {
        void onDataChanged();
    }
}
