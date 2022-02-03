package com.hz_apps.matricintermcqs.Home.SelectChapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.Database.DBHelper;
import com.hz_apps.matricintermcqs.databinding.FragmentSelectChapterBinding;

import java.util.List;

public class SelectChapterFragment extends Fragment {
    FragmentSelectChapterBinding binding;
    RecyclerAdapterChapter.ChapterViewOnClick listener;
    private int selectedClass, selectedBook;
    private List<BookChapter> chapterList;
    private boolean checkboxSelected = false;
    RecyclerAdapterChapter adapter;
    RecyclerView recyclerview;
    View view;
    BookChapter[] checkedChapterList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectChapterBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        recyclerview = binding.SelectChapterRV;

        //This class is created because I want to run this in another thread.
        ShowChaptersInRecyclerView showChapters = new ShowChaptersInRecyclerView();
        showChapters.start();

        binding.nextBtnSelectChapter.setOnClickListener(v -> {

        });

        return view;
    }

    void clickListener(){
        listener = new RecyclerAdapterChapter.ChapterViewOnClick() {
            @Override
            public void onClick(View view, int position, int chapter) {
                if (!checkboxSelected) {
                    NavDirections action = SelectChapterFragmentDirections
                            .actionFragmentSelectChapterToTestSetupFragment(selectedClass, selectedBook, chapter, chapterList.get(position).getChapterName());
                    Navigation.findNavController(view).navigate(action);
                }
            }

            @Override
            public void CheckChangeListener(int position) {
                if (checkedChapterList[position] == null){
                    checkedChapterList[position] = chapterList.get(position);
                }else{
                    checkedChapterList[position] = null;
                }
                if (isArrayNull(checkedChapterList)){
                    binding.nextBtnSelectChapter.setVisibility(View.GONE);
                }else{
                    binding.nextBtnSelectChapter.setVisibility(View.VISIBLE);
                }
            }
        };
    }

    private class ShowChaptersInRecyclerView extends Thread{
        @Override
        public void run() {
            requireActivity().runOnUiThread(() -> {
                onBackPress();
                setChapterInRecyclerView();
                binding.progressBarSelectChapter.setVisibility(View.GONE);
            });

            //Getting information from previous Fragment
            selectedClass = SelectChapterFragmentArgs.fromBundle(getArguments()).getClassName();
            selectedBook = SelectChapterFragmentArgs.fromBundle(getArguments()).getBook();
            //Getting chapters from Database
            DBHelper dbHelper = new DBHelper(getContext(), "MCQS.db");
            chapterList = dbHelper.getBookChapters(selectedClass, selectedBook);
            checkedChapterList = new BookChapter[chapterList.size()];
            clickListener();

            //Floating Action Button
            binding.createOwnTestFloatingBtn.setOnClickListener(v -> {
                checkboxSelected = true;
                setChapterInRecyclerView();
                binding.createOwnTestFloatingBtn.setVisibility(View.GONE);
            });
        }
    }

    private void setChapterInRecyclerView(){
        adapter = new RecyclerAdapterChapter(getContext(), chapterList , listener, checkboxSelected);
        recyclerview.setAdapter(adapter);
    }

    public void onBackPress(){
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (checkboxSelected){
                    checkboxSelected = false;
                    setChapterInRecyclerView();
                    binding.createOwnTestFloatingBtn.setVisibility(View.VISIBLE);
                }else{
                    Navigation.findNavController(view).navigateUp();
                }
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

    private boolean isArrayNull(BookChapter[] array){
        for (BookChapter bookChapter : array) {
            if (bookChapter != null) {
                return false;
            }
        }
        return true;
    }
}