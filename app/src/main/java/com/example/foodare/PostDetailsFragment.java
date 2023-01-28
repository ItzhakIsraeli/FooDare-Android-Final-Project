package com.example.foodare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.squareup.picasso.Picasso;

public class PostDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_details, container, false);

        String username = PostDetailsFragmentArgs.fromBundle(getArguments()).getUsername();
        String restaurant = PostDetailsFragmentArgs.fromBundle(getArguments()).getRestaurant();
        String meal = PostDetailsFragmentArgs.fromBundle(getArguments()).getMeal();
        String rate = PostDetailsFragmentArgs.fromBundle(getArguments()).getRate();
        String description = PostDetailsFragmentArgs.fromBundle(getArguments()).getDescription();
        String imageUrl = PostDetailsFragmentArgs.fromBundle(getArguments()).getImageUrl();


//        TextView usernameTv = view.findViewById(R.id.post_details_username_tv);
        TextView restaurantTv = view.findViewById(R.id.post_details_restaurant_tv);
        TextView mealTv = view.findViewById(R.id.post_details_meal_tv);
        TextView rateTv = view.findViewById(R.id.post_details_rate_tv);
        TextView descriptionTv = view.findViewById(R.id.post_details_description_tv);
        ImageView imageUrlIV = view.findViewById(R.id.post_details_image);

        if (imageUrl != "") {
            Picasso.get().load(imageUrl).placeholder(R.drawable.hamburger).into(imageUrlIV);
        } else {
            imageUrlIV.setImageResource(R.drawable.hamburger);
        }

//        usernameTv.setText(username);
        restaurantTv.setText(restaurant);
        mealTv.setText(meal);
        rateTv.setText(rate);
        descriptionTv.setText(description);

        Button button = view.findViewById(R.id.post_details_back_btn);
        button.setOnClickListener((buttonView) -> {
            Navigation.findNavController(buttonView).popBackStack();
        });
        return view;
    }
}