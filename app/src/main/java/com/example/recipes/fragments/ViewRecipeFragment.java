package com.example.recipes.fragments;

import com.example.recipes.databinding.FragmentViewRecipeBinding;
import com.example.recipes.helper.ImageHelper;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

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

        String recipeId = getArguments().getString("recipeId");
        Recipe data = ModelClient.instance().recipes.getRecipe(recipeId);
        showRecipeDetails(data, binding);

        return view;
    }

    private Void showRecipeDetails(Recipe recipe, FragmentViewRecipeBinding binding) {
        binding.recipeNameTv.setText(recipe.getName());
        //binding.recipeCreatorNameTv.setText(recipe.getUserId()); //get userId name from user collection
        binding.recipeBodyTv.setText(recipe.getBody());
        ImageHelper.insertImageByUrl(recipe,binding.recipeImage);

        return null;
    }
}