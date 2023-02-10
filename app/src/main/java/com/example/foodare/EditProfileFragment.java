package com.example.foodare;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodare.databinding.FragmentEditProfileBinding;
import com.example.foodare.model.Model;
import com.example.foodare.model.UserModel;
import com.squareup.picasso.Picasso;

public class EditProfileFragment extends Fragment {
    FragmentEditProfileBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;

    Boolean isImageSelected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),
                new ActivityResultCallback<Bitmap>() {
                    @Override
                    public void onActivityResult(Bitmap result) {
                        if (result != null) {
                            binding.editProfileAvatar.setImageBitmap(result);
                            isImageSelected = true;
                        }
                    }
                });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.editProfileAvatar.setImageURI(result);
                    isImageSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        String name = EditProfileFragmentArgs.fromBundle(getArguments()).getName();
        String age = EditProfileFragmentArgs.fromBundle(getArguments()).getAge();
        String phone = EditProfileFragmentArgs.fromBundle(getArguments()).getPhone();
        String imageUrl = EditProfileFragmentArgs.fromBundle(getArguments()).getImageUrl();
        ImageView image = binding.editProfileAvatar;

        binding.editProfileNameEt.setText(name);
        binding.editProfileAgeEt.setText(age);
        binding.editProfilePhoneEt.setText(phone);

        if (!imageUrl.equals("")) {
            Picasso.get().load(imageUrl).placeholder(R.drawable.userimage).into(image);
        } else {
            image.setImageResource(R.drawable.userimage);
        }

        binding.editProfileSaveBtn.setOnClickListener(saveBtnView -> {
            String newName = binding.editProfileNameEt.getText().toString();
            String newAge = binding.editProfileAgeEt.getText().toString();
            String newPhone = binding.editProfilePhoneEt.getText().toString();
            UserModel userModel = new UserModel(Model.instance().getCurrentUserMail(), newName, newAge, newPhone, imageUrl);

            Model.instance().addUser(userModel, (unused) -> {
                Navigation.findNavController(saveBtnView).popBackStack();
            });

        });

        binding.editUserCameraBtn.setOnClickListener(cameraBtnView -> {
            cameraLauncher.launch(null);
        });

        binding.editUserGalleryBtn.setOnClickListener(galleryBtnView -> {
            galleryLauncher.launch("image/*");
        });

        binding.editProfileCancelBtn.setOnClickListener(cancelBtnView -> {
            Navigation.findNavController(cancelBtnView).popBackStack();
        });

        return view;
    }
}