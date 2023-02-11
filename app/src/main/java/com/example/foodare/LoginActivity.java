package com.example.foodare;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodare.databinding.ActivityLoginBinding;
import com.example.foodare.model.Model;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.loginActivityRegisterBtn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        binding.loginActivityLoginBtn.setOnClickListener(view -> {
            String email = binding.loginActivityEmailEt.getText().toString();
            String password = binding.loginActivityPasswordEt.getText().toString();
            Model.instance().loginUser(email, password, (callback) -> {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        });

    }
}