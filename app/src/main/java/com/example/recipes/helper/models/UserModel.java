package com.example.recipes.helper.models;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.recipes.MyApplication;
import com.example.recipes.database.UserFirebaseHandler;
import com.example.recipes.localdatabase.AppLocalDb;
import com.example.recipes.localdatabase.AppLocalDbRepository;
import com.example.recipes.models.User;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;

import androidx.lifecycle.LiveData;

import java.util.concurrent.Executor;

public class UserModel {
    private final Executor executor;
    private final AppLocalDbRepository localDb;
    private UserFirebaseHandler userFirebase = new UserFirebaseHandler();

    public UserModel(Executor executor, AppLocalDbRepository localDb) {
        this.executor = executor;
        this.localDb = localDb;
    }

    public interface CompListener {
        void onComplete();
    }

    public LiveData<User> getUser() {
        refreshUser(null);
        LiveData<User> liveData = AppLocalDb.getAppDb().UserDao().getAll();
        return liveData;
    }

    //change to be like recipeModel
    public void refreshUser(final CompListener listener) {
        userFirebase.getUser(FirebaseAuth.getInstance().getCurrentUser().getUid(), new ModelClient.Listener<User>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onComplete(final User data) {
                new AsyncTask<String, String, String>() {
                    @Override
                    protected String doInBackground(String... strings) {
                        localDb.UserDao().insertAll(data);

                        SharedPreferences.Editor edit = MyApplication.getMyContext().getSharedPreferences("TAG", MODE_PRIVATE).edit();
                        edit.apply();
                        return "";
                    }

                    @Override
                    protected void onPostExecute(String s) {
                        super.onPostExecute(s);
                        if (listener != null) listener.onComplete();
                    }
                }.execute("");
            }
        });
    }

    /////////////////////// Auth //////////////////////////////////////////////////////
    public interface RegisterListener {
        void onComplete();

        void onFailed(String err);
    }

    public void registerUser(final User user, String password, final RegisterListener listener) {
        userFirebase.registerUser(user, password, listener);
    }

    public interface LoginListener extends RegisterListener {
    }

    public void loginUser(final String email, String password, final LoginListener listener) {
        userFirebase.loginUser(email, password, listener);
    }
}