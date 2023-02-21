package com.example.recipes.localdatabase;

import com.example.recipes.MyApplication;
import com.example.recipes.models.User;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {User.class}, version = 80)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao users();
}

public class AppLocalDb {
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}
