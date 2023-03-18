package com.example.recipes.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.R;
import com.example.recipes.fragments.views.RecipesListPageFragmentViewModel;
import com.example.recipes.fragments.views.UserRecipesListPageFragmentViewModel;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class UserRecipesListPageFragment extends Fragment {
    private List<Recipe> recipes = new ArrayList<>();
    private RecipesListFragment recipesListFragment;
    private FragmentTransaction tran;
    private UserRecipesListPageFragmentViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_recipes_list_page, container, false);
        String userId = "0lPwehYTFIXzWA06sqWpdREY8yN2";
        this.showRecipesList();

        this.viewModel.getRecipes().observe(getViewLifecycleOwner(), (List<Recipe> recipes)-> {
            if (recipes != null) {
                this.recipesListFragment.setRecipes(recipes);
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(UserRecipesListPageFragmentViewModel.class);
    }

    private void showRecipesList() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        if (this.recipesListFragment != null && this.recipesListFragment.isAdded()) {
            this.tran.remove(this.recipesListFragment);
        }

        this.tran = manager.beginTransaction();
        this.recipesListFragment = new RecipesListFragment();
        this.tran.add(R.id.fragment_user_recipes_list_page_container, this.recipesListFragment);
        this.tran.commit();

        if (this.viewModel.getRecipes().getValue() != null) {
            this.recipesListFragment.setRecipes(this.viewModel.getRecipes().getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("hasAccess", true);
        this.recipesListFragment.setArguments(bundle);
    }
}