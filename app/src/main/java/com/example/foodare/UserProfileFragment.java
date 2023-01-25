package com.example.foodare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class UserProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        Button editProfileButton = view.findViewById(R.id.user_profile_edit_btn);
        editProfileButton.setOnClickListener(view1 -> {
            UserProfileFragmentDirections.ActionUserProfileFragmentToEditProfileFragment action = UserProfileFragmentDirections.actionUserProfileFragmentToEditProfileFragment("", ",", "", ":");
            Navigation.findNavController(view).navigate(action);
        });

        return view;
    }

}