package com.example.recipes.fragments.recipes.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipes.database.UserFirebaseHandler;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;
import com.example.recipes.models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class UserRecipesListPageFragmentViewModel extends ViewModel {
    private LiveData<List<Recipe>> recipes = ModelClient.instance().recipes.getUserRecipes(
            FirebaseAuth.getInstance().getCurrentUser().getUid());


    public LiveData<List<Recipe>> getRecipes(){
        return this.recipes;
    }

}
