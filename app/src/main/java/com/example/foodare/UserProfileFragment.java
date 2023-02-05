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

import com.example.foodare.model.Model;
import com.example.foodare.model.UserModel;
import com.squareup.picasso.Picasso;

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
        ImageView image = view.findViewById(R.id.user_profile_avatar);

        Model.instance().getUserByMail(Model.instance().getCurrentUserMail(), user -> {
            nameTv.setText(user.getUsername());
            ageTv.setText(user.getAge());
            phoneTv.setText(user.getPhone());
            mailTv.setText(user.getMail());
            if (!user.getImageUrl().equals("")) {
                Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.userimage).into(image);
            } else {
                image.setImageResource(R.drawable.userimage);
            }
        });

        editProfileButton.setOnClickListener(editView -> {
            String name = (String) nameTv.getText();
            String age = (String) ageTv.getText();
            String phone = (String) phoneTv.getText();

            UserProfileFragmentDirections.ActionUserProfileFragmentToEditProfileFragment action = UserProfileFragmentDirections.actionUserProfileFragmentToEditProfileFragment(name, age, phone);
            Navigation.findNavController(view).navigate(action);
        });

        return view;
    }
}