package com.hz_apps.matricintermcqs.Home.MCQS;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hz_apps.matricintermcqs.Database.DBHelper;
import com.hz_apps.matricintermcqs.databinding.ActivityMcqsBinding;

import java.util.List;

public class MCQsActivity extends AppCompatActivity {

    private TextView OptionA, OptionB, OptionC, OptionD, mcqs_statement, questionNum;
    private AlertDialog.Builder alertdialog;
    ActivityMcqsBinding binding;
    private int position, numberOfMCQs;
    private int selectedClass, selectedBook, selectedChapter;
    private List<MCQS> mcqsList;
    public static short[] answers;
    MCQsFunctionality mcqsFun;
    TextView[] AllOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMcqsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading MCQs");
        progressDialog.setCancelable(false);

        OptionA = binding.OptionA;
        OptionB = binding.OptionB;
        OptionC = binding.OptionC;
        OptionD = binding.OptionD;
        questionNum = binding.questionNum;

        AllOptions = new TextView[] {OptionA, OptionB, OptionC, OptionD};
        mcqs_statement = binding.mcqsStatement;

        mcqsFun = new MCQsFunctionality();

        //All Options
        OptionA.setOnClickListener(view -> {
            mcqsFun.setAllOptionsUnselected(AllOptions);
            answers[position] = 1;
            mcqsFun.checkMCQsOption(AllOptions, answers[position], mcqsList.get(position).getAns());
        });
        OptionB.setOnClickListener(view -> {
            mcqsFun.setAllOptionsUnselected(AllOptions);
            answers[position] = 2;
            mcqsFun.checkMCQsOption(AllOptions, answers[position], mcqsList.get(position).getAns());
        });
        OptionC.setOnClickListener(view -> {
            mcqsFun.setAllOptionsUnselected(AllOptions);
            answers[position] = 3;
            mcqsFun.checkMCQsOption(AllOptions, answers[position], mcqsList.get(position).getAns());
        });
        OptionD.setOnClickListener(view -> {
            mcqsFun.setAllOptionsUnselected(AllOptions);
            answers[position] = 4;
            mcqsFun.checkMCQsOption(AllOptions, answers[position], mcqsList.get(position).getAns());
        });

        //Get MCQs from database
        DBHelper dbHelper = new DBHelper(this, "MCQS.db");
        selectedClass = getIntent().getIntExtra("selectedClass", 1);
        selectedBook = getIntent().getIntExtra("selectedBook", 1);
        selectedChapter = getIntent().getIntExtra("selectedChapter", 1);
        String tableCode = "10" + selectedClass + "0" + selectedBook +"0" + selectedChapter;
        mcqsList = dbHelper.getMCQSFromDB(tableCode);

        numberOfMCQs = mcqsList.size();
        answers = new short[numberOfMCQs];
        position = 0;
        showMCQsOnTextView();

        //Next Button
        binding.nextBtn.setOnClickListener(view -> {
            if (position<numberOfMCQs-1){
                position++;
                showMCQsOnTextView();
            }else{
                Toast.makeText(this, "No more MCQs", Toast.LENGTH_SHORT).show();
            }
        });

        //Back Button
        binding.backBtn.setOnClickListener(view -> {
            if (position>0){
                position--;
                showMCQsOnTextView();
            }else{
                Toast.makeText(this, "No more MCQs", Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        alertdialog = new AlertDialog.Builder(MCQsActivity.this);
        alertdialog.setMessage("Do you really want to end test?")
                .setPositiveButton("Yes", (dialog, which) ->
                        MCQsActivity.super.onBackPressed())
                .setNegativeButton("No", (dialog, which) -> {}).show();

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void showMCQsOnTextView(){
        MCQS mcqs = mcqsList.get(position);
        mcqs_statement.setText(Html.fromHtml(mcqs.getStatement()));
        OptionA.setText(Html.fromHtml(mcqs.getOptA()));
        OptionB.setText(Html.fromHtml(mcqs.getOptB()));
        OptionC.setText(Html.fromHtml(mcqs.getOptC()));
        OptionD.setText(Html.fromHtml(mcqs.getOptD()));

        mcqsFun.setAllOptionsUnselected(AllOptions);

        switch (answers[position]){
            case 1:
                mcqsFun.setOptionSelected(OptionA);
                break;
            case 2:
                mcqsFun.setOptionSelected(OptionB);
                break;
            case 3:
                mcqsFun.setOptionSelected(OptionC);
                break;
            case 4:
                mcqsFun.setOptionSelected(OptionD);
                break;
        }

        //update question number on textView
        questionNum.setText(position+1+"/"+numberOfMCQs);

    }

}