package com.lollipop50.croaker.repository.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {

    @Query("SELECT * FROM PostEntity")
    List<PostEntity> getAll();

    @Query("SELECT * FROM PostEntity WHERE id == :id")
    PostEntity getById(String id);

    @Insert
    void add(PostEntity postEntity);

    @Delete
    void delete(PostEntity postEntity);

    @Update
    void update(PostEntity postEntity);
}
