package com.hz_apps.matricintermcqs.saved.SavedTest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.Database.SavedTest;
import com.hz_apps.matricintermcqs.Database.UserDatabase;
import com.hz_apps.matricintermcqs.databinding.FragmentSavedTestBinding;

import java.util.List;

public class SavedTestFragment extends Fragment {
    FragmentSavedTestBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSavedTestBinding.inflate(getLayoutInflater());

        UserDatabase database = new UserDatabase(getContext());
        List<SavedTest> savedTestList = database.getAllSavedTests();

        SavedTestRecyclerAdapter adapter = new SavedTestRecyclerAdapter(getContext(), savedTestList);
        System.out.println("saved tests " + savedTestList.size());
        RecyclerView recyclerView = binding.recyclerViewSavedTest;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return binding.getRoot();
    }
}