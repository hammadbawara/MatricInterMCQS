package com.hz_apps.matricintermcqs.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hz_apps.matricintermcqs.R;
import com.hz_apps.matricintermcqs.databinding.FragmentMoreBinding;

public class Fragment_more extends Fragment {
    FragmentMoreBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
}