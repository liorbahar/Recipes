package com.example.recipes.model.database.handlers;

import com.example.recipes.model.database.handlers.interfaces.IRecipesDBHandler;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;

public class RecipesFirebaseHandler implements IRecipesDBHandler{
    public FirebaseFirestore db;
    public FirebaseStorage storage;

    public RecipesFirebaseHandler(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();
    }
}
