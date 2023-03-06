package com.example.recipes.database;

import com.example.recipes.database.interfaces.IRecipesDBHandler;
import com.example.recipes.helper.models.RecipeModel;
import com.example.recipes.models.Recipe;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;


public class RecipesFirebaseHandler implements IRecipesDBHandler {
    public FirebaseFirestore db;
    public FirebaseStorage storage;

    public RecipesFirebaseHandler() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();
    }

    public void addRecipe(Recipe recipe, RecipeModel.AddRecipeListener listener) {
        db.collection(recipe.COLLECTION).document(recipe.getId()).set(recipe.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete();
                    }
                });
    }
}
