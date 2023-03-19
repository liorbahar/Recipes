package com.example.recipes.helper.models.interfaces;

import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;
import androidx.lifecycle.LiveData;
import java.util.List;

public interface IRecipeModel {
    LiveData<List<Recipe>> getAllRecipes();

    LiveData<List<Recipe>> getUserRecipes(String userId);

    void addRecipe(Recipe recipe, ModelClient.Listener listener);

    LiveData<Recipe> getRandomRecipe();
}
