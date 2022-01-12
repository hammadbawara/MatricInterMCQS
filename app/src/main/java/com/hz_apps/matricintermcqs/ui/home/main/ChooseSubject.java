package com.hz_apps.matricintermcqs.ui.home.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hz_apps.matricintermcqs.R;

public class ChooseSubject extends Fragment {

    RecyclerView ChooseSubject_RV;
    String[] subject_names;
    int[] subject_icons;
    RecyclerAdapter.SubjectViewOnClick listener;
    View view;
    TextView class9_TVi, class10_TVi, class11_TVi, class12_TVi;
    int SelectedClass = 9;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_choose_subject, container, false);
        //findViewById
        ChooseSubject_RV = view.findViewById(R.id.ChooseSubject_RV);
        class9_TVi = view.findViewById(R.id.class9_TVi);
        class10_TVi = view.findViewById(R.id.class10_TVi);
        class11_TVi = view.findViewById(R.id.class11_TVi);
        class12_TVi = view.findViewById(R.id.class12_TVi);

        subject_names = new String[]{"Physics", "Chemistry", "Biology", "Mathematics"};
        subject_icons = new int[]{R.drawable.ico_physics, R.drawable.ico_chemistry, R.drawable.ico_biology, R.drawable.ico_mathematics};

        selectUnSelectClass();
        ClickListener();
        RecyclerAdapter adapter = new RecyclerAdapter(getContext(), subject_names, subject_icons, listener);
        ChooseSubject_RV.setAdapter(adapter);
        ChooseSubject_RV.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        return view;
    }
    void ClickListener(){
        listener = (v, position) -> {
            NavController navigation = Navigation.findNavController(view);
            navigation.navigate(R.id.action_chooseSubject_to_fragmentSelectChapter);
        };
    }
    void selectUnSelectClass(){
        class9_TVi.setOnClickListener(v -> {
            setBackgroundOnClass(class9_TVi);
            SelectedClass = 9;
        });
        class10_TVi.setOnClickListener(v -> {
            setBackgroundOnClass(class10_TVi);
            SelectedClass = 10;
        });
        class11_TVi.setOnClickListener(v -> {
            setBackgroundOnClass(class11_TVi);
            SelectedClass = 11;
        });
        class12_TVi.setOnClickListener(v -> {
            setBackgroundOnClass(class12_TVi);
            SelectedClass = 12;
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    void setBackgroundOnClass(TextView Class){
        switch (SelectedClass){
            case 9:
                class9_TVi.setBackground(getResources().getDrawable(R.drawable.class_unselected));
                class9_TVi.setTextColor(getResources().getColor(R.color.black));
                break;
            case 10:
                class10_TVi.setBackground(getResources().getDrawable(R.drawable.class_unselected));
                class10_TVi.setTextColor(getResources().getColor(R.color.black));
                break;
            case 11:
                class11_TVi.setBackground(getResources().getDrawable(R.drawable.class_unselected));
                class11_TVi.setTextColor(getResources().getColor(R.color.black));
                break;
            case 12:
                class12_TVi.setBackground(getResources().getDrawable(R.drawable.class_unselected));
                class12_TVi.setTextColor(getResources().getColor(R.color.black));
                break;
        }
        Class.setBackground(getResources().getDrawable(R.drawable.class_selected));
        Class.setTextColor(getResources().getColor(R.color.white));
    }

}