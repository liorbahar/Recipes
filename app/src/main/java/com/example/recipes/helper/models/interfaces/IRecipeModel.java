package com.example.recipes.helper.models.interfaces;

import com.example.recipes.helper.models.RecipeModel;
import com.example.recipes.models.Recipe;

public interface IRecipeModel {
    void getAllRecipes(RecipeModel.GetAllRecipesListener callback);
    void addRecipe(Recipe recipe, RecipeModel.AddRecipeListener listener);
}
