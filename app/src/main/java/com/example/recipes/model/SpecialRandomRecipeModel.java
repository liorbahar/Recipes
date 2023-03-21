package com.example.recipes.model;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipes.MyApplication;
import com.example.recipes.R;
import com.example.recipes.model.interfaces.ISpecialRandomRecipeModel;
import com.example.recipes.dto.Recipe;
import com.example.recipes.dto.api.RandomRecipeApiResult;
import com.example.recipes.model.interfaces.LoadingState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpecialRandomRecipeModel implements ISpecialRandomRecipeModel {
    private Retrofit retrofit;
    private RecipeApi recipeApi;
    private HashMap<String, String> headers = new HashMap<>();;
    final private MutableLiveData<LoadingState> EventSpecialRandomRecipeLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    public SpecialRandomRecipeModel(){
       Context context = MyApplication.getMyContext();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(context.getString(R.string.baseUrl))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        recipeApi = retrofit.create(RecipeApi.class);
        headers.put("x-api-key", context.getString(R.string.apiKey));
    }

    public LiveData<Recipe> getRandomRecipe(ModelClient.Listener<Void> onFailedListener) {
        EventSpecialRandomRecipeLoadingState.setValue(LoadingState.LOADING);
        MutableLiveData<Recipe> data = new MutableLiveData<>();
        Call<RandomRecipeApiResult> call = this.recipeApi.getRandomRecipe(headers);
        call.enqueue(new Callback<RandomRecipeApiResult>() {
            @Override
            public void onResponse(Call<RandomRecipeApiResult> call, Response<RandomRecipeApiResult> response) {
                if (response.isSuccessful()) {
                    RandomRecipeApiResult res = response.body();
                    data.setValue(res.getRecipe().toRecipe());
                }
                EventSpecialRandomRecipeLoadingState.postValue(LoadingState.NOT_LOADING);
            }

            @Override
            public void onFailure(Call<RandomRecipeApiResult> call, Throwable t) {
                EventSpecialRandomRecipeLoadingState.postValue(LoadingState.NOT_LOADING);
                onFailedListener.onComplete(null);
            }
        });
        return data;
    }

    @Override
    public MutableLiveData<LoadingState> getEventSpecialRandomRecipeLoadingState() {
        return this.EventSpecialRandomRecipeLoadingState;
    }
}
