package com.example.recipes.fragments.recipes.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipes.model.ModelClient;
import com.example.recipes.dto.Recipe;

import java.util.List;

public class UserRecipesListPageFragmentViewModel extends ViewModel {

    public LiveData<List<Recipe>> getRecipes(){
        return ModelClient.instance().recipes.getUserRecipes();
    }

}
