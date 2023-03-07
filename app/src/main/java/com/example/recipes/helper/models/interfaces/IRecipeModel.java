package com.example.recipes.helper.models.interfaces;

import com.example.recipes.helper.models.RecipeModel;


public interface IRecipeModel {
    void getAllRecipes(RecipeModel.GetAllRecipesListener callback);
}