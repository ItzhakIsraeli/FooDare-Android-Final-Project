package com.example.foodare;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodare.databinding.FragmentEditPostBinding;
import com.example.foodare.model.Model;
import com.example.foodare.model.Post;
import com.squareup.picasso.Picasso;

public class EditPostFragment extends Fragment {
    Post post;
    FragmentEditPostBinding binding;
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
                            binding.editPostImage.setImageBitmap(result);
                            isImageSelected = true;
                        }
                    }
                });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.editPostImage.setImageURI(result);
                    isImageSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditPostBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.editPostProgressbar.setVisibility(View.GONE);
        String postId = EditPostFragmentArgs.fromBundle(getArguments()).getPostId();
        String restaurant = EditPostFragmentArgs.fromBundle(getArguments()).getRestaurant();
        String meal = EditPostFragmentArgs.fromBundle(getArguments()).getMeal();
        String rate = EditPostFragmentArgs.fromBundle(getArguments()).getRate();
        String description = EditPostFragmentArgs.fromBundle(getArguments()).getDescription();
        String imageUrl = EditPostFragmentArgs.fromBundle(getArguments()).getImageUrl();

        EditText restaurantEt = binding.editPostRestaurantEt;
        EditText mealEt = binding.editPostMealEt;
        EditText rateEt = binding.editPostRateEt;
        EditText descriptionEt = binding.editPostDescriptionEt;
        ImageView imageUrlIV = binding.editPostImage;

        binding.editPostRestaurantEt.setText(restaurant);
        binding.editPostMealEt.setText(meal);
        binding.editPostRateEt.setText(rate);
        binding.editPostDescriptionEt.setText(description);

        if (!imageUrl.equals("")) {
            Picasso.get().load(imageUrl).placeholder(R.drawable.hamburger).into(imageUrlIV);
        } else {
            imageUrlIV.setImageResource(R.drawable.hamburger);
        }

        binding.editPostSaveBtn.setOnClickListener(uploadBtnView -> {
            binding.editPostProgressbar.setVisibility(View.VISIBLE);
            String newRestaurant = restaurantEt.getText().toString();
            String newMeal = mealEt.getText().toString();
            String newRate = rateEt.getText().toString();
            String newDescription = descriptionEt.getText().toString();
            post = new Post(postId, false, Model.instance().getCurrentUserMail(), newRestaurant, newMeal, newRate, newDescription, imageUrl);
            String id = Long.toString(SystemClock.elapsedRealtime());

            if (isImageSelected) {
                imageUrlIV.setDrawingCacheEnabled(true);
                imageUrlIV.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) imageUrlIV.getDrawable()).getBitmap();
                Model.instance().uploadImage(id, bitmap, url -> {
                    if (url != null) {
                        post.setImageUrl(url);
                    }
                    Model.instance().addPost(post, (unused) -> {
                        useRunnable(uploadBtnView);
                    });
                });
            } else {
                Model.instance().addPost(post, (unused) -> {
                    useRunnable(uploadBtnView);
                });
            }
        });

        binding.editPostDeleteBtn.setOnClickListener((buttonView) -> {
            DeleteDialogFragment deleteDialogFragment = new DeleteDialogFragment();
            Bundle args = new Bundle();
            args.putString("postId", postId);
            deleteDialogFragment.setArguments(args);
            deleteDialogFragment.addView(buttonView);
            deleteDialogFragment.show(getChildFragmentManager(), "DELETE_POST");
        });

        binding.editPostCameraBtn.setOnClickListener(cameraBtnView -> {
            cameraLauncher.launch(null);
        });

        binding.editPostGalleryBtn.setOnClickListener(galleryBtnView -> {
            galleryLauncher.launch("image/*");
        });

        binding.editPostCancelBtn.setOnClickListener((buttonView) -> {
            Navigation.findNavController(buttonView).popBackStack();
        });

        return view;
    }

    public void useRunnable(View uploadBtnView) {
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        Navigation.findNavController(uploadBtnView).popBackStack();
                        binding.editPostProgressbar.setVisibility(View.GONE);
                    }
                },
                1200);
    }
}