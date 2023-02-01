package com.example.foodare.model;

import android.util.Log;

import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.auth.User;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class UserModel {

    private static final UserModel _instance = new UserModel();

    @PrimaryKey
    @NonNull
    public @androidx.annotation.NonNull
    String id = "";
    public String username = "";
    public Long lastUpdated;

    public UserModel(@androidx.annotation.NonNull String id, String username) {
        this.id = id;
        this.username = username;
    }

    public static UserModel instance() {
        return _instance;
    }


    public UserModel(){}

    static final String ID = "id";
    static final String USERNAME = "username";
    static final String COLLECTION = "users";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "users_local_last_update";

    public static UserModel fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String username = (String) json.get(USERNAME);
        UserModel user = new UserModel(id, username);

        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            user.setLastUpdated(time.getSeconds());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return user;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(USERNAME, getUsername());
        return json;
    }

    @androidx.annotation.NonNull
    public String getId() {
        return id;
    }

    public void setId(@androidx.annotation.NonNull String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
