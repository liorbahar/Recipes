package com.example.recipes.fragments;

import com.example.recipes.databinding.FragmentBasePutRecipeBinding;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

public class BasePutRecipeFragment extends Fragment {
    FragmentBasePutRecipeBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    Boolean isAvatarSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.addrecipeAvatarImv.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.addrecipeAvatarImv.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBasePutRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.saveBtn.setOnClickListener(view1 -> {
            String name = binding.basePutRecipeNameEt.getText().toString();
            String body = binding.basePutRecipeBodyEt.getText().toString();
            String uniqueID = UUID.randomUUID().toString();
            Recipe recipe = new Recipe(uniqueID, name, body, "");

            if (isAvatarSelected) {
                binding.addrecipeAvatarImv.setDrawingCacheEnabled(true);
                binding.addrecipeAvatarImv.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.addrecipeAvatarImv.getDrawable()).getBitmap();
                Log.d("PIC", "url");
                ModelClient.instance().recipes.uploadImage(uniqueID, bitmap, url -> {
                    if (url != null) {
                        recipe.setAvatarUrl(url);
                    }
                    ModelClient.instance().recipes.addRecipe(recipe, (unused) -> {
                    });
                });
            } else {
                ModelClient.instance().recipes.addRecipe(recipe, (unused) -> {
                });
            }
        });

        binding.cameraButton.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });
        return view;
    }
}