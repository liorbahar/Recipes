package com.example.recipes.models;

import java.util.HashMap;
import java.util.Map;

import kotlin.NotImplementedError;

public class User {
    public static String COLLECTION_NAME = "users";

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
