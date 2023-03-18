package com.example.recipes.fragments.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.recipes.database.UserFirebaseHandler;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;
import com.example.recipes.models.User;

import java.util.List;

public class UserRecipesListPageFragmentViewModel extends ViewModel {
    private LiveData<List<Recipe>> data;
    private User currentUser;

    public UserRecipesListPageFragmentViewModel() {
//        new UserFirebaseHandler().getCurrentUser((User user) -> {
//            this.currentUser = user;
//        });
    }

    public LiveData<List<Recipe>> getRecipes(){
        String userId = "0lPwehYTFIXzWA06sqWpdREY8yN2";
        this.data = ModelClient.instance().recipes.getUserRecipes(userId);
        return this.data;
    }

}
