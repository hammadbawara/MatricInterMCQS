package com.hz_apps.matricintermcqs.Home.TestSetup;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.Database.DBHelper;
import com.hz_apps.matricintermcqs.Home.MCQS.MCQS;
import com.hz_apps.matricintermcqs.Home.MCQS.MCQsActivity;
import com.hz_apps.matricintermcqs.databinding.FragmentTestSetupBinding;

import java.io.Serializable;
import java.util.List;

public class TestSetupFragment extends Fragment {
    int selectedClass, selectedBook, selectedChapter;
    String tableName;
    FragmentTestSetupBinding binding;
    RecyclerView recyclerView;
    TestGenerator testGenerator;
    Test[] tests;
    DBHelper dbHelper;

    TestsRecyclerAdapter.SetClickListenerOnTest listener;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTestSetupBinding.inflate(getLayoutInflater());
        setClickListener();
        testGenerator = new TestGenerator();
        dbHelper = new DBHelper(getContext(), "MCQS.db");

        selectedClass = TestSetupFragmentArgs.fromBundle(getArguments()).getSelectedClass();
        selectedBook = TestSetupFragmentArgs.fromBundle(getArguments()).getBook();
        selectedChapter = TestSetupFragmentArgs.fromBundle(getArguments()).getChapter();


        String tableCode = "";
        if (selectedChapter<10) tableCode = "10" + selectedClass + "0" + selectedBook +"0" + selectedChapter;
        else tableCode = "10" + selectedClass + "0" + selectedBook + selectedChapter;
        tableName = dbHelper.generateTableName(Long.parseLong(tableCode));
        int numberOfQuestion = dbHelper.getNumberOfMCQs(tableName);
        tests = testGenerator.generateTest(numberOfQuestion);

        TestsRecyclerAdapter adapter = new TestsRecyclerAdapter(getContext(), tests, listener);
        recyclerView = binding.recyclerViewTestSetup;
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    void setClickListener(){
        listener = new TestsRecyclerAdapter.SetClickListenerOnTest() {
            @Override
            public void onClick(int position) {
                List<MCQS> mcqsList = dbHelper.getMCQsWithRowId(tableName, tests[position].getStartPosition(), tests[position].getEndPosition());
                Intent intent = new Intent(getActivity(), MCQsActivity.class);
                intent.putExtra("MCQsList", (Serializable) mcqsList);
                intent.putExtra("TestTitle", tests[position].getTitle());
                startActivity(intent);
            }
        };
    }
}