package com.example.foodare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.foodare.model.Model;
import com.example.foodare.model.Post;

import java.time.Instant;
import java.util.Date;

public class AddPostFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_post, container, false);

        EditText restaurantNameEt = view.findViewById(R.id.add_post_restaurant_et);
        EditText mealNameEt = view.findViewById(R.id.add_post_meal_et);
        EditText mealRateEt = view.findViewById(R.id.add_post_rate_et);
        EditText descriptionEt = view.findViewById(R.id.add_post_description_et);

        Button uploadBtn = view.findViewById(R.id.add_post_upload_btn);
        Button cancelBtn = view.findViewById(R.id.add_post_cancel_btn);

        uploadBtn.setOnClickListener(uploadBtnView -> {
            // TODO: add upload to DB
            String restaurant = restaurantNameEt.getText().toString();
            String meal = mealNameEt.getText().toString();
            String rate = mealRateEt.getText().toString();
            String description = descriptionEt.getText().toString();

            Post post = new Post(Long.toString(SystemClock.elapsedRealtime()), "MorAndIzhak", restaurant, meal, rate, description);
            Model.instance().addPost(post, () -> {
                Navigation.findNavController(uploadBtnView).popBackStack();
            });
        });

        cancelBtn.setOnClickListener(cancelBtnView -> {
            Navigation.findNavController(cancelBtnView).popBackStack();
        });

        return view;
    }
}