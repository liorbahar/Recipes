package com.example.recipes.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

import kotlin.NotImplementedError;


@Entity
public class User {
    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String email;
    public long lastUpdated;

    //@Ignore
    public User() {
    }

    public User(@NonNull String id, @NonNull String name,@NonNull String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }

    static final String ID = "id";
    static final String NAME = "name";
    static final String EMAIL = "email";
    public static String COLLECTION_NAME = "users";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "recipes_local_last_update";

    public static User fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String name = (String) json.get(NAME);
        String email = (String) json.get(EMAIL);
        User user = new User(id, name, email);
        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            user.setLastUpdated(time.getSeconds());

        } catch (Exception e) {

        }
        return user;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(NAME, getName());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());

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

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
