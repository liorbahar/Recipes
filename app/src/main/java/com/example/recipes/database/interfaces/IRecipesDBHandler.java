package com.example.recipes.database.interfaces;

import com.example.recipes.dto.Recipe;
import com.example.recipes.model.ModelClient;
import com.example.recipes.model.RecipeModel;

import java.util.List;

public interface IRecipesDBHandler {
    void getAllRecipes(ModelClient.Listener<List<Recipe>> callback);

    void getRecipesOfUser(String userId, ModelClient.Listener<List<Recipe>> callback);

    void addRecipe(Recipe recipe, ModelClient.Listener<Void> listener);
}
