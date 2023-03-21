package com.example.recipes.database;

import androidx.annotation.NonNull;

import com.example.recipes.database.interfaces.IUserDBHandler;
import com.example.recipes.model.ModelClient;
import com.example.recipes.model.UserModel;
import com.example.recipes.dto.User;
import com.example.recipes.model.interfaces.AuthenticationListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.Map;

public class UserFirebaseHandler implements IUserDBHandler {
    public FirebaseFirestore db;
    public FirebaseAuth firebaseAuth;

    public UserFirebaseHandler() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public void getCurrentUser(ModelClient.Listener<User> listener) {
        db.collection(User.COLLECTION_NAME).document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(task -> {
            User user = new User();
            if (task.isSuccessful()) {
                DocumentSnapshot doc = task.getResult();
                Map<String, Object> json = doc.getData();
                user = User.fromJson(json);
            }
            listener.onComplete(user);
        });
    }

    public void editUser(User user, ModelClient.Listener listener) {
        db.collection(User.COLLECTION_NAME).document(user.getId()).set(user.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });
    }

    public Boolean isUserLogIn() {
        return this.firebaseAuth.getInstance().getCurrentUser() != null;
    }


    public void registerUser(User user, String password, AuthenticationListener listener) {
        this.firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener(authResult -> {
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    user.setId(userId);
                    db.collection(User.COLLECTION_NAME).document(userId).set(user.toJson()).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                listener.onComplete();
                            }
                        });

                    }).addOnFailureListener(e -> {
                        listener.onFailed(e.getMessage());
                    });
                }

        public void signOutUser () {
            this.firebaseAuth.signOut();
        }

        public void loginUser (String email, String password, AuthenticationListener listener){
            this.firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    listener.onComplete();
                }

            }).addOnFailureListener(e -> {
                listener.onFailed(e.getMessage());
            });
        }
    }
