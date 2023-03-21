package com.example.recipes.fragments.recipes;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;


import com.example.recipes.MainActivity;
import com.example.recipes.databinding.FragmentRecipesListBinding;
import com.example.recipes.utils.ExistApplicationDialog;
import com.example.recipes.dto.Recipe;
import com.example.recipes.model.ModelClient;
import java.util.concurrent.Executors;

import java.util.ArrayList;
import java.util.List;

public class RecipesListFragment extends Fragment {
    private List<Recipe> recipes = new ArrayList<>();
    private Boolean hasAccess = true;
    private FragmentRecipesListBinding binding;
    private RecipeRecyclerAdapter adapter;

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
        this.adapter = new RecipeRecyclerAdapter(getLayoutInflater(), this.recipes, this.hasAccess);
        binding.recipesListFragmentLs.setAdapter(this.adapter);

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

        adapter.setOnDeleteButtonClickListener(new RecipeRecyclerAdapter.OnDeleteButtonClickListener() {
            @Override
            public void onItemClick(String recipeId) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> ModelClient.instance().recipes.getAllRecipes().observe(getViewLifecycleOwner(), (List<Recipe> recipes) -> {
                    for (Recipe recipe : recipes) {
                        if (recipe.getId().equals(recipeId)) {
                            Executors.newSingleThreadExecutor().execute(new Runnable() {
                                @Override
                                public void run() {
                                    ModelClient.instance().recipes.removeRecipe(recipe, (unused) -> {
                                    });
                                }
                            });
                        }
                    }
                }));
            }
        });

        binding.fragmentRecipesListSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String recipeNameSearch) {
                List<Recipe> matchRecipes = searchRecipesByName(recipeNameSearch);
                adapter.setRecipes(matchRecipes);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String recipeNameSearch) {
                List<Recipe> matchRecipes = searchRecipesByName(recipeNameSearch);
                adapter.setRecipes(matchRecipes);
                return true;
            }
        });

        this.listenToBackButtonClick();

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
        if (this.adapter != null) {
            this.adapter.setRecipes(recipes);
        }
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