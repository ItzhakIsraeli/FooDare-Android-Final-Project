package com.example.foodare.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodare.MyApplication;

@Database(entities = {Post.class}, version = 5)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao PostDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration().allowMainThreadQueries()
                .build();
    }

    private AppLocalDb() {
    }
}

