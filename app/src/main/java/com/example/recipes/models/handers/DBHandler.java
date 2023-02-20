package com.example.recipes.models.handers;

import kotlin.NotImplementedError;

public class DBHandler {
    private static final DBHandler _instance = new DBHandler();

    public static DBHandler instance() {
        return _instance;
    }

    private DBHandler() {
    }

    public User GetUser(String userUuid){
        throw new NotImplementedError();
    }

    public void UpdateUser(String userUuid, User user){
        throw new NotImplementedError();
    }

    public void CreateUser(){
    }

}
