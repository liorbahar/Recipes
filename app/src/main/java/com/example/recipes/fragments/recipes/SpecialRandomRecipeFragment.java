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
import android.widget.Toast;

import com.example.recipes.databinding.FragmentSpecialRandomRecipeBinding;
import com.example.recipes.databinding.FragmentViewRecipeBinding;
import com.example.recipes.utils.ExistApplicationDialog;
import com.example.recipes.utils.ImageHelper;
import com.example.recipes.model.ModelClient;
import com.example.recipes.model.RecipeModel;
import com.example.recipes.dto.Recipe;

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
            recipeLiveData = this.fetchRandomRecipe();
            recipeLiveData.observe(getViewLifecycleOwner(), this::showRecipeDetails);
        });

        ModelClient.instance().randomRecipe.getEventSpecialRandomRecipeLoadingState().observe(getViewLifecycleOwner(),status->{
            binding.swipeSpecialRandomRefresh.setRefreshing(status == ModelClient.LoadingState.LOADING);

            if (status == ModelClient.LoadingState.NOT_LOADING){
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
        recipeLiveData = this.fetchRandomRecipe();
    }

    private LiveData<Recipe> fetchRandomRecipe(){
        ModelClient.Listener<Void> onFailedListener = data -> {
            Toast.makeText(getContext(),"Failed getting random recipe, please try again later",Toast.LENGTH_LONG).show();
        };
        return ModelClient.instance().randomRecipe.getRandomRecipe(onFailedListener);
    }

    private void listenToBackButtonClick() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                new ExistApplicationDialog(getContext(), getActivity()).show();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}