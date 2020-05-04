package com.lollipop50.croaker.repository.room;

import android.content.Context;

import androidx.room.Room;

import com.lollipop50.croaker.model.Post;
import com.lollipop50.croaker.repository.BaseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RoomRepository extends BaseRepository {

    private PostDao postDao;

    public RoomRepository(Context context) {
        postDao = Room.databaseBuilder(
                context,
                PostDatabase.class,
                "post-database.sqlite"
        )
                .allowMainThreadQueries()
                .build()
                .getPostDao();
    }

    @Override
    public List<Post> getAllPosts() {
        List<PostEntity> postEntities = postDao.getAll();

        List<Post> resultList = new ArrayList<>();

        for (PostEntity postEntity : postEntities) {
            resultList.add(Converter.convert(postEntity));
        }

        return resultList;
    }

    @Override
    public Post getPostById(UUID id) {
        PostEntity postEntity = postDao.getById(id.toString());
        return Converter.convert(postEntity);
    }

    @Override
    public void add(Post post) {
        postDao.add(Converter.convert(post));
        notifyListeners();
    }

    @Override
    public void delete(Post post) {
        postDao.delete(Converter.convert(post));
        notifyListeners();
    }

    @Override
    public void update(Post post) {
        postDao.update(Converter.convert(post));
        notifyListeners();
    }
}
