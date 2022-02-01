package com.hz_apps.matricintermcqs.Home.SelectChapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.Database.DBHelper;
import com.hz_apps.matricintermcqs.databinding.FragmentSelectChapterBinding;

import java.util.List;

public class SelectChapterFragment extends Fragment {
    FragmentSelectChapterBinding binding;
    RecyclerAdapterChapter.ChapterViewOnClick listener;
    private int selectedClass, selectedBook;
    private List<BookChapter> chapterList;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectChapterBinding.inflate(getLayoutInflater());

        //This class is created because I want to run this in another thread.
        ShowChaptersInRecyclerView showChapters = new ShowChaptersInRecyclerView();
        showChapters.start();

        return binding.getRoot();
    }
    void clickListener(){
        listener = (view, position, chapter) -> {
            NavDirections action = SelectChapterFragmentDirections
                    .actionFragmentSelectChapterToTestSetupFragment(selectedClass, selectedBook, chapter, chapterList.get(position).getChapterName());
            Navigation.findNavController(view).navigate(action);
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
            clickListener();
            //Setting up recyclerView
            RecyclerAdapterChapter adapter = new RecyclerAdapterChapter(getContext(), chapterList , listener);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    RecyclerView recyclerview = binding.SelectChapterRV;
                    recyclerview.setAdapter(adapter);
                    recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
                    binding.progressBarSelectChapter.setVisibility(View.GONE);
                }
            });
        }
    }
}