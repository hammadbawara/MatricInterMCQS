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
import com.hz_apps.matricintermcqs.Home.HomeMain.HomeMainFragment;
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
    String[] Classes = new String[] {"9th", "10th"};

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



        tableName = dbHelper.generateTableName(selectedClass, selectedBook, selectedChapter);
        int numberOfQuestion = dbHelper.getNumberOfMCQs(tableName);
        tests = testGenerator.generateTest(numberOfQuestion);

        TestsRecyclerAdapter adapter = new TestsRecyclerAdapter(getContext(), tests, listener);
        recyclerView = binding.recyclerViewTestSetup;
        recyclerView.setAdapter(adapter);

        return binding.getRoot();
    }

    void setClickListener(){
        listener = position -> {
            List<MCQS> mcqsList = dbHelper.getMCQsWithRowId(tableName, tests[position].getStartPosition(), tests[position].getEndPosition());
            Intent intent = new Intent(getActivity(), MCQsActivity.class);
            intent.putExtra("MCQsList", (Serializable) mcqsList);
            intent.putExtra("TestTitle", tests[position].getTitle());
            intent.putExtra("ClassName", Classes[selectedClass-1]);
            intent.putExtra("BookIcon", HomeMainFragment.BookIcon);
            startActivity(intent);
        };
    }
}