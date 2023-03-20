package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.appcompat.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.recipes.helper.models.ModelClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration
                .Builder(R.id.recipesListPageFragment,R.id.recipesUserListPageFragment ,R.id.addRecipeFragment, R.id.specialRecipe, R.id.viewUserProfileFragment)
                .build();

        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        BottomNavigationView navView = findViewById(R.id.main_bottomNavigationView);
        NavigationUI.setupWithNavController(navView, navController);

        this.changeActionBarBackgroundColor();

        if (ModelClient.instance().users.isUserLogIn()) {
            navController.navigate(R.id.action_loginFragment_to_recipesListPageFragment);
        }
    }

    int fragmentMenuId = 0;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if (fragmentMenuId != 0) {
            menu.removeItem(fragmentMenuId);
        }
        fragmentMenuId = 0;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            navController.popBackStack();
        } else {
            fragmentMenuId = item.getItemId();
            return NavigationUI.onNavDestinationSelected(item, navController);
        }
        return super.onOptionsItemSelected(item);
    }

    public void setBottomNavigationVisibility(int visibilityNum) {
        this.findViewById(R.id.main_bottomNavigationView).setVisibility(visibilityNum);
    }

    public void hideSupportActionBar() {
        getSupportActionBar().hide();
    }

    public void showSupportActionBar() {
        getSupportActionBar().show();
    }

    public void changeActionBarBackgroundColor() {
        ActionBar actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#085063"));
        actionBar.setBackgroundDrawable(colorDrawable);
    }

}