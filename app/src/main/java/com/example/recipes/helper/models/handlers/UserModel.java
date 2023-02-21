package com.example.recipes.helper.models.handlers;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.os.Looper;
import androidx.core.os.HandlerCompat;
import com.example.recipes.database.UserFirebaseHandler;

public class UserModel {
    private Executor executor;
    private Handler mainHandler;
    private final UserFirebaseHandler dbUser;
    //AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    public UserModel(){
        this.dbUser = new UserFirebaseHandler();
        this.mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        this.executor = Executors.newSingleThreadExecutor();
    }
}
