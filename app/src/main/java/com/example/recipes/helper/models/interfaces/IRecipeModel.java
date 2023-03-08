package com.example.recipes.helper.models.interfaces;

import androidx.lifecycle.LiveData;

import com.example.recipes.helper.models.RecipeModel;
import com.example.recipes.models.Recipe;

import java.util.List;


public interface IRecipeModel {
    LiveData<List<Recipe>> getAllRecipes();

    LiveData<List<Recipe>> getUserRecipes(String userId);
}