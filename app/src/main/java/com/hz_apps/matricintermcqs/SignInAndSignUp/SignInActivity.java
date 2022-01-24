package com.hz_apps.matricintermcqs.SignInAndSignUp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hz_apps.matricintermcqs.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity {

    ActivitySignInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}