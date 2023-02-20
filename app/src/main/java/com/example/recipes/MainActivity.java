package com.example.recipes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.recipes.model.models.ModelClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ModelClient.instance().users.AddUser();

    }
}