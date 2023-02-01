package com.example.foodare.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealModel {
    final public static MealModel instance = new MealModel();

    final String BASE_URL = "https://www.themealdb.com/";
    Retrofit retrofit;
    MealApi mealApi;

    private MealModel() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        mealApi = retrofit.create(MealApi.class);
        Log.d("MEAL", "in the meal model func");
    }

    public LiveData<List<Meal>> getDailyMeal() {
        MutableLiveData<List<Meal>> data = new MutableLiveData<>();
        Call<MealSearchResult> call = mealApi.getDailyMeal();
        call.enqueue(new Callback<MealSearchResult>() {
            @Override
            public void onResponse(Call<MealSearchResult> call, Response<MealSearchResult> response) {
                if (response.isSuccessful()) {
                    MealSearchResult res = response.body();
                    data.setValue(res.getSearch());
                } else {
                    Log.d("MEAL", "----- searchMealsByTitle response error");
                }
            }

            @Override
            public void onFailure(Call<MealSearchResult> call, Throwable t) {
                Log.d("MEAL", "----- searchMealsByTitle fail");
            }
        });
        return data;
    }

}
