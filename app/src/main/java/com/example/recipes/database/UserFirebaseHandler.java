package com.example.recipes.database;

import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.helper.models.UserModel;
import com.example.recipes.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class UserFirebaseHandler {
    public static FirebaseFirestore db;
    public FirebaseAuth firebaseAuth;

    public UserFirebaseHandler() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        firebaseAuth = FirebaseAuth.getInstance();
    }


    public static void getUser(String id, final ModelClient.Listener<User> listener) {
        db.collection(User.COLLECTION_NAME).whereEqualTo("id", id)
                .get().addOnCompleteListener(task -> {
                    User user = null;
                    if (task.isSuccessful()) {
                        user = new User();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            user = User.fromJson(doc.getData());
                        }
                    }
                    listener.onComplete(user);
                });
    }

    public void registerUser(User user, String password, UserModel.RegisterListener listener) {
        firebaseAuth.createUserWithEmailAndPassword(user.email, password)
                .addOnSuccessListener(authResult -> {
                    db.collection(User.COLLECTION_NAME).document(firebaseAuth.getCurrentUser().getUid()).set(user.toJson()).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            listener.onComplete();
                        }
                    });

                }).addOnFailureListener(e -> {
                    listener.onFailed(e.getMessage());
                });
    }

    public void loginUser(String email, String password, UserModel.LoginListener listener) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                listener.onComplete();
            }

        }).addOnFailureListener(e -> {
            listener.onFailed(e.getMessage());
        });
    }
}
