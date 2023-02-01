package com.example.foodare.model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealApi {
    @GET("/api/json/v1/1/random.php/")
    Call<MealSearchResult> getDailyMeal();
}
