package com.lollipop50.croaker.repository.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(
        entities = {PostEntity.class},
        version = 1,
        exportSchema = false
)
public abstract class PostDatabase extends RoomDatabase {

    public abstract PostDao getPostDao();
}
