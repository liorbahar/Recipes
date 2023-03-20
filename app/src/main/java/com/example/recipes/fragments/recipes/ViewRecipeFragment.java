package com.example.recipes.fragments.recipes;

import com.example.recipes.databinding.FragmentViewRecipeBinding;
import com.example.recipes.utils.ImageHelper;
import com.example.recipes.model.ModelClient;
import com.example.recipes.dto.Recipe;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewRecipeFragment extends Fragment {
    FragmentViewRecipeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        this.listenToBackButtonClick(view);

        String recipeId = getArguments().getString("recipeId");
        Recipe data = ModelClient.instance().recipes.getRecipe(recipeId);
        this.showRecipeDetails(data, binding);
        return view;
    }

    private void showRecipeDetails(Recipe recipe, FragmentViewRecipeBinding binding) {
        binding.recipeNameTv.setText(recipe.getName());
        binding.recipeBodyTv.setText(recipe.getBody());
        ImageHelper.insertImageByUrl(recipe, binding.recipeImage);

        binding.recipeNameTv.setEnabled(false);
        binding.recipeBodyTv.setEnabled(false);
    }

    private void listenToBackButtonClick(View view) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(view).popBackStack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}