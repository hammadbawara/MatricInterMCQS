package com.hz_apps.matricintermcqs.home.SelectChapter;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.Database.DBHelper;
import com.hz_apps.matricintermcqs.databinding.FragmentSelectChapterBinding;
import com.hz_apps.matricintermcqs.home.MCQS.MCQsActivity;

import java.util.List;

public class SelectChapterFragment extends Fragment {
    private RecyclerView recyclerview;
    FragmentSelectChapterBinding binding;
    RecyclerAdapterChapter.ChapterViewOnClick listener;
    int selectedClass, selectedBook;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectChapterBinding.inflate(getLayoutInflater());

        ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading chapters...");
        progressDialog.setCancelable(false);

        recyclerview = binding.SelectChapterRV;

        selectedClass = SelectChapterFragmentArgs.fromBundle(getArguments()).getClassName();
        selectedBook = SelectChapterFragmentArgs.fromBundle(getArguments()).getBook();
        DBHelper dbHelper = new DBHelper(getContext(), "MCQS.db");
        List<BookChapter> chapterList = dbHelper.getBookChapters(selectedClass, selectedBook);

        clickListener();
        RecyclerAdapterChapter adapterChapter = new RecyclerAdapterChapter(getContext(), chapterList , listener);
        recyclerview.setAdapter(adapterChapter);
        recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressDialog.dismiss();
        return binding.getRoot();
    }
    void clickListener(){
        listener = (view, chapter) -> {
            Intent intent = new Intent(getActivity(), MCQsActivity.class);
            intent.putExtra("selectedClass", selectedClass);
            intent.putExtra("selectedBook", selectedBook);
            intent.putExtra("selectedChapter", chapter);
            startActivity(intent);
        };
    }
}