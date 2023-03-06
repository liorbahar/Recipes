package com.example.recipes.models;

import com.example.recipes.MyApplication;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Recipe {
    @PrimaryKey
    @NonNull
    public String id = "";
    public String name = "";
    public String body = "";
    public String avatarUrl = "";
    public Long lastUpdated;

    public Recipe() {
    }

    public Recipe(@NonNull String id, String name, String body, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.body = body;
        this.avatarUrl = avatarUrl;
    }

    static final String ID = "id";
    static final String NAME = "name";
    static final String BODY = "body";
    static final String AVATAR = "avatar";
    public static final String COLLECTION = "recipes";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "recipes_local_last_update";

    public static Recipe fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String name = (String) json.get(NAME);
        String body = (String) json.get(BODY);
        String avatar = (String) json.get(AVATAR);
        Recipe recipe = new Recipe(id, name, body, avatar);
        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            recipe.setLastUpdated(time.getSeconds());
        } catch (Exception e) {

        }
        return recipe;
    }

    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }

    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED, time);
        editor.commit();
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(NAME, getName());
        json.put(BODY, getBody());
        json.put(AVATAR, getAvatarUrl());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBody() {
        return body;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}