package com.example.recipes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;


import com.example.recipes.MainActivity;
import com.example.recipes.R;
import com.example.recipes.databinding.FragmentRecipesListBinding;
import com.example.recipes.models.Recipe;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class RecipesListFragment extends Fragment {
    private List<Recipe> recipes = new ArrayList<>();
    private Boolean hasAccess = true;
    private FragmentRecipesListBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.hasAccess = getArguments().getBoolean("hasAccess");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRecipesListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ((MainActivity) getActivity()).setBottomNavigationVisibility(view.VISIBLE);
        ((MainActivity) getActivity()).showSupportActionBar();

        binding.recipesListFragmentLs.setHasFixedSize(true);
        binding.recipesListFragmentLs.setLayoutManager(new LinearLayoutManager(getContext()));
        RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(getLayoutInflater(), this.recipes, this.hasAccess);
        binding.recipesListFragmentLs.setAdapter(adapter);

        adapter.setOnItemClickListener(new RecipeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String recipeId) {
                if (hasAccess) {
                    UserRecipesListPageFragmentDirections.ActionUserRecipesListFragmentToViewRecipesFragment action = UserRecipesListPageFragmentDirections.actionUserRecipesListFragmentToViewRecipesFragment(recipeId);
                    Navigation.findNavController(view).navigate(action);
                } else {
                    RecipesListPageFragmentDirections.ActionRecipesListPageFragmentToViewRecipesFragment action1 = RecipesListPageFragmentDirections.actionRecipesListPageFragmentToViewRecipesFragment(recipeId);
                    Navigation.findNavController(view).navigate(action1);
                }
            }
        });

        adapter.setOnEditButtonClickListener(new RecipeRecyclerAdapter.OnEditButtonClickListener() {
            @Override
            public void onItemClick(String recipeId) {
                //get userId from context when have login
                UserRecipesListPageFragmentDirections.ActionRecipesListFragmentToEditRecipesFragment action = UserRecipesListPageFragmentDirections.actionRecipesListFragmentToEditRecipesFragment(recipeId);
                Navigation.findNavController(view).navigate(action);
            }
        });


        binding.fragmentRecipesListSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String recipeNameSearch) {
                List<Recipe> matchRecipes = searchRecipesByName(recipeNameSearch);
                RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(getLayoutInflater(), matchRecipes, hasAccess);
                binding.recipesListFragmentLs.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String recipeNameSearch) {
                List<Recipe> matchRecipes = searchRecipesByName(recipeNameSearch);
                RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(getLayoutInflater(), matchRecipes, hasAccess);
                binding.recipesListFragmentLs.setAdapter(adapter);
                return true;
            }
        });

        return view;
    }

    private List<Recipe> searchRecipesByName(String recipeName) {
        List<Recipe> matchRecipes = new ArrayList<Recipe>();
        for (Recipe recipe : this.recipes) {
            if (recipe.name.contains(recipeName)) {
                matchRecipes.add(recipe);
            }
        }
        return matchRecipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}