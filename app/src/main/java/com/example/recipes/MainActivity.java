package com.example.recipes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

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

    @Override
    public void onBackPressed() {
        int id = navController.getCurrentDestination().getId();

        switch (id){
            case R.id.loginFragment:
                finish();
                break;
            case R.id.registerFragment:
                navController.popBackStack();
                navController.navigate(R.id.loginFragment);
                break;
            default:
                //navController.popBackStack();
                navController.navigate(R.id.recipesListPageFragment);
        }
    }
}