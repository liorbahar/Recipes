package com.example.recipes.database.interfaces;

import com.example.recipes.dto.User;
import com.example.recipes.model.ModelClient;
import com.example.recipes.model.UserModel;
import com.example.recipes.model.interfaces.AuthenticationListener;

public interface IUserDBHandler {
    void getCurrentUser(ModelClient.Listener<User> listener);

    void editUser(User user, ModelClient.Listener listener);

    Boolean isUserLogIn();

    void registerUser(User user, String password, AuthenticationListener listener);

    void signOutUser();

    void loginUser(String email, String password, AuthenticationListener listener);
}
