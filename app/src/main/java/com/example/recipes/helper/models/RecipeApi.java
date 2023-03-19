package com.example.recipes.helper.models;


import com.example.recipes.models.api.RandomRecipeApiResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RecipeApi {
    @Headers("x-api-key: b990ced0509740359925d03e8206fa04")
    @GET("/recipes/random")
    Call<RandomRecipeApiResult> getRandomRecipe();

}
