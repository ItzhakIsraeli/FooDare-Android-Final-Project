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
    String mail = "";
    public String username = "";
    public String phone = "";
    public String age = "";
    public String password = "";
    public Long lastUpdated;
    private String imageUrl = "";

    public UserModel(@androidx.annotation.NonNull String mail, String username, String age, String phone, String password, String imageUrl) {
        this.mail = mail;
        this.username = username;
        this.age = age;
        this.phone = phone;
        this.password = password;
        this.imageUrl = imageUrl;
    }

    public static UserModel instance() {
        return _instance;
    }

    public UserModel() {
    }

    static final String MAIL = "mail";
    static final String USERNAME = "username";
    static final String AGE = "age";
    static final String PHONE = "phone";
    static final String PASSWORD = "password";
    static final String COLLECTION = "users";
    static final String LAST_UPDATED = "lastUpdated";
    static final String IMAGE_URL = "imageUrl";
    static final String LOCAL_LAST_UPDATED = "users_local_last_update";

    public static UserModel fromJson(Map<String, Object> json) {
        String mail = (String) json.get(MAIL);
        String username = (String) json.get(USERNAME);
        String age = (String) json.get(AGE);
        String phone = (String) json.get(PHONE);
        String password = (String) json.get(PASSWORD);
        String imageUrl = (String) json.get(IMAGE_URL);
        UserModel user = new UserModel(mail, username, age, phone, password, imageUrl);

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
        json.put(MAIL, getMail());
        json.put(USERNAME, getUsername());
        json.put(AGE, getAge());
        json.put(PHONE, getPhone());
        json.put(PASSWORD, getPassword());
        json.put(IMAGE_URL, getImageUrl());
        return json;
    }

    @androidx.annotation.NonNull
    public String getMail() {
        return mail;
    }

    public void setMail(@androidx.annotation.NonNull String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
