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
import com.example.recipes.databinding.FragmentUserRecipesListPageBinding;
import com.example.recipes.dto.User;
import com.example.recipes.fragments.recipes.views.UserRecipesListPageFragmentViewModel;
import com.example.recipes.model.ModelClient;
import com.example.recipes.dto.Recipe;
import com.example.recipes.model.interfaces.LoadingState;
import java.util.List;

public class UserRecipesListPageFragment extends Fragment {
    private RecipesListFragment recipesListFragment;
    private UserRecipesListPageFragmentViewModel viewModel;
    private FragmentUserRecipesListPageBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserRecipesListPageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        this.showRecipesList();

        binding.swipeUserRecipesRefresh.setOnRefreshListener(() -> {
            ModelClient.instance().users.getCurrentUser().observe(getViewLifecycleOwner(), (User user) -> {
                ModelClient.instance().recipes.refreshUserRecipes(user.getId());
            });
        });

        ModelClient.instance().recipes.getEventUserRecipesListLoadingState().observe(getViewLifecycleOwner(), status -> {
            binding.swipeUserRecipesRefresh.setRefreshing(status == LoadingState.LOADING);

            if (status == LoadingState.NOT_LOADING) {
                this.viewModel.getRecipes().observe(getViewLifecycleOwner(), (List<Recipe> recipes) -> {
                    this.recipesListFragment.setRecipes(recipes);
                });
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
        this.recipesListFragment = new RecipesListFragment();

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_user_recipes_list_page_container, this.recipesListFragment)
                .commit();

        if (this.viewModel.getRecipes().getValue() != null) {
            this.recipesListFragment.setRecipes(this.viewModel.getRecipes().getValue());
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("hasAccess", true);
        this.recipesListFragment.setArguments(bundle);
    }


}