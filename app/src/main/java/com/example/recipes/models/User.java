package com.example.recipes.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//import com.example.recipes.helper.models.interfaces.IImageModel;

import java.util.HashMap;
import java.util.Map;



@Entity
//public class User implements IImageModel {
public class User{

    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String email;
    public String avatarUrl = "";

    //@Ignore
    public User() {
    }

    public User(@NonNull String id, @NonNull String name, @NonNull String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    static final String ID = "id";
    static final String NAME = "name";
    static final String EMAIL = "email";
    public static String COLLECTION_NAME = "Users";

    public static User fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String name = (String) json.get(NAME);
        String email = (String) json.get(EMAIL);
        User user = new User(id, name, email);
        return user;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(NAME, getName());
        json.put(EMAIL, getEmail());
        return json;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
