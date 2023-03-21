package com.example.recipes.dto.api;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomRecipeApiResult {
    @SerializedName("recipes")
    List<RandomRecipeApi> recipes;

    public RandomRecipeApi getRecipe() {
        return recipes.get(0);
    }
}
