package com.example.foodare;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.SystemClock;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodare.model.Model;
import com.example.foodare.model.Post;
import com.squareup.picasso.Picasso;

public class EditPostFragment extends Fragment {
    Post post;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_post, container, false);
        String postId = EditPostFragmentArgs.fromBundle(getArguments()).getPostId();
        String restaurant = EditPostFragmentArgs.fromBundle(getArguments()).getRestaurant();
        String meal = EditPostFragmentArgs.fromBundle(getArguments()).getMeal();
        String rate = EditPostFragmentArgs.fromBundle(getArguments()).getRate();
        String description = EditPostFragmentArgs.fromBundle(getArguments()).getDescription();
        String imageUrl = EditPostFragmentArgs.fromBundle(getArguments()).getImageUrl();

        EditText restaurantEt = view.findViewById(R.id.edit_post_restaurant_et);
        EditText mealEt = view.findViewById(R.id.edit_post_meal_et);
        EditText rateEt = view.findViewById(R.id.edit_post_rate_et);
        EditText descriptionEt = view.findViewById(R.id.edit_post_description_et);
        ImageView imageUrlIV = view.findViewById(R.id.edit_post_image);

        restaurantEt.setText(restaurant);
        mealEt.setText(meal);
        rateEt.setText(rate);
        descriptionEt.setText(description);

        if (!imageUrl.equals("")) {
            Picasso.get().load(imageUrl).placeholder(R.drawable.hamburger).into(imageUrlIV);
        } else {
            imageUrlIV.setImageResource(R.drawable.hamburger);
        }

        Button cancelBtn = view.findViewById(R.id.edit_post_cancel_btn);
        Button saveBtn = view.findViewById(R.id.edit_post_save_btn);
        ImageButton deleteBtn = view.findViewById(R.id.edit_post_delete_btn);

        cancelBtn.setOnClickListener((buttonView) -> {
            Navigation.findNavController(buttonView).popBackStack();
        });

        saveBtn.setOnClickListener(uploadBtnView -> {
            String newRestaurant = restaurantEt.getText().toString();
            String newMeal = mealEt.getText().toString();
            String newRate = rateEt.getText().toString();
            String newDescription = descriptionEt.getText().toString();
            post = new Post(postId, Model.instance().getCurrentUserMail(), newRestaurant, newMeal, newRate, newDescription, imageUrl);

            Model.instance().addPost(post, (unused) -> {
                Navigation.findNavController(uploadBtnView).popBackStack();
            });

        });

        deleteBtn.setOnClickListener((buttonView) -> {
            DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
            Bundle args = new Bundle();

            args.putString("postId", postId);
            args.putString("mail", postId);
            args.putString("restaurant", postId);
            args.putString("meal", postId);
            args.putString("rate", postId);
            args.putString("description", postId);
            args.putString("imageUrl", postId);

            deleteDialogFragment.setArguments(args);
            deleteDialogFragment.show(getChildFragmentManager(), "DELETE_POST");
        });
        return view;
    }
}