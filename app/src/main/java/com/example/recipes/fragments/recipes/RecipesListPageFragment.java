package com.example.recipes.fragments.recipes;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.recipes.R;
import com.example.recipes.databinding.FragmentRecipesListPageBinding;
import com.example.recipes.fragments.recipes.views.RecipesListPageFragmentViewModel;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.helper.models.RecipeModel;
import com.example.recipes.models.Recipe;

import java.util.List;

public class RecipesListPageFragment extends Fragment {
    private RecipesListFragment recipesListFragment;
    private RecipesListPageFragmentViewModel viewModel;
    private FragmentRecipesListPageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       //  Inflate the layout for this fragment
        binding = FragmentRecipesListPageBinding.inflate(inflater , container, false);
        View view = binding.getRoot();
        this.showRecipesList();

        binding.swipeRefresh.setOnRefreshListener(()->{
            ModelClient.instance().recipes.refreshAllRecipes();
        });

        ModelClient.instance().recipes.EventRecipesListLoadingState.observe(getViewLifecycleOwner(),status->{
            binding.swipeRefresh.setRefreshing(status == RecipeModel.LoadingState.LOADING);

            if (status == RecipeModel.LoadingState.NOT_LOADING){
                this.viewModel.getRecipes().observe(getViewLifecycleOwner(), (List<Recipe> recipes)-> {
                    this.recipesListFragment.setRecipes(recipes);
                });
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(RecipesListPageFragmentViewModel.class);
    }

    private void showRecipesList() {
        this.recipesListFragment = new RecipesListFragment();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_recipes_list_page_container, this.recipesListFragment)
                .commit();
        if (this.viewModel.getRecipes().getValue() != null) {
            this.recipesListFragment.setRecipes(this.viewModel.getRecipes().getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("hasAccess", false);
        this.recipesListFragment.setArguments(bundle);
    }
}