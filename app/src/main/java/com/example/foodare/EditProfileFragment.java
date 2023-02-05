package com.example.foodare;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class EditProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        Button saveBtn = view.findViewById(R.id.edit_profile_save_btn);
        Button cancelBtn = view.findViewById(R.id.edit_profile_cancel_btn);

        String name = EditProfileFragmentArgs.fromBundle(getArguments()).getName();
        String age = EditProfileFragmentArgs.fromBundle(getArguments()).getAge();
        String phone = EditProfileFragmentArgs.fromBundle(getArguments()).getPhone();

        TextView nameTv = view.findViewById(R.id.edit_profile_name_et);
        TextView ageTv = view.findViewById(R.id.edit_profile_age_et);
        TextView phoneTv = view.findViewById(R.id.edit_profile_phone_et);

        saveBtn.setOnClickListener(saveBtnView -> {
            // TODO: save changes to DB
        });

        cancelBtn.setOnClickListener(cancelBtnView -> {
            Navigation.findNavController(cancelBtnView).popBackStack();
        });

        nameTv.setText(name);
        ageTv.setText(age);
        phoneTv.setText(phone);
        return view;
    }
}