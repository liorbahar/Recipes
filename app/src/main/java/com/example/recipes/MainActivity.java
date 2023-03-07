package com.example.recipes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.recipes.fragments.RecipesListFragment;
import com.example.recipes.fragments.RecipesListPageFragment;
import com.example.recipes.helper.models.ModelClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecipesListPageFragment myFrag = new RecipesListPageFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tran = manager.beginTransaction();

        tran.add(R.id.main_fragment_container, myFrag);
        tran.commit();

    }
}