package com.example.foodare.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealSearchResult {
    @SerializedName("meals")
    List<Meal> meals;

    public List<Meal> getSearch() {
        return meals;
    }

    public void setSearch(List<Meal> meals) {
        this.meals = meals;
    }
}
