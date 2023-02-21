package com.example.recipes.helper.models;


import com.example.recipes.helper.models.handlers.UserModel;

public class ModelClient {
    private static final ModelClient _instance = new ModelClient();

    public UserModel users = new UserModel();

    public static ModelClient instance(){
        return _instance;
    }
    private ModelClient(){
    }
}
