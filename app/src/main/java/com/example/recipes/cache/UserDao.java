package com.example.recipes.cache;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.recipes.dto.User;

@Dao
public interface UserDao {
    @Query("select * from User")
    LiveData<User> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Query("DELETE FROM User")
    void delete();
}
