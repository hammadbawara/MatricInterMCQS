package com.hz_apps.matricintermcqs.Home.TestSetup;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hz_apps.matricintermcqs.databinding.FragmentTestSetupBinding;

public class TestSetupFragment extends Fragment {
    FragmentTestSetupBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTestSetupBinding.inflate(getLayoutInflater());


        return binding.getRoot();
    }
}