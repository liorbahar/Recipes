package com.example.recipes.fragments;

import com.example.recipes.R;
import com.example.recipes.databinding.FragmentViewRecipeBinding;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewRecipeFragment extends Fragment {
    FragmentViewRecipeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentViewRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String recipeId = UserRecipesListPageFragmentArgs.fromBundle(getArguments()).getRecipeId();
        Recipe data = ModelClient.instance().recipes.getRecipe(recipeId);
        showRecipeDetails(data, binding);

        return view;
    }

    private Void showRecipeDetails(Recipe recipe, FragmentViewRecipeBinding binding) {
        binding.recipeNameTv.setText(recipe.getName());
        //binding.recipeCreatorNameTv.setText(recipe.getUserId()); //get userId name from user collection
        binding.recipeBodyTv.setText(recipe.getBody());

        ImageView avatarImage = binding.recipeImage;

        if (!recipe.getAvatarUrl().equals("")) {
            Picasso.get().load(recipe.getAvatarUrl()).placeholder(R.drawable.add_image_avatar).into(avatarImage);
        } else {
            avatarImage.setImageResource(R.drawable.add_image_avatar);
        }

        return null;
    }
}