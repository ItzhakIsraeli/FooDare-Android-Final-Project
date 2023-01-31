package com.example.foodare;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodare.databinding.FragmentAddPostBinding;
import com.example.foodare.model.Model;
import com.example.foodare.model.Post;

public class AddPostFragment extends Fragment {
    FragmentAddPostBinding binding;
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
                            binding.addPostAvatarImage.setImageBitmap(result);
                            isImageSelected = true;
                        }
                    }
                });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.addPostAvatarImage.setImageURI(result);
                    isImageSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.addPostUploadBtn.setOnClickListener(uploadBtnView -> {
            String restaurant = binding.addPostRestaurantEt.getText().toString();
            String meal = binding.addPostMealEt.getText().toString();
            String rate = binding.addPostRateEt.getText().toString();
            String description = binding.addPostDescriptionEt.getText().toString();
            String id = Long.toString(SystemClock.elapsedRealtime());
            Post post = new Post(id, "MorAndIzhak", restaurant, meal, rate, description, "");

            if (isImageSelected) {
                binding.addPostAvatarImage.setDrawingCacheEnabled(true);
                binding.addPostAvatarImage.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.addPostAvatarImage.getDrawable()).getBitmap();
                Model.instance().uploadImage(id, bitmap, url -> {
                    if (url != null) {
                        post.setImageUrl(url);
                    }
                    Model.instance().addPost(post, (unused) -> {
                        Navigation.findNavController(uploadBtnView).popBackStack();
                    });
                });
            } else {
                Model.instance().addPost(post, (unused) -> {
                    Navigation.findNavController(uploadBtnView).popBackStack();
                });
            }
        });

        binding.addPostCancelBtn.setOnClickListener(cancelBtnView -> {
            Navigation.findNavController(cancelBtnView).popBackStack();
        });

        binding.addPostCameraBtn.setOnClickListener(cameraBtnView -> {
            cameraLauncher.launch(null);
        });

        binding.addPostGalleryBtn.setOnClickListener(galleryBtnView -> {
            galleryLauncher.launch("image/*");
        });

        return view;
    }
}