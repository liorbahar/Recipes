package com.example.recipes.localdatabase;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.recipes.models.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("select * from Recipe")
    List<Recipe> getAll();
}
