package com.example.recipes.helper.models;

import java.util.concurrent.Executor;

import android.os.Handler;

import androidx.lifecycle.LiveData;

import com.example.recipes.database.RecipesFirebaseHandler;
import com.example.recipes.helper.models.interfaces.IRecipeModel;
import com.example.recipes.localdatabase.AppLocalDbRepository;
import com.example.recipes.models.Recipe;

import java.util.List;

public class RecipeModel implements IRecipeModel {
    private final Executor executor;
    private final Handler mainHandler;
    private final AppLocalDbRepository localDb;
    private RecipesFirebaseHandler recipesFirebaseHandler = new RecipesFirebaseHandler();
    private LiveData<List<Recipe>> recipesList;


    public RecipeModel(Handler mainHandler, Executor executor, AppLocalDbRepository localDb) {
        this.mainHandler = mainHandler;
        this.executor = executor;
        this.localDb = localDb;
    }

    public interface GetAllRecipesListener {
        void onComplete(List<Recipe> data);
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        if (this.recipesList == null) {
            this.recipesList = this.localDb.recipesDao().getAll();
            this.refreshAllRecipes();
        }
        return this.recipesList;
    }

    public void refreshAllRecipes(){
        this.recipesFirebaseHandler.getAllRecipes((List<Recipe> recipes) -> {
            executor.execute(() -> {
                for(Recipe recipe:recipes){
                    localDb.recipesDao().insertAll(recipe);
                }
            });
        });
    }

//    public void getAllRecipes(GetAllRecipesListener callback) {
//        executor.execute(() -> {
////            List<Recipe> data = localDb.recipesDao().getAll();
//            this.recipesFirebaseHandler.getAllRecipes(data1 -> {
//                mainHandler.post(() -> callback.onComplete(data1));
//            });
//
//        });
//    }
}