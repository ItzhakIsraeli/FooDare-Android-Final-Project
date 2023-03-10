package com.example.foodare.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.foodare.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Post {

    @PrimaryKey
    @NonNull
    public @androidx.annotation.NonNull
    String id = "";
    public String username = "";
    public String restaurant = "";
    public String meal = "";
    public String rate = "";
    public String description = "";
    public String imageUrl = "";
    public Long lastUpdated;
    public Boolean isDeleted = false;

    public Post() {

    }

    public Post(@androidx.annotation.NonNull String id, Boolean isDeleted, String username, String restaurant, String meal, String rate, String description, String imageUrl) {
        this.id = id;
        this.isDeleted = isDeleted;
        this.username = username;
        this.restaurant = restaurant;
        this.meal = meal;
        this.rate = rate;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    static final String COLLECTION = "posts";
    static final String ID = "id";
    static final String USERNAME = "username";
    static final String RESTAURANT = "restaurant";
    static final String MEAL = "meal";
    static final String RATE = "rate";
    static final String DESCRIPTION = "description";
    static final String IMAGE_URL = "imageUrl";
    static final String IS_DELETED = "isDeleted";

    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "posts_local_last_update";

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

    public static Post fromJson(Map<String, Object> json) {
        String id = (String) json.get(ID);
        String username = (String) json.get(USERNAME);
        String restaurant = (String) json.get(RESTAURANT);
        String meal = (String) json.get(MEAL);
        String rate = (String) json.get(RATE);
        String description = (String) json.get(DESCRIPTION);
        String imageUrl = (String) json.get(IMAGE_URL);
        Boolean isDeleted = (Boolean) json.get(IS_DELETED);

        Post post = new Post(id, isDeleted, username, restaurant, meal, rate, description, imageUrl);

        try {
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            post.setLastUpdated(time.getSeconds());
        } catch (Exception e) {
            Log.d("ERROR", e.toString());
        }
        return post;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put(ID, getId());
        json.put(IS_DELETED, getIsDeleted());
        json.put(USERNAME, getUsername());
        json.put(RESTAURANT, getRestaurant());
        json.put(MEAL, getMeal());
        json.put(RATE, getRate());
        json.put(DESCRIPTION, getDescription());
        json.put(IMAGE_URL, getImageUrl());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@androidx.annotation.NonNull String id) {
        this.id = id;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(String restaurant) {
        this.restaurant = restaurant;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
