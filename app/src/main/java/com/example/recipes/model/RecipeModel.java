package com.example.recipes.model;

import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipes.database.RecipesFirebaseHandler;
import com.example.recipes.database.interfaces.IRecipesDBHandler;
import com.example.recipes.model.interfaces.IRecipeModel;
import com.example.recipes.cache.AppLocalDbRepository;
import com.example.recipes.dto.Recipe;
import com.example.recipes.model.interfaces.LoadingState;

import java.util.List;

public class RecipeModel implements IRecipeModel {
    private final Executor executor;
    private final AppLocalDbRepository localDb;
    private IRecipesDBHandler recipesFirebaseHandler = new RecipesFirebaseHandler();
    private LiveData<List<Recipe>> recipesList;
    private LiveData<List<Recipe>> userRecipesList;
    final private MutableLiveData<LoadingState> EventRecipesListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);
    final private MutableLiveData<LoadingState> EventUserRecipesListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);


    public RecipeModel(Executor executor, AppLocalDbRepository localDb) {
        this.executor = executor;
        this.localDb = localDb;
    }

    public MutableLiveData<LoadingState> getEventRecipesListLoadingState(){
        return this.EventRecipesListLoadingState;
    }

    public MutableLiveData<LoadingState> getEventUserRecipesListLoadingState(){
        return this.EventUserRecipesListLoadingState;
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
        this.refreshUserRecipes(recipe.userId);
    }

    public void refreshUserRecipes(String userId) {
        EventUserRecipesListLoadingState.setValue(LoadingState.LOADING);
        this.recipesFirebaseHandler.getRecipesOfUser(userId, (List<Recipe> recipes) -> {
            executor.execute(() -> {
                for (Recipe recipe : recipes) {
                    localDb.recipesDao().insertAll(recipe);
                }
                EventUserRecipesListLoadingState.postValue(LoadingState.NOT_LOADING);
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

    public void refreshAllRecipes() {
        EventRecipesListLoadingState.setValue(LoadingState.LOADING);
        Long localLastUpdate = Recipe.getLocalLastUpdate();

        this.recipesFirebaseHandler.getAllRecipesSince(localLastUpdate, (List<Recipe> recipes) -> {
            executor.execute(() -> {
                Long time = localLastUpdate;

                for (Recipe recipe : recipes) {
                    localDb.recipesDao().insertAll(recipe);
                    if (time < recipe.getLastUpdated()) {
                        time = recipe.getLastUpdated();
                    }
                }

                Recipe.setLocalLastUpdate(time);
                EventRecipesListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void removeRecipe(Recipe recipe, ModelClient.Listener listener) {
        this.recipesFirebaseHandler.deleteRecipe(recipe.id, listener);
        this.localDb.recipesDao().delete(recipe.id);
    }

}