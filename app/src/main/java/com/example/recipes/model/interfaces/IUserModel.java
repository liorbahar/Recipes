package com.example.recipes.model.interfaces;

import androidx.lifecycle.LiveData;

import com.example.recipes.model.ModelClient;
import com.example.recipes.model.UserModel;
import com.example.recipes.dto.User;

public interface IUserModel {
    public LiveData<User> getCurrentUser();

    public void refreshCurrentUser();

    public void clearUser();

    public void editUser(User user, ModelClient.Listener listener);

    public Boolean isUserLogIn();

    public void registerUser(final User user, String password, final AuthenticationListener listener);

    public void signOutUser();

    public void loginUser(final String email, String password, final AuthenticationListener listener);
}
