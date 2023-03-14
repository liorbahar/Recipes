package com.example.recipes.helper.models;

import com.example.recipes.localdatabase.AppLocalDb;
import com.example.recipes.localdatabase.AppLocalDbRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ModelClient {
    private static final ModelClient _instance = new ModelClient();
    private Executor executor = Executors.newSingleThreadExecutor();
    private AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public UserModel users;
    public RecipeModel recipes;


    public static ModelClient instance() {
        return _instance;
    }

    public interface Listener<T> {
        void onComplete(T data);
    }

    private ModelClient() {
        this.users = new UserModel(executor, localDb);
        this.recipes = new RecipeModel(executor, localDb);
    }
}
