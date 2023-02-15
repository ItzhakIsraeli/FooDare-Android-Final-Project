package com.example.foodare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodare.databinding.ActivityRegisterBinding;
import com.example.foodare.model.Model;
import com.example.foodare.model.UserModel;

public class RegisterActivity extends AppCompatActivity {
    ActivityRegisterBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;

    Boolean isImageSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.userRegisterProgressbar.setVisibility(View.GONE);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),
                new ActivityResultCallback<Bitmap>() {
                    @Override
                    public void onActivityResult(Bitmap result) {
                        if (result != null) {
                            binding.userRegisterAvatar.setImageBitmap(result);
                            isImageSelected = true;
                        }
                    }
                });

        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null) {
                    binding.userRegisterAvatar.setImageURI(result);
                    isImageSelected = true;
                }
            }
        });

        binding.userRegisterBtn.setOnClickListener(view -> {
            binding.userRegisterProgressbar.setVisibility(View.VISIBLE);

            String name = binding.userRegisterNameEt.getText().toString();
            String age = binding.userRegisterAgeEt.getText().toString();
            String phone = binding.userRegisterPhoneEt.getText().toString();
            String mail = binding.userRegisterMailEt.getText().toString();
            String password = binding.userRegisterPasswordEt.getText().toString();

            UserModel user = new UserModel(mail, name, age, phone, "");

            if (isImageSelected) {
                String id = Long.toString(SystemClock.elapsedRealtime());
                binding.userRegisterAvatar.setDrawingCacheEnabled(true);
                binding.userRegisterAvatar.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.userRegisterAvatar.getDrawable()).getBitmap();
                Model.instance().uploadImage(id, bitmap, url -> {
                    if (url != null) {
                        user.setImageUrl(url);
                    }
                    Model.instance().addUser(user, (unused) -> {
                        Model.instance().addFirebaseUser(user.getMail(),password, (callback) -> {
                            useRunnable(view);
                        });
                    });
                    binding.userRegisterProgressbar.setVisibility(View.GONE);
                });
            } else {
                Model.instance().addUser(user, (unused) -> {
                    Model.instance().addFirebaseUser(user.getMail(), password, (callback) -> {
                        useRunnable(view);
                    });
                });
                binding.userRegisterProgressbar.setVisibility(View.GONE);
            };
        });

        binding.userRegisterCameraBtn.setOnClickListener(cameraBtnView -> {
            cameraLauncher.launch(null);
        });

        binding.userRegisterGalleryBtn.setOnClickListener(galleryBtnView -> {
            galleryLauncher.launch("image/*");
        });
    }

    public void useRunnable(View uploadBtnView) {
        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        binding.userRegisterProgressbar.setVisibility(View.GONE);
                    }
                },
                1200);
    }
}