package com.hz_apps.matricintermcqs.Home.CreateCustomTest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.hz_apps.matricintermcqs.Database.DBHelper;
import com.hz_apps.matricintermcqs.Home.HomeMain.HomeMainFragment;
import com.hz_apps.matricintermcqs.Home.MCQS.MCQS;
import com.hz_apps.matricintermcqs.Home.MCQS.MCQsActivity;
import com.hz_apps.matricintermcqs.Home.SelectChapter.BookChapter;
import com.hz_apps.matricintermcqs.databinding.FragmentCreateOwnTestBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CreateOwnTestFragment extends Fragment {
    FragmentCreateOwnTestBinding binding;
    int numberOfMCQs;
    int selectedBook, selectedClass;
    DBHelper db;
    BookChapter[] checkedChaptersList;
    String[] Classes = {"9th", "10th"};
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateOwnTestBinding.inflate(getLayoutInflater());

        checkedChaptersList = (BookChapter[]) getArguments().getSerializable("checkedChaptersList");
        selectedClass = getArguments().getInt("selectedClass");
        selectedBook = getArguments().getInt("selectedBook");

        db = new DBHelper(getContext(), "MCQS.db");
        numberOfMCQs = db.getNumberOfMCQs(checkedChaptersList, selectedBook, selectedClass);
        binding.numberOfMCQsInTestCreateTest.setHint("12-"+ numberOfMCQs );

        TextView numberOfMCQsTextView = binding.numberOfMCQsInTestCreateTest;
        binding.startTestBtnCreateOwnTest.setOnClickListener(v -> {
            if (numberOfMCQsTextView.getText().toString().isEmpty()){
                numberOfMCQsTextView.setError("Enter number of MCQs in Test First");
                return;
            }
            int userMCQs = Integer.parseInt(numberOfMCQsTextView.getText().toString());
            if (userMCQs <12 | userMCQs>numberOfMCQs){
                numberOfMCQsTextView.setError("MCQs should be in range 12-" + numberOfMCQs);
            }else {
                List<MCQS> mcqsList = createTest(userMCQs);
                Intent intent = new Intent(getActivity(), MCQsActivity.class);
                intent.putExtra("MCQsList", (Serializable) mcqsList);
                intent.putExtra("ClassName", Classes[selectedClass-1]);
                intent.putExtra("BookIcon", HomeMainFragment.BookIcon);
                startActivity(intent);
            }
        });


        return binding.getRoot();
    }

    private List<MCQS> createTest(int num){
        List<MCQS> allMCQsList = db.getMCQsOfChapters(checkedChaptersList, numberOfMCQs, selectedClass, selectedBook);
        Collections.shuffle(allMCQsList);
        List<MCQS> mcqsList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            mcqsList.add(allMCQsList.get(i));
        }
        return mcqsList;
    }
}