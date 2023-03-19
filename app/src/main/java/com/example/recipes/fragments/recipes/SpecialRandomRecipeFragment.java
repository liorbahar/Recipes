package com.example.recipes.fragments.recipes;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.databinding.FragmentSpecialRandomRecipeBinding;
import com.example.recipes.databinding.FragmentViewRecipeBinding;
import com.example.recipes.helper.DialogsHelper;
import com.example.recipes.helper.ImageHelper;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.helper.models.RecipeModel;
import com.example.recipes.models.Recipe;

public class SpecialRandomRecipeFragment extends Fragment {
    FragmentSpecialRandomRecipeBinding binding;
    FragmentViewRecipeBinding fragmentViewRecipeBinding;
    LiveData<Recipe> recipeLiveData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSpecialRandomRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        fragmentViewRecipeBinding = FragmentViewRecipeBinding.bind(view);

        binding.swipeSpecialRandomRefresh.setOnRefreshListener(()->{
            recipeLiveData = ModelClient.instance().recipes.getRandomRecipe();
            recipeLiveData.observe(getViewLifecycleOwner(), this::showRecipeDetails);
        });

        ModelClient.instance().recipes.EventSpecialRandomRecipeLoadingState.observe(getViewLifecycleOwner(),status->{
            binding.swipeSpecialRandomRefresh.setRefreshing(status == RecipeModel.LoadingState.LOADING);

            if (status == RecipeModel.LoadingState.NOT_LOADING){
                recipeLiveData.observe(getViewLifecycleOwner(), this::showRecipeDetails);
            }
        });

        this.listenToBackButtonClick();

        return view;
    }

    private void showRecipeDetails(Recipe recipe) {
        fragmentViewRecipeBinding.recipeNameTv.setText(recipe.getName());
        fragmentViewRecipeBinding.recipeBodyTv.setText(recipe.getBody());
        ImageHelper.insertImageByUrl(recipe, fragmentViewRecipeBinding.recipeImage);
        fragmentViewRecipeBinding.recipeNameTv.setEnabled(false);
        fragmentViewRecipeBinding.recipeBodyTv.setEnabled(false);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        recipeLiveData = ModelClient.instance().recipes.getRandomRecipe();
    }

    private void listenToBackButtonClick() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                DialogsHelper.getDialog(getContext(), getActivity()).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}