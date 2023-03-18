package com.example.recipes.fragments.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;

import java.util.List;

public class RecipesListPageFragmentViewModel extends ViewModel {
    private LiveData<List<Recipe>> data = ModelClient.instance().recipes.getAllRecipes();

    public LiveData<List<Recipe>> getRecipes(){
        return data;
    }

}
