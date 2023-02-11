package com.example.foodare;

import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;

import com.example.foodare.databinding.FragmentDailyMealBinding;
import com.example.foodare.model.Meal;
import com.example.foodare.model.MealModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DailyMealFragment extends Fragment {
    FragmentDailyMealBinding binding;
    String mealName;
    String category;
    String area;
    String tags;
    String mealImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDailyMealBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        LiveData<List<Meal>> data = MealModel.instance.getDailyMeal();

        data.observe(getViewLifecycleOwner(), meal -> {
            mealName = meal.get(0).getStrMeal();
            category = meal.get(0).getStrCategory();
            area = meal.get(0).getStrArea();
            tags = meal.get(0).getStrInstructions();
            mealImage = meal.get(0).getStrMealThumb();
            ImageView imageUrlIV = binding.dailyMealImage;

            binding.dailyMealNameTv.setText(mealName);
            binding.dailyMealCategoryTv.setText(category);
            binding.dailyMealAreaTv.setText(area);
            binding.dailyMealTagsTv.setText(tags);

            if (mealImage != "") {
                Picasso.get().load(mealImage).placeholder(R.drawable.hamburger).into(imageUrlIV);
            } else {
                imageUrlIV.setImageResource(R.drawable.hamburger);
            }

            new android.os.Handler(Looper.getMainLooper()).postDelayed(
                    new Runnable() {
                        public void run() {
                            binding.dailyMealProgressbar.setVisibility(View.GONE);
                        }
                    },
                    1200);
        });

        binding.dailyMealBtn.setOnClickListener((buttonView) -> {
            Navigation.findNavController(buttonView).popBackStack();
        });

        return view;
    }
}