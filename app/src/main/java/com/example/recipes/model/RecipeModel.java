package com.example.recipes.model;

import java.util.concurrent.Executor;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.recipes.database.RecipesFirebaseHandler;
import com.example.recipes.database.interfaces.IRecipesDBHandler;
import com.example.recipes.model.interfaces.IRecipeModel;
import com.example.recipes.cache.AppLocalDbRepository;
import com.example.recipes.dto.Recipe;
import java.util.List;

public class RecipeModel implements IRecipeModel {
    private final Executor executor;
    private final AppLocalDbRepository localDb;
    private IRecipesDBHandler recipesFirebaseHandler = new RecipesFirebaseHandler();
    private LiveData<List<Recipe>> recipesList;
    private LiveData<List<Recipe>> userRecipesList;
    final private MutableLiveData<ModelClient.LoadingState> EventRecipesListLoadingState = new MutableLiveData<ModelClient.LoadingState>(ModelClient.LoadingState.NOT_LOADING);
    final private MutableLiveData<ModelClient.LoadingState> EventUserRecipesListLoadingState = new MutableLiveData<ModelClient.LoadingState>(ModelClient.LoadingState.NOT_LOADING);


    public RecipeModel(Executor executor, AppLocalDbRepository localDb) {
        this.executor = executor;
        this.localDb = localDb;
    }

    public MutableLiveData<ModelClient.LoadingState> getEventRecipesListLoadingState(){
        return this.EventRecipesListLoadingState;
    }

    public MutableLiveData<ModelClient.LoadingState> getEventUserRecipesListLoadingState(){
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
        EventUserRecipesListLoadingState.setValue(ModelClient.LoadingState.LOADING);
        this.recipesFirebaseHandler.getRecipesOfUser(userId, (List<Recipe> recipes) -> {
            executor.execute(() -> {
                for (Recipe recipe : recipes) {
                    localDb.recipesDao().insertAll(recipe);
                }
                EventUserRecipesListLoadingState.postValue(ModelClient.LoadingState.NOT_LOADING);
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
        EventRecipesListLoadingState.setValue(ModelClient.LoadingState.LOADING);
        this.recipesFirebaseHandler.getAllRecipes((List<Recipe> recipes) -> {
            executor.execute(() -> {
                for (Recipe recipe : recipes) {
                    localDb.recipesDao().insertAll(recipe);
                }
                EventRecipesListLoadingState.postValue(ModelClient.LoadingState.NOT_LOADING);
            });
        });
    }




}