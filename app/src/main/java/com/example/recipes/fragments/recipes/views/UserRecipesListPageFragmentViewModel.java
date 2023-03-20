package com.example.recipes.fragments.recipes.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipes.model.ModelClient;
import com.example.recipes.dto.Recipe;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class UserRecipesListPageFragmentViewModel extends ViewModel {
    private LiveData<List<Recipe>> recipes = ModelClient.instance().recipes.getUserRecipes(
            FirebaseAuth.getInstance().getCurrentUser().getUid());


    public LiveData<List<Recipe>> getRecipes(){
        return this.recipes;
    }

}
