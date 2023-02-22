package com.example.recipes.localdatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.recipes.models.User;

@Database(entities = {User.class}, version = 80)
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao users();
}
