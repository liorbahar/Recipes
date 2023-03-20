package com.example.recipes.fragments.recipes;

import com.example.recipes.R;
import com.example.recipes.databinding.FragmentBasePutRecipeBinding;
import com.example.recipes.helper.DialogsHelper;
import com.example.recipes.helper.ImageHelper;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;
import com.google.firebase.auth.FirebaseAuth;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.UUID;

import android.graphics.Bitmap;
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
    String recipeId = "";

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
            recipeId = BasePutRecipeFragmentArgs.fromBundle(getArguments()).getRecipeId();
            ModelClient.instance().recipes.getAllRecipes().observe(getViewLifecycleOwner(), (List<Recipe> recipes) -> {
                for (Recipe recipe : recipes) {
                    if (recipe.getId().equals(recipeId)) {
                        this.showRecipeDetails(recipe, binding);
                    }
                }
            });
        } catch (Exception e) {
        }

        binding.saveBtn.setOnClickListener(view1 -> {
            this.onSaveStudentClick(binding, view1);
        });

        binding.cancelBtn.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).popBackStack();
            Navigation.findNavController(view1).navigate(R.id.recipesListPageFragment);

        });
        setHasOptionsMenu(true);

        binding.cameraButton.setOnClickListener(view1 -> {
            cameraLauncher.launch(null);
        });

        binding.galleryButton.setOnClickListener(view1 -> {
            galleryLauncher.launch("image/*");
        });

        this.listenToBackButtonClick(view);

        return view;
    }

    private boolean onEditMode() {
        return !recipeId.isEmpty();
    }

    private void showRecipeDetails(Recipe recipe, FragmentBasePutRecipeBinding binding) {
        binding.basePutRecipeNameEt.setText(recipe.getName());
        binding.basePutRecipeBodyEt.setText(recipe.getBody());
        ImageHelper.insertImageByUrl(recipe, binding.addrecipeAvatarImv);
    }

    private void onSaveStudentClick(FragmentBasePutRecipeBinding binding, View view) {
        String name = binding.basePutRecipeNameEt.getText().toString();
        String body = binding.basePutRecipeBodyEt.getText().toString();
        String id = !recipeId.isEmpty() ? recipeId : UUID.randomUUID().toString();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Recipe recipe = new Recipe(id, name, body, userId, "");

        if (isAvatarSelected) {
            Bitmap bitmap = ImageHelper.getImageViewBitmap(binding.addrecipeAvatarImv);
            ModelClient.instance().uploadImage(id, bitmap, url -> {
                if (url != null) {
                    recipe.setAvatarUrl(url);
                }
                this.addRecipeAndNavigate(recipe, view);
            });
        } else {
            this.addRecipeAndNavigate(recipe, view);
        }
    }

    private void addRecipeAndNavigate(Recipe recipe, View view) {
        ModelClient.instance().recipes.addRecipe(recipe, (unused) -> {
            Navigation.findNavController(view).popBackStack();
            Navigation.findNavController(view).navigate(R.id.recipesUserListPageFragment);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
    }

    private void listenToBackButtonClick(View view) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (onEditMode()) {
                    Navigation.findNavController(view).popBackStack();
                } else {
                    DialogsHelper.getDialog(getContext(), getActivity()).show();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

}