package com.example.recipes.fragments.recipes.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipes.model.ModelClient;
import com.example.recipes.dto.Recipe;

import java.util.List;

public class RecipesListPageFragmentViewModel extends ViewModel {
    private LiveData<List<Recipe>> recipes = ModelClient.instance().recipes.getAllRecipes();

    public LiveData<List<Recipe>> getRecipes(){
        return recipes;
    }

}
