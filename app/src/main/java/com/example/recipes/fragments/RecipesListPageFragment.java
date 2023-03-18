package com.example.recipes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.R;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecipesListPageFragment extends Fragment {
    private List<Recipe> recipes = new ArrayList<>();
    private RecipesListFragment recipesListFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipes_list_page, container, false);
        FragmentManager manager = getActivity().getSupportFragmentManager();

        ModelClient.instance().recipes.getAllRecipes().observe(getViewLifecycleOwner(), (List<Recipe> recipes) -> {
            this.recipes = recipes;

            if (this.recipesListFragment == null) {
                this.recipesListFragment = new RecipesListFragment();
            }

            if (!this.recipesListFragment.isAdded()) {
                this.recipesListFragment = new RecipesListFragment();
                FragmentTransaction tran = manager.beginTransaction();
                tran.add(R.id.fragment_recipes_list_page_container, this.recipesListFragment);
                tran.commit();
            }
            this.recipesListFragment.setRecipes(this.recipes);
            Bundle bundle = new Bundle();
            bundle.putBoolean("hasAccess", false);
            this.recipesListFragment.setArguments(bundle);
        });

        return view;
    }
}