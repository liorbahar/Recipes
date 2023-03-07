package com.example.recipes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.R;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;

import java.util.List;

public class RecipesListFragment extends Fragment {
    private List<Recipe> recipes;
    private Boolean hasAccess = true;


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
        View view =  inflater.inflate(R.layout.fragment_recipes_list, container, false);
        RecyclerView list = view.findViewById(R.id.recipes_list_fragment_ls);

        ModelClient.instance().recipes.getAllRecipes(recipes -> {
                this.recipes = recipes;

                list.setHasFixedSize(true);
                list.setLayoutManager(new LinearLayoutManager(getContext()));
                RecipeRecyclerAdapter adapter = new RecipeRecyclerAdapter(getLayoutInflater(), this.recipes, this.hasAccess);
                list.setAdapter(adapter);
        });
        return view;
    }
}