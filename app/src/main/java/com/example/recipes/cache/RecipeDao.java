package com.example.recipes.cache;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.recipes.dto.Recipe;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("select * from Recipe")
    LiveData<List<Recipe>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Recipe... recipes);

    @Query("select * from Recipe where userId = :userId")
    LiveData<List<Recipe>> getRecipesByUserId(String userId);

    @Query("DELETE FROM Recipe")
    void deleteAll();

    @Query("DELETE FROM Recipe WHERE id =:id")
    void delete(String id);
}
