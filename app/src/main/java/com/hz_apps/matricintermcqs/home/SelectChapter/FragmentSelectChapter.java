package com.hz_apps.matricintermcqs.home.SelectChapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.Database.DBHelper;
import com.hz_apps.matricintermcqs.R;
import com.hz_apps.matricintermcqs.home.MCQS.MCQS_Activity;

import java.util.List;

public class FragmentSelectChapter extends Fragment {
    RecyclerView SelectChapter_RV;
    View view;
    RecyclerAdapterChapter.ChapterViewOnClick listener;
    int selectedClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_select_chapter, container, false);

        selectedClass = FragmentSelectChapterArgs.fromBundle(getArguments()).getClass();
        int selectedBook = FragmentSelectChapterArgs.fromBundle(getArguments()).getBook();
        DBHelper dbHelper = new DBHelper(getContext(), "MCQS.db");
        List<BookChapter> chapterList = dbHelper.getBookChapters(selectedClass, selectedBook);

        clickListener();
        RecyclerAdapterChapter adapterChapter = new RecyclerAdapterChapter(getContext(), chapterList , listener);
        SelectChapter_RV.setAdapter(adapterChapter);
        SelectChapter_RV.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }
    void clickListener(){
        listener = (view, position) -> {
            Intent intent = new Intent(getActivity(), MCQS_Activity.class);
            startActivity(intent);
        };
    }
}