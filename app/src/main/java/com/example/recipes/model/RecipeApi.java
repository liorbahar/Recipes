package com.example.recipes.model;


import com.example.recipes.dto.api.RandomRecipeApiResult;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public interface RecipeApi {
    @GET("/recipes/random")
    Call<RandomRecipeApiResult> getRandomRecipe(@HeaderMap Map<String, String> headers);

}
