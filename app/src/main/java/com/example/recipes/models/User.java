package com.example.recipes.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

import kotlin.NotImplementedError;


@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String id = "";
    public String name = "";
    public static String COLLECTION_NAME = "users";

    public User(){}
    public Map<String, Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        // example:
        // json.put("first", "Ada");
        // json.put("last", "Lovelace");
        // json.put("born", 1815);
        return json;
    }

    public User fromJson(Map<String, Object> json){
        throw new NotImplementedError();
    }
}
