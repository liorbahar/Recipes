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

import java.util.ArrayList;
import java.util.List;

public class UserRecipesListPageFragment extends Fragment {
    private List<Recipe> recipes = new ArrayList<>();
    private RecipesListFragment recipesOwnListFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_recipes_list_page, container, false);

        FragmentManager manager = getActivity().getSupportFragmentManager();
        String userId = "b9800fd2-1671-40f2-bb15-48f0b83ede46";
        ModelClient.instance().recipes.getUserRecipes(userId).observe(getViewLifecycleOwner(), (List<Recipe> recipes)-> {
            this.recipes = recipes;

            if (this.recipesOwnListFragment == null){
                this.recipesOwnListFragment = new RecipesListFragment();
            }

            if (!this.recipesOwnListFragment.isAdded()) {
                getFragmentManager().executePendingTransactions();
                this.recipesOwnListFragment = new RecipesListFragment();
                FragmentTransaction tran = manager.beginTransaction();
                tran.add(R.id.fragment_recipes_list_page_container, this.recipesOwnListFragment);
                tran.commit();
            }
            this.recipesOwnListFragment.setRecipes(this.recipes);
            Bundle bundle = new Bundle();
            bundle.putBoolean("hasAccess", true);
            this.recipesOwnListFragment.setArguments(bundle);
        });

        return view;
    }
}