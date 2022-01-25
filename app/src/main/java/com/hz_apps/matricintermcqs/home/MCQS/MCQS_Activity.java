package com.hz_apps.matricintermcqs.home.MCQS;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hz_apps.matricintermcqs.Database.DBHelper;
import com.hz_apps.matricintermcqs.R;
import com.hz_apps.matricintermcqs.databinding.ActivityMcqsBinding;

import java.util.List;

public class MCQS_Activity extends AppCompatActivity {

    TextView OptionA, OptionB, OptionC, OptionD, mcqs_statement;
    int selectedOption = 0;
    AlertDialog.Builder alertdialog;
    ActivityMcqsBinding binding;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMcqsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        OptionA = findViewById(R.id.OptionA);
        OptionB = findViewById(R.id.OptionB);
        OptionC = findViewById(R.id.OptionC);
        OptionD = findViewById(R.id.OptionD);
        mcqs_statement = findViewById(R.id.mcqs_statement);
        ClickEffectOnOptions();

        DBHelper dbHelper = new DBHelper(this, "MCQS.db", "hz1010101");

        List<MCQS> mcqsList = dbHelper.getMCQSFromDB();
        position = 0;
        binding.nextBtn.setOnClickListener(view -> {
            if (position<mcqsList.size()-1) {
                position++;
                MCQS mcqs = mcqsList.get(position);
                mcqs_statement.setText(Html.fromHtml(mcqs.getStatement()));
                OptionA.setText(Html.fromHtml(mcqs.getOptA()));
                OptionB.setText(Html.fromHtml(mcqs.getOptB()));
                OptionC.setText(Html.fromHtml(mcqs.getOptC()));
                OptionD.setText(Html.fromHtml(mcqs.getOptD()));
            }
        });
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