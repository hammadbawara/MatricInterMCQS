package com.hz_apps.matricintermcqs.Home.SelectChapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.Database.DBHelper;
import com.hz_apps.matricintermcqs.R;
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

        if (checkboxSelected) {
            checkboxSelected = false;
            binding.createOwnTestFloatingBtn.setVisibility(View.VISIBLE);
            binding.nextBtnSelectChapter.setVisibility(View.GONE);
            setChapterInRecyclerView();
        }

        //This class is created because I want to run this in another thread.
        ShowChaptersInRecyclerView showChapters = new ShowChaptersInRecyclerView();
        showChapters.start();

        binding.nextBtnSelectChapter.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putSerializable("checkedChaptersList", checkedChapterList);
            args.putInt("selectedBook", selectedBook);
            args.putInt("selectedClass", selectedClass);
            Navigation.findNavController(v).navigate(R.id.action_fragmentSelectChapter_to_createOwnTestFragment, args);
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
                //Save if it not saved already
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

            @Override
            public void longClick(int position) {
                enterIntoContextualMenu();
            }
        };
    }

    private class ShowChaptersInRecyclerView extends Thread{
        @Override
        public void run() {
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
                enterIntoContextualMenu();
            });

            requireActivity().runOnUiThread(() -> {
                onBackPress();
                setChapterInRecyclerView();
                binding.progressBarSelectChapter.setVisibility(View.GONE);
            });
        }
    }

    private void setChapterInRecyclerView(){
        adapter = new RecyclerAdapterChapter(getContext(), chapterList, listener, checkboxSelected);
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
                    binding.nextBtnSelectChapter.setVisibility(View.GONE);
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

    private void enterIntoContextualMenu(){
        checkboxSelected = true;
        setChapterInRecyclerView();
        binding.createOwnTestFloatingBtn.setVisibility(View.GONE);
    }
}