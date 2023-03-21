package com.example.recipes.model;

import com.example.recipes.database.UserFirebaseHandler;
import com.example.recipes.database.interfaces.IUserDBHandler;
import com.example.recipes.model.interfaces.AuthenticationListener;
import com.example.recipes.model.interfaces.IUserModel;
import com.example.recipes.cache.AppLocalDbRepository;
import com.example.recipes.dto.User;
import androidx.lifecycle.LiveData;
import java.util.concurrent.Executor;

public class UserModel implements IUserModel {
    private final Executor executor;
    private final AppLocalDbRepository localDb;
    private IUserDBHandler userFirebase = new UserFirebaseHandler();
    private LiveData<User> userData;

    public UserModel(Executor executor, AppLocalDbRepository localDb) {
        this.executor = executor;
        this.localDb = localDb;
    }

    public LiveData<User> getCurrentUser() {
        if (this.userData == null) {
            this.userData = this.localDb.UserDao().getAll();
            this.refreshCurrentUser();
        }
        return this.userData;
    }

    public void refreshCurrentUser() {
        this.userFirebase.getCurrentUser((User user) -> {
            executor.execute(() -> {
                this.localDb.UserDao().insertAll(user);
            });
        });
    }

    public void clearUser() {
        executor.execute(() -> {
            this.localDb.UserDao().delete();
        });
    }

    public void editUser(User user, ModelClient.Listener listener) {
        this.userFirebase.editUser(user, listener);
    }

    public Boolean isUserLogIn() {
        return this.userFirebase.isUserLogIn();
    }

    public void registerUser(final User user, String password, final AuthenticationListener listener) {
        this.userFirebase.registerUser(user, password, listener);
    }

    public void signOutUser() {
        this.userFirebase.signOutUser();
    }

    public void loginUser(final String email, String password, final AuthenticationListener listener) {
        this.userFirebase.loginUser(email, password, listener);
    }
}