package com.example.recipes.helper.models;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.recipes.localdatabase.AppLocalDb;
import com.example.recipes.localdatabase.AppLocalDbRepository;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ModelClient {
    private static final ModelClient _instance = new ModelClient();
    private Executor executor = Executors.newSingleThreadExecutor();
    private AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    public FirebaseStorage storage = FirebaseStorage.getInstance();


    public UserModel users;
    public RecipeModel recipes;


    public static ModelClient instance() {
        return _instance;
    }

    public interface Listener<T> {
        void onComplete(T data);
    }

    private ModelClient() {
        this.users = new UserModel(executor, localDb);
        this.recipes = new RecipeModel(executor, localDb);
    }

    public void uploadImage(String name, Bitmap bitmap, ModelClient.Listener<String> listener) {
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + name + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });
    }
}
