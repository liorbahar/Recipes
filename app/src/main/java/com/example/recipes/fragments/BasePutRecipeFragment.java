package com.example.recipes.fragments;

import com.example.recipes.databinding.FragmentBasePutRecipeBinding;
import com.example.recipes.helper.models.ModelClient;
import com.example.recipes.models.Recipe;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

public class BasePutRecipeFragment extends Fragment {
    FragmentBasePutRecipeBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBasePutRecipeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.saveBtn.setOnClickListener(view1 -> {
            String name = binding.basePutRecipeNameEt.getText().toString();
            String body = binding.basePutRecipeBodyEt.getText().toString();
            String uniqueID = UUID.randomUUID().toString();
            Recipe recipe = new Recipe(uniqueID, name, body, "");

            ModelClient.instance().recipes.addRecipe(recipe, () -> {
            });
        });

        return view;
    }
}