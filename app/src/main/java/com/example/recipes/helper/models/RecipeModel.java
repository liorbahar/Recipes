package com.example.recipes.helper.models;

import java.util.concurrent.Executor;

import android.os.Handler;

import com.example.recipes.helper.models.interfaces.IRecipeModel;
import com.example.recipes.localdatabase.AppLocalDbRepository;
import com.example.recipes.models.Recipe;

import java.util.List;

public class RecipeModel implements IRecipeModel {
    private final Executor executor;
    private final Handler mainHandler;
    private final AppLocalDbRepository localDb;

    public RecipeModel(Handler mainHandler, Executor executor, AppLocalDbRepository localDb) {
        this.mainHandler = mainHandler;
        this.executor = executor;
        this.localDb = localDb;
    }

    public interface GetAllRecipesListener {
        void onComplete(List<Recipe> data);
    }

    public void getAllRecipes(GetAllRecipesListener callback) {
        executor.execute(() -> {
            List<Recipe> data = localDb.recipesDao().getAll();
            mainHandler.post(() -> callback.onComplete(data));
        });
    }

    public interface AddRecipeListener {
        void onComplete();
    }

    public void addRecipe(Recipe recipe, AddRecipeListener listener) {
        executor.execute(() -> {
            localDb.recipesDao().insertAll(recipe);
            mainHandler.post(() -> listener.onComplete());
        });
    }
}
