package com.example.recipes.helper.models;

import java.util.concurrent.Executor;

import android.graphics.Bitmap;
import android.os.Handler;

import androidx.lifecycle.LiveData;

import com.example.recipes.database.RecipesFirebaseHandler;
import com.example.recipes.helper.models.interfaces.IRecipeModel;
import com.example.recipes.localdatabase.AppLocalDbRepository;
import com.example.recipes.models.Recipe;

import java.util.List;

public class RecipeModel implements IRecipeModel {
    private final Executor executor;
    private final AppLocalDbRepository localDb;
    private RecipesFirebaseHandler recipesFirebaseHandler = new RecipesFirebaseHandler();
    private LiveData<List<Recipe>> recipesList;
    private LiveData<List<Recipe>> userRecipesList;


    public RecipeModel(Executor executor, AppLocalDbRepository localDb) {
        this.executor = executor;
        this.localDb = localDb;
    }

    public interface GetAllRecipesListener {
        void onComplete(List<Recipe> data);
    }

    public LiveData<List<Recipe>> getUserRecipes(String userId) {
        if (this.userRecipesList == null) {
            this.userRecipesList = this.localDb.recipesDao().getRecipesByUserId(userId);
            this.refreshUserRecipes(userId);
        }
        return this.userRecipesList;
    }

    public void addRecipe(Recipe recipe, ModelClient.Listener listener) {
        recipesFirebaseHandler.addRecipe(recipe, listener);
    }

    public void refreshUserRecipes(String userId) {
        this.recipesFirebaseHandler.getRecipesOfUser(userId, (List<Recipe> recipes) -> {
            executor.execute(() -> {
                for (Recipe recipe : recipes) {
                    localDb.recipesDao().insertAll(recipe);
                }
            });
        });
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        if (this.recipesList == null) {
            this.recipesList = this.localDb.recipesDao().getAll();
            this.refreshAllRecipes();
        }
        return this.recipesList;
    }

    public Recipe getRecipe(String recipeId) {
        if (this.recipesList == null) {
            this.recipesList = this.localDb.recipesDao().getAll();
            this.refreshAllRecipes();
        }
        for (Recipe recipe : this.recipesList.getValue()) {
            if (recipe.getId().equals(recipeId)) {
                return recipe;
            }
        }

        return null;
    }


    public void refreshAllRecipes() {
        this.recipesFirebaseHandler.getAllRecipes((List<Recipe> recipes) -> {
            executor.execute(() -> {
                for (Recipe recipe : recipes) {
                    localDb.recipesDao().insertAll(recipe);
                }
            });
        });
    }
}