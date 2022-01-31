package com.hz_apps.matricintermcqs.saved.SavedTest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hz_apps.matricintermcqs.databinding.FragmentSavedTestBinding;

public class SavedTestFragment extends Fragment {
    FragmentSavedTestBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSavedTestBinding.inflate(getLayoutInflater());



        return binding.getRoot();
    }
}