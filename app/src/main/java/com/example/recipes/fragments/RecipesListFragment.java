package com.example.recipes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.example.recipes.R;
import com.example.recipes.databinding.ActivityMainBinding;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesListFragment extends Fragment {
    private List<Recipe> recipes;
    private Boolean hasAccess = true;
    ActivityMainBinding activityMainBinding;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.hasAccess = getArguments().getBoolean("hasAccess");
//            this.recipes = (ArrayList<Recipe>)getArguments().getSerializable("recipes");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_recipes_list, container, false);
        RecyclerView list = view.findViewById(R.id.recipes_list_fragment_ls);
        SearchView searchView = (SearchView) view.findViewById(R.id.fragment_recipes_list_search);

        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(getLayoutInflater(), this.recipes, this.hasAccess);
        list.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String recipeNameSearch) {
                List<Recipe> matchRecipes = searchRecipesByName(recipeNameSearch);
                RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(getLayoutInflater(), matchRecipes, hasAccess);
                list.setAdapter(adapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String recipeNameSearch) {
                List<Recipe> matchRecipes = searchRecipesByName(recipeNameSearch);
                RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(getLayoutInflater(), matchRecipes, hasAccess);
                list.setAdapter(adapter);
                return true;
            }
        });

        return view;
    }

    private List<Recipe> searchRecipesByName(String recipeName) {
        List<Recipe> matchRecipes = new ArrayList<Recipe>();
        for (Recipe recipe : recipes)
        {
            if (recipe.name.contains(recipeName)) {
                matchRecipes.add(recipe);
            }
        }
        return matchRecipes;
    }

    public void setRecipes(List<Recipe> recipes){
        this.recipes = recipes;

    }

}