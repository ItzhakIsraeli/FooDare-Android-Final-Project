package com.example.foodare.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodare.MyApplication;


//TODO: add also User Entity

@Database(entities = {Post.class}, version = 2)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract PostDao PostDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb() {
    }
}

