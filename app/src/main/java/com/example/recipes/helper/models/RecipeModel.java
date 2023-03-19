package com.example.recipes.helper.models;

import java.util.concurrent.Executor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.recipes.database.RecipesFirebaseHandler;
import com.example.recipes.helper.models.interfaces.IRecipeModel;
import com.example.recipes.localdatabase.AppLocalDbRepository;
import com.example.recipes.models.api.RandomRecipeApiResult;
import com.example.recipes.models.Recipe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeModel implements IRecipeModel {
    private final Executor executor;
    private final AppLocalDbRepository localDb;
    private RecipesFirebaseHandler recipesFirebaseHandler = new RecipesFirebaseHandler();
    private LiveData<List<Recipe>> recipesList;
    private LiveData<List<Recipe>> userRecipesList;

    Retrofit retrofit;
    RecipeApi recipeApi;


    public RecipeModel(Executor executor, AppLocalDbRepository localDb) {
        this.executor = executor;
        this.localDb = localDb;
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spoonacular.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        recipeApi = retrofit.create(RecipeApi.class);
    }

    public interface GetAllRecipesListener {
        void onComplete(List<Recipe> data);
    }

    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }
    final public MutableLiveData<LoadingState> EventRecipesListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);
    final public MutableLiveData<LoadingState> EventUserRecipesListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);
    final public MutableLiveData<LoadingState> EventSpecialRandomRecipeLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);


    public LiveData<List<Recipe>> getUserRecipes(String userId) {
        if (this.userRecipesList == null) {
            this.userRecipesList = this.localDb.recipesDao().getRecipesByUserId(userId);
            this.refreshUserRecipes(userId);
        }
        return this.userRecipesList;
    }

    public void addRecipe(Recipe recipe, ModelClient.Listener listener) {
        recipesFirebaseHandler.addRecipe(recipe, listener);
        this.refreshUserRecipes(recipe.userId);
    }

    public void refreshUserRecipes(String userId) {
        EventUserRecipesListLoadingState.setValue(LoadingState.LOADING);
        this.recipesFirebaseHandler.getRecipesOfUser(userId, (List<Recipe> recipes) -> {
            executor.execute(() -> {
                for (Recipe recipe : recipes) {
                    localDb.recipesDao().insertAll(recipe);
                }
                EventUserRecipesListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public LiveData<List<Recipe>> getAllRecipes() {
        if (this.recipesList == null) {
            this.recipesList = this.localDb.recipesDao().getAll();
            this.refreshAllRecipes();
        }
        return this.recipesList;
    }

    public Recipe getRecipe(String recipeId) {
        if (this.recipesList == null) {
            this.recipesList = this.localDb.recipesDao().getAll();
            this.refreshAllRecipes();
        }
        for (Recipe recipe : this.recipesList.getValue()) {
            if (recipe.getId().equals(recipeId)) {
                return recipe;
            }
        }

        return null;
    }


    public void refreshAllRecipes() {
        EventRecipesListLoadingState.setValue(LoadingState.LOADING);
        this.recipesFirebaseHandler.getAllRecipes((List<Recipe> recipes) -> {
            executor.execute(() -> {
                for (Recipe recipe : recipes) {
                    localDb.recipesDao().insertAll(recipe);
                }
                EventRecipesListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }


        public LiveData<Recipe> getRandomRecipe(){
            EventSpecialRandomRecipeLoadingState.setValue(LoadingState.LOADING);
            MutableLiveData<Recipe> data = new MutableLiveData<>();
            Call<RandomRecipeApiResult> call = this.recipeApi.getRandomRecipe();
            call.enqueue(new Callback<RandomRecipeApiResult>() {
                @Override
                public void onResponse(Call<RandomRecipeApiResult> call, Response<RandomRecipeApiResult> response) {
                    if (response.isSuccessful()){
                        RandomRecipeApiResult res = response.body();
                        data.setValue(res.getRecipe().toRecipe());
                    }
                    EventSpecialRandomRecipeLoadingState.postValue(LoadingState.NOT_LOADING);
                }

                @Override
                public void onFailure(Call<RandomRecipeApiResult> call, Throwable t) {
                    EventSpecialRandomRecipeLoadingState.postValue(LoadingState.NOT_LOADING);
                }
            });
            return data;
        }

}