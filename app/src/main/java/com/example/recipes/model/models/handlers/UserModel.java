package com.example.recipes.model.models.handlers;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.os.Looper;
import androidx.core.os.HandlerCompat;
import com.example.recipes.model.database.handlers.UserFirebaseHandler;

public class UserModel {
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private UserFirebaseHandler dbUser = new UserFirebaseHandler();
//    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    public UserModel(){

    }

    public void AddUser(){
        this.dbUser.AddUser();
    }
}
