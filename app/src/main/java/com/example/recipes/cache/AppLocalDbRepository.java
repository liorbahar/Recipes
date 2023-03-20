package com.example.recipes.cache;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.recipes.dto.Recipe;
import com.example.recipes.dto.User;

@Database(entities = {User.class, Recipe.class}, version = 85)
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao UserDao();

    public abstract RecipeDao recipesDao();
}
