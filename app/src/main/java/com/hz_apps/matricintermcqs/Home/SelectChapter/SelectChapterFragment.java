package com.hz_apps.matricintermcqs.Home.SelectChapter;

import android.app.ProgressDialog;
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
    private RecyclerView recyclerview;
    FragmentSelectChapterBinding binding;
    RecyclerAdapterChapter.ChapterViewOnClick listener;
    int selectedClass, selectedBook;
    List<BookChapter> chapterList;

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
        String tableName = dbHelper.generateTableName(Long.parseLong("10" + selectedClass +"0"+ selectedBook));
        chapterList = dbHelper.getBookChapters(tableName);

        clickListener();
        RecyclerAdapterChapter adapterChapter = new RecyclerAdapterChapter(getContext(), chapterList , listener);
        recyclerview.setAdapter(adapterChapter);

        progressDialog.dismiss();
        return binding.getRoot();
    }
    void clickListener(){
        listener = new RecyclerAdapterChapter.ChapterViewOnClick() {
            @Override
            public void onClick(View view, int position, int chapter) {
                NavDirections action = SelectChapterFragmentDirections
                        .actionFragmentSelectChapterToTestSetupFragment(selectedClass, selectedBook, chapter, chapterList.get(position).getChapterName());
                Navigation.findNavController(view).navigate(action);
            }
        };
    }
}