package com.hz_apps.matricintermcqs.saved.SavedTest;

import android.content.Intent;
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
import com.hz_apps.matricintermcqs.Home.MCQS.MCQS;
import com.hz_apps.matricintermcqs.Home.MCQS.MCQsActivity;
import com.hz_apps.matricintermcqs.databinding.FragmentSavedTestBinding;

import java.io.Serializable;
import java.util.List;

public class SavedTestFragment extends Fragment {
    FragmentSavedTestBinding binding;
    private SavedTestRecyclerAdapter.SavedTestClickListener listener;
    private UserDatabase database;
    List<SavedTest> savedTestList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSavedTestBinding.inflate(getLayoutInflater());
        setClickListener();

        database = new UserDatabase(getContext());
        savedTestList = database.getAllSavedTestsList();

        SavedTestRecyclerAdapter adapter = new SavedTestRecyclerAdapter(getContext(), savedTestList, listener);
        System.out.println("saved tests " + savedTestList.size());
        RecyclerView recyclerView = binding.recyclerViewSavedTest;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        return binding.getRoot();
    }

    private void setClickListener(){
        listener = position -> {
            List<MCQS> mcqsList = database.getSavedTest(savedTestList.get(position).getTableName());
            Intent intent = new Intent(requireActivity(), MCQsActivity.class);
            intent.putExtra("MCQsList", (Serializable) mcqsList);
            intent.putExtra("TestTitle", savedTestList.get(position).getTestTitle());
            intent.putExtra("Position", savedTestList.get(position).getPosition());
            startActivity(intent);
        };
    }
}