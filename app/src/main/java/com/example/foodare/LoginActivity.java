package com.example.foodare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import com.example.foodare.databinding.ActivityLoginBinding;
import com.example.foodare.model.Model;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginActivityProgressbar.setVisibility(View.GONE);

        binding.loginActivityRegisterBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        binding.loginActivityLoginBtn.setOnClickListener(view -> {
            binding.loginActivityProgressbar.setVisibility(View.VISIBLE);

            String email = binding.loginActivityEmailEt.getText().toString();
            String password = binding.loginActivityPasswordEt.getText().toString();

            if (!email.equals("") && !password.equals("")) {
                Model.instance().loginUser(email, password, (callback) -> {
                    new android.os.Handler(Looper.getMainLooper()).postDelayed(
                            new Runnable() {
                                public void run() {
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                    binding.loginActivityProgressbar.setVisibility(View.GONE);
                                }
                            },
                            1200);
                });
                binding.loginActivityProgressbar.setVisibility(View.GONE);
            } else {
                binding.loginActivityProgressbar.setVisibility(View.GONE);
            }
        });

    }
}