package com.hz_apps.matricintermcqs.home.HomeMain;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.R;
import com.hz_apps.matricintermcqs.databinding.FragmentHomeMainBinding;

public class HomeMainFragment extends Fragment {

    RecyclerView recyclerView;
    FragmentHomeMainBinding binding;
    TextView class9_TVi, class10_TVi;
    int SelectedClass;
    private final String FragmentName = "HomeMainFragment";
    BooksRecyclerView adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeMainBinding.inflate(getLayoutInflater());

        recyclerView = binding.ChooseSubjectRV;
        class9_TVi = binding.class9TVi;
        class10_TVi = binding.class10TVi;

        SelectedClass = requireActivity().getSharedPreferences(FragmentName, Context.MODE_PRIVATE).getInt("className", 9);
        changeClass(SelectedClass);

        class9_TVi.setOnClickListener(v -> askBeforeChangingClass( 9));
        class10_TVi.setOnClickListener(v -> askBeforeChangingClass( 10));

        return binding.getRoot();
    }

    private void askBeforeChangingClass(int selectedClass){
        new AlertDialog.Builder(getContext()).setMessage("Do you really want to change class?")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    changeClass(selectedClass);
                }).setNegativeButton("No", (dialogInterface, i) -> {

                }).setCancelable(false).show();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeClass(int selectedClass){
        String[] books = null;
        int[] books_icons = null;
        switch (selectedClass){
            case 9:
                books = getResources().getStringArray(R.array.class_9_books);
                books_icons = getResources().getIntArray(R.array.class_9_books_icons);
                //unselect class 10th
                class10_TVi.setBackground(getResources().getDrawable(R.drawable.class_unselected));
                class10_TVi.setTextColor(getResources().getColor(R.color.black));
                //select class 9th
                class9_TVi.setBackground(getResources().getDrawable(R.drawable.class_selected));
                class9_TVi.setTextColor(getResources().getColor(R.color.white));
                break;
            case 10:
                books = getResources().getStringArray(R.array.class_10_books);
                books_icons = getResources().getIntArray(R.array.class_10_books_icons);
                //unselect class 9th
                class9_TVi.setBackground(getResources().getDrawable(R.drawable.class_unselected));
                class9_TVi.setTextColor(getResources().getColor(R.color.black));
                //select class 10
                class10_TVi.setBackground(getResources().getDrawable(R.drawable.class_selected));
                class10_TVi.setTextColor(getResources().getColor(R.color.white));
                break;
        }

        //set RecyclerView
        adapter = new BooksRecyclerView(getContext(), books, books_icons);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        //saved selected class in sharedPreference
        SelectedClass = selectedClass;
        requireActivity().getSharedPreferences(FragmentName, Context.MODE_PRIVATE)
                .edit().putInt("className", selectedClass).apply();
    }

}