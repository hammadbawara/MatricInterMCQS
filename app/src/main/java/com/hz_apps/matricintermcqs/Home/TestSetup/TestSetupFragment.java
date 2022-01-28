package com.hz_apps.matricintermcqs.Home.TestSetup;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hz_apps.matricintermcqs.databinding.FragmentTestSetupBinding;

public class TestSetupFragment extends Fragment {
    FragmentTestSetupBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTestSetupBinding.inflate(getLayoutInflater());


        return binding.getRoot();
    }
}