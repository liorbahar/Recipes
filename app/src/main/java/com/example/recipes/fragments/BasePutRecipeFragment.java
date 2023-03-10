package com.example.recipes.fragments;

import com.example.recipes.R;
import com.example.recipes.databinding.FragmentBasePutRecipeBinding;
import com.example.recipes.helper.ImageHelper;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.navigation.Navigation;

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

        try {
            String recipeId = UserRecipesListPageFragmentArgs.fromBundle(getArguments()).getRecipeId();
            Recipe data = ModelClient.instance().recipes.getRecipe(recipeId);
            showRecipeDetails(data, binding);
        } catch (Exception e) {
        }

        binding.saveBtn.setOnClickListener(view1 -> {
            this.onSaveStudentClick(binding, view1);
        });

        binding.cancelBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.recipesListPageFragment, false));
        setHasOptionsMenu(true);

        binding.cameraButton.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });
        return view;
    }

    private Void showRecipeDetails(Recipe recipe, FragmentBasePutRecipeBinding binding) {
        binding.basePutRecipeNameEt.setText(recipe.getName());
        binding.basePutRecipeBodyEt.setText(recipe.getBody());
        ImageHelper.insertImageByUrl(recipe,binding.addrecipeAvatarImv);

        return null;
    }

    private void onSaveStudentClick(FragmentBasePutRecipeBinding binding, View view) {
        String name = binding.basePutRecipeNameEt.getText().toString();
        String body = binding.basePutRecipeBodyEt.getText().toString();
        String uniqueID = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString(); //change when have login user
        Recipe recipe = new Recipe(uniqueID, name, body, userId, "");

        if (isAvatarSelected) {
            binding.addrecipeAvatarImv.setDrawingCacheEnabled(true);
            binding.addrecipeAvatarImv.buildDrawingCache();
            Bitmap bitmap = ((BitmapDrawable) binding.addrecipeAvatarImv.getDrawable()).getBitmap();
            ModelClient.instance().recipes.uploadImage(uniqueID, bitmap, url -> {
                if (url != null) {
                    recipe.setAvatarUrl(url);
                }
                ModelClient.instance().recipes.addRecipe(recipe, (unused) -> {
                    Navigation.findNavController(view).popBackStack();
                });
            });
        } else {
            ModelClient.instance().recipes.addRecipe(recipe, (unused) -> {
                Navigation.findNavController(view).popBackStack();
            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
    }
}