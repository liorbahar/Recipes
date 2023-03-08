package com.example.recipes.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.recipes.R;
import com.example.recipes.databinding.FragmentEditRecipeBinding;
import com.example.recipes.models.Recipe;
import com.squareup.picasso.Picasso;

public class EditRecipeFragment extends BasePutRecipeFragment {
    FragmentEditRecipeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Recipe recipe = new Recipe("uniqueID", "name", "body", "userId", "https://firebasestorage.googleapis.com/v0/b/recipes-5fd46.appspot.com/o/images%2Fead52b5e-5f22-48e5-a2c7-593b679b171a.jpg?alt=media&token=1f5867d7-5d8a-4718-8b26-c2de5cac549b");
        //get Recipe from parent when click

        binding = FragmentEditRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        EditText nameEt = view.findViewById(R.id.basePutRecipeNameEt);
        EditText bodyEt = view.findViewById(R.id.basePutRecipeBodyEt);
        ImageView avatarImage = view.findViewById(R.id.addrecipeAvatarImv);
        nameEt.setText(recipe.getName());
        bodyEt.setText(recipe.getBody());

        if (recipe.getAvatarUrl() != "") {
            Picasso.get().load(recipe.getAvatarUrl()).placeholder(R.drawable.add_image_avatar).into(avatarImage);
        } else {
            avatarImage.setImageResource(R.drawable.add_image_avatar);
        }

        BasePutRecipeFragment parentFrag = ((BasePutRecipeFragment)EditRecipeFragment.this.getParentFragment());

        return view;
    }
}