package com.example.recipes.model.interfaces;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipes.model.ModelClient;
import com.example.recipes.model.RecipeModel;
import com.example.recipes.dto.Recipe;

public interface ISpecialRandomRecipeModel {
    LiveData<Recipe> getRandomRecipe(ModelClient.Listener<Void> onFailedListener);

    MutableLiveData<LoadingState> getEventSpecialRandomRecipeLoadingState();



}
