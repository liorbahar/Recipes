package com.example.recipes.model.interfaces;

import com.example.recipes.model.ModelClient;
import com.example.recipes.model.RecipeModel;
import com.example.recipes.dto.Recipe;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public interface IRecipeModel {
    LiveData<List<Recipe>> getAllRecipes();

    LiveData<List<Recipe>> getUserRecipes(String userId);

    void addRecipe(Recipe recipe, ModelClient.Listener listener);

    MutableLiveData<LoadingState> getEventRecipesListLoadingState();

    MutableLiveData<LoadingState> getEventUserRecipesListLoadingState();

    void refreshAllRecipes();

    Recipe getRecipe(String recipeId);

    void refreshUserRecipes(String userId);
}
