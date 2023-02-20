package com.example.recipes.models.handers;

import java.util.HashMap;
import java.util.Map;

public class User {
    public String Id;
    public String Name;

    public User(String id, String name){
        this.Id = id;
        this.Name = name;
    }

    public Map<String,Object> toJson(){
        Map<String,Object> json = new HashMap<String,Object>();
        json.put("id",this.Id);
        json.put("name",this.Name);
        return json;
    }

    public Recipe fromJson(){
        return null;
    }
}
