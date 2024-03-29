package com.hz_apps.matricintermcqs.Home.HomeMain;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.Database.DBHelper;
import com.hz_apps.matricintermcqs.R;
import com.hz_apps.matricintermcqs.databinding.ExitFromAppDialogBinding;
import com.hz_apps.matricintermcqs.databinding.FragmentHomeMainBinding;

public class HomeMainFragment extends Fragment {

    RecyclerView recyclerView;
    FragmentHomeMainBinding binding;
    TextView class9_TVi, class10_TVi;
    static int SelectedClass;
    private final String FragmentName = "HomeMainFragment";
    BooksRecyclerView adapter;
    public static int BookIcon;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeMainBinding.inflate(getLayoutInflater());
        BackPressAction();

        ShowBooksInRecyclerView showBooks = new ShowBooksInRecyclerView();
        showBooks.start();

        recyclerView = binding.ChooseSubjectRV;
        class9_TVi = binding.class9TVi;
        class10_TVi = binding.class10TVi;

        return binding.getRoot();
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void changeClass(int selectedClass){
        String[] books = null;
        TypedArray books_icons = null;
        switch (selectedClass){
            case 1:
                books = getResources().getStringArray(R.array.class_9_books);
                books_icons = getResources().obtainTypedArray(R.array.class_9_books_icons);
                //unselect class 10th
                class10_TVi.setBackground(getResources().getDrawable(R.drawable.class_unselected));
                class10_TVi.setTextColor(getResources().getColor(R.color.black));
                //select class 9th
                class9_TVi.setBackground(getResources().getDrawable(R.drawable.class_selected));
                class9_TVi.setTextColor(getResources().getColor(R.color.white));
                break;
            case 2:
                books = getResources().getStringArray(R.array.class_10_books);
                books_icons = getResources().obtainTypedArray(R.array.class_10_books_icons);
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

        //saved selected class in sharedPreference
        SelectedClass = selectedClass;
        requireActivity().getSharedPreferences(FragmentName, Context.MODE_PRIVATE)
                .edit().putInt("className", selectedClass).apply();
    }

    public void BackPressAction(){
        ExitFromAppDialogBinding dialogBinding = ExitFromAppDialogBinding.inflate(getLayoutInflater());
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {

                if(dialogBinding.getRoot().getParent() != null) {
                    ((ViewGroup)dialogBinding.getRoot().getParent()).removeView(dialogBinding.getRoot());
                }

                DBHelper dbHelper = new DBHelper(getContext(), "MCQS.db");
                String[] quote = dbHelper.getQuote();

                dialogBinding.QuoteTView.setText(quote[0]);
                dialogBinding.QuoteAuthorTView.setText(quote[1]);

                AlertDialog.Builder exitDialog = new AlertDialog.Builder(getContext());
                exitDialog.setView(dialogBinding.getRoot());
                AlertDialog dialog = exitDialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogBinding.cancelBtnExitDialog.setOnClickListener(view -> {
                    dbHelper.close();
                    dialog.dismiss();
                });
                dialogBinding.ExitBtnExitDialog.setOnClickListener(view -> requireActivity().finish());
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

    public class ShowBooksInRecyclerView extends Thread{

        @Override
        public void run() {
            SelectedClass = requireActivity().getSharedPreferences(FragmentName, Context.MODE_PRIVATE).getInt("className", 1);

            requireActivity().runOnUiThread(() -> {
                binding.progressBarHomeMainFragment.setVisibility(View.GONE);
                changeClass(SelectedClass);
                class9_TVi.setOnClickListener(view -> changeClass(1));
                class10_TVi.setOnClickListener(view -> changeClass(2));
            });
        }
    }
}