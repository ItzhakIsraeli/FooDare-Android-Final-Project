package com.example.foodare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class UserProfileFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);
        Button editProfileButton = view.findViewById(R.id.user_profile_edit_btn);

        TextView nameTv = view.findViewById(R.id.user_profile_name_tv);
        TextView ageTv = view.findViewById(R.id.user_profile_age_tv);
        TextView phoneTv = view.findViewById(R.id.user_profile_phone_tv);
        TextView mailTv = view.findViewById(R.id.user_profile_mail_tv);

        String name = (String) nameTv.getText();
        String age = (String) ageTv.getText();
        String phone = (String) phoneTv.getText();
        String mail = (String) mailTv.getText();

        editProfileButton.setOnClickListener(view1 -> {
            UserProfileFragmentDirections.ActionUserProfileFragmentToEditProfileFragment action = UserProfileFragmentDirections.actionUserProfileFragmentToEditProfileFragment(name, age, phone, mail);
            Navigation.findNavController(view).navigate(action);
        });

        return view;
    }

}