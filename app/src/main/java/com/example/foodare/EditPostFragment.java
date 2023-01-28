package com.example.foodare;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditPostFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_post, container, false);

        String restaurant = EditPostFragmentArgs.fromBundle(getArguments()).getRestaurant();
        String meal = EditPostFragmentArgs.fromBundle(getArguments()).getMeal();
        String rate = EditPostFragmentArgs.fromBundle(getArguments()).getRate();
        String description = EditPostFragmentArgs.fromBundle(getArguments()).getDescription();

        EditText restaurantEt = view.findViewById(R.id.edit_post_restaurant_et);
        EditText mealEt = view.findViewById(R.id.edit_post_meal_et);
        EditText rateEt = view.findViewById(R.id.edit_post_rate_et);
        EditText descriptionEt = view.findViewById(R.id.edit_post_description_et);

        restaurantEt.setText(restaurant);
        mealEt.setText(meal);
        rateEt.setText(rate);
        descriptionEt.setText(description);

        Button cancelBtn = view.findViewById(R.id.edit_post_cancel_btn);
        Button saveBtn = view.findViewById(R.id.edit_post_save_btn);
        Button deleteBtn = view.findViewById(R.id.edit_post_delete_btn);

        cancelBtn.setOnClickListener((buttonView) -> {
            Navigation.findNavController(buttonView).popBackStack();
        });

        saveBtn.setOnClickListener((buttonView) -> {
            Log.d("EDIT_POST", "SAVE CLICKED");
        });

        deleteBtn.setOnClickListener((buttonView) -> {
            DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
            deleteDialogFragment.show(getChildFragmentManager(), "IZHAK");
        });
        return view;
    }
}