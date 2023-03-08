package com.example.recipes.database.interfaces;

import com.example.recipes.helper.models.RecipeModel;

public interface IRecipesDBHandler {
    void getAllRecipes(RecipeModel.GetAllRecipesListener callback);

    void getRecipesOfUser(String userId, RecipeModel.GetAllRecipesListener callback);
}
