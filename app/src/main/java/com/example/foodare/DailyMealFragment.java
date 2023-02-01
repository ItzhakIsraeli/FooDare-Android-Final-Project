package com.example.foodare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodare.model.Meal;
import com.example.foodare.model.MealModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DailyMealFragment extends Fragment {

    String mealName;
    String category;
    String area;
    String instructions;
    String mealImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_meal, container, false);

        LiveData<List<Meal>> data = MealModel.instance.getDailyMeal();

//        data.observe(getViewLifecycleOwner(), meal -> {
//            Log.d("MEAL", "meal");
//            mealName = meal.get(0).getStrMeal();
//            category = meal.get(0).getStrCategory();
//            area = meal.get(0).getStrArea();
//            instructions = meal.get(0).getStrInstructions();
//            mealImage = meal.get(0).getStrMealThumb();
//        });

        TextView mealTv = view.findViewById(R.id.daily_meal_name_tv);
        TextView categoryTv = view.findViewById(R.id.daily_meal_category_tv);
        TextView areaTv = view.findViewById(R.id.daily_meal_area_tv);
        TextView instructionsTv = view.findViewById(R.id.daily_meal_instructions_tv);
        ImageView imageUrlIV = view.findViewById(R.id.daily_meal_image);

        mealTv.setText(mealName);
        categoryTv.setText(category);
        areaTv.setText(area);
        instructionsTv.setText(instructions);

        if (mealImage != "") {
            Picasso.get().load(mealImage).placeholder(R.drawable.hamburger).into(imageUrlIV);
        } else {
            imageUrlIV.setImageResource(R.drawable.hamburger);
        }

        Button backBtn = view.findViewById(R.id.daily_meal_btn);

        backBtn.setOnClickListener((buttonView) -> {
            Navigation.findNavController(buttonView).popBackStack();
        });


        return view;
    }
}