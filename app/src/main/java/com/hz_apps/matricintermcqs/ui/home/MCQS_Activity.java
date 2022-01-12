package com.hz_apps.matricintermcqs.ui.home;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hz_apps.matricintermcqs.R;

public class MCQS_Activity extends AppCompatActivity {

    TextView OptionA, OptionB, OptionC, OptionD, mcqs_statement;
    int selectedOption = 0;
    AlertDialog.Builder alertdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcqs);

        OptionA = findViewById(R.id.OptionA);
        OptionB = findViewById(R.id.OptionB);
        OptionC = findViewById(R.id.OptionC);
        OptionD = findViewById(R.id.OptionD);
        mcqs_statement = findViewById(R.id.mcqs_statement);
        ClickEffectOnOptions();
    }
    void ClickEffectOnOptions(){
        OptionA.setOnClickListener(v -> setBackgroundOnOption(OptionA, 1));
        OptionB.setOnClickListener(v -> setBackgroundOnOption(OptionB, 2));
        OptionC.setOnClickListener(v -> setBackgroundOnOption(OptionC, 3));
        OptionD.setOnClickListener(v -> setBackgroundOnOption(OptionD, 4));
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    void setBackgroundOnOption(TextView option, int option_number){
        switch (selectedOption){
            case 1:
                OptionA.setBackground(getResources().getDrawable(R.drawable.unselected_option));
                break;
            case 2:
                OptionB.setBackground(getResources().getDrawable(R.drawable.unselected_option));
                break;
            case 3:
                OptionC.setBackground(getResources().getDrawable(R.drawable.unselected_option));
                break;
            case 4:
                OptionD.setBackground(getResources().getDrawable(R.drawable.unselected_option));
                break;
        }
        option.setBackground(getResources().getDrawable(R.drawable.selected_mcqs_option));
        selectedOption = option_number;
    }

    @Override
    public void onBackPressed() {
        alertdialog = new AlertDialog.Builder(MCQS_Activity.this);
        alertdialog.setMessage("Do you really want to end test?")
                .setPositiveButton("Yes", (dialog, which) ->
                        MCQS_Activity.super.onBackPressed())
                .setNegativeButton("No", (dialog, which) -> {}).show();

    }
}