package com.example.recipes.helper.models;

import com.example.recipes.database.UserFirebaseHandler;
import com.example.recipes.localdatabase.AppLocalDbRepository;
import com.example.recipes.models.User;
import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.Executor;

public class UserModel {
    private final Executor executor;
    private final AppLocalDbRepository localDb;
    private UserFirebaseHandler userFirebase = new UserFirebaseHandler();
    private LiveData<List<User>> userData;

    public UserModel(Executor executor, AppLocalDbRepository localDb) {
        this.executor = executor;
        this.localDb = localDb;
    }


    public interface GetCurrentUserListener {
        void onComplete(User user);
    }

    public LiveData<List<User>> getCurrentUser() {
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

    public void editUser(User user, ModelClient.Listener listener) {
        this.userFirebase.editUser(user, listener);
    }

    public Boolean isUserLogIn() {
        return this.userFirebase.isUserLogIn();
    }

    /////////////////////// Auth //////////////////////////////////////////////////////
    public interface RegisterListener {
        void onComplete();

        void onFailed(String err);
    }

    public void registerUser(final User user, String password, final RegisterListener listener) {
        this.userFirebase.registerUser(user, password, listener);
    }

    public void signOutUser() {
        this.userFirebase.signOutUser();
    }

    public interface LoginListener extends RegisterListener {
    }

    public void loginUser(final String email, String password, final LoginListener listener) {
        this.userFirebase.loginUser(email, password, listener);
    }
}