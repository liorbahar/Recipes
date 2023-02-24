package com.example.recipes.helper.models;

import java.util.concurrent.Executor;
import android.os.Handler;
import com.example.recipes.database.UserFirebaseHandler;
import com.example.recipes.helper.models.interfaces.IUserModel;
import com.example.recipes.localdatabase.AppLocalDbRepository;

public class UserModel implements IUserModel {
    private final Executor executor;
    private final Handler mainHandler;
    private final UserFirebaseHandler dbUser;
    private final AppLocalDbRepository localDb;

    public UserModel(Handler mainHandler,Executor executor,AppLocalDbRepository localDb){
        this.dbUser = new UserFirebaseHandler();
        this.mainHandler = mainHandler;
        this.executor = executor;
        this.localDb = localDb;
    }
}
