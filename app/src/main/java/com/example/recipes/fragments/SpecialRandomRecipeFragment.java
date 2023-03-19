package com.example.recipes.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.databinding.FragmentSpecialRandomRecipeBinding;
import com.example.recipes.helper.ImageHelper;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.helper.models.RecipeModel;
import com.example.recipes.models.Recipe;

public class SpecialRandomRecipeFragment extends Fragment {
    FragmentSpecialRandomRecipeBinding binding;
    LiveData<Recipe> recipeLiveData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSpecialRandomRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.swipeSpecialRandomRefresh.setOnRefreshListener(()->{
            recipeLiveData = ModelClient.instance().recipes.getRandomRecipe();
            recipeLiveData.observe(getViewLifecycleOwner(), this::showRecipeDetails);
        });

        ModelClient.instance().recipes.EventSpecialRandomRecipeLoadingState.observe(getViewLifecycleOwner(),status->{
            binding.swipeSpecialRandomRefresh.setRefreshing(status == RecipeModel.LoadingState.LOADING);

            if (status == RecipeModel.LoadingState.NOT_LOADING){
                recipeLiveData.observe(getViewLifecycleOwner(), this::showRecipeDetails);            }
        });

        return view;
    }

    private Void showRecipeDetails(Recipe recipe) {
        binding.recipeNameTv.setText(recipe.getName());
        binding.recipeBodyTv.setText(recipe.getBody());
        ImageHelper.insertImageByUrl(recipe, binding.recipeImage);
        binding.recipeNameTv.setEnabled(false);
        binding.recipeBodyTv.setEnabled(false);
        return null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        recipeLiveData = ModelClient.instance().recipes.getRandomRecipe();
    }
}