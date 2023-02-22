package com.example.recipes.helper.models;


import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.example.recipes.localdatabase.AppLocalDb;
import com.example.recipes.localdatabase.AppLocalDbRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ModelClient {
    private static final ModelClient _instance = new ModelClient();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public UserModel users;

    public static ModelClient instance(){
        return _instance;
    }
    private ModelClient(){
        this.users = new UserModel(mainHandler, executor, localDb);
    }
}
