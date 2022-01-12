package com.hz_apps.matricintermcqs.ui.home.selectChapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.R;
import com.hz_apps.matricintermcqs.ui.home.MCQS_Activity;

public class fragmentSelectChapter extends Fragment {
    RecyclerView SelectChapter_RV;
    String[] chapters_name;
    View view;
    RecyclerAdapterChapter.ChapterViewOnClick listener;
    int selectedClass;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_select_chapter, container, false);

        SelectChapter_RV = view.findViewById(R.id.SelectChapter_RV);

        chapters_name = new String[] {"Hello", "Bello"};

        clickListener();
        RecyclerAdapterChapter adapterChapter = new RecyclerAdapterChapter(getContext(), chapters_name, listener);
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