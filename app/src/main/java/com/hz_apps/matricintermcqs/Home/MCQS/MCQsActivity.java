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

    /*
    position - This is a variable that change with the MCQs. It tells us that on which MCQs user is right now.
    MCQsFunctionality (mcqsFun) - This is class that made for hiding complexity. In this class many function are addedd
                for lowering code on MainActivity class.
     */


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
        //This prevent screen from rotation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding.toolbarMcqsActivity.setNavigationOnClickListener(view -> onBackPressed());
        mcqsFun = new MCQsFunctionality();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading MCQs");
        progressDialog.setCancelable(false);

        //Assigning values through binding
        OptionA = binding.OptionA;
        OptionB = binding.OptionB;
        OptionC = binding.OptionC;
        OptionD = binding.OptionD;
        questionNum = binding.questionNum;
        mcqs_statement = binding.mcqsStatement;
        AllOptions = new TextView[] {OptionA, OptionB, OptionC, OptionD};

        /*
        When someone click on option. First it will remove drawable from all the options
        then it will set 'red' on the selected option. After that it set 'green' on the right option.
        if user clicked (or selected) on right option then that option color become 'green' else it remain red.
         */
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

        //Getting information from previous fragment
        String TestTitle = getIntent().getStringExtra("testTitle");
        String TableName = getIntent().getStringExtra("TableName");
        int[] testMCQs = getIntent().getIntArrayExtra("TestMCQS");

        //set chapter name
        binding.chapterNameMcqsActivity.setText(TestTitle);

        //Get MCQs from database
        DBHelper dbHelper = new DBHelper(this, "MCQS.db");

        /*
        This generate database table code on the basis of user class, book and chapter.
        This table code is used for generating table name and then that table name is used
        to get data from database.
         */
        //Getting MCQs list from Database
        mcqsList = dbHelper.getMCQsWithRowId(TableName, testMCQs[0], testMCQs[1]);

        numberOfMCQs = mcqsList.size();
        answers = new short[numberOfMCQs];
        position = 0;
        setMCQsOnTextViews();

        //Next Button
        binding.nextBtn.setOnClickListener(view -> {
            if (position<numberOfMCQs-1){
                position++;
                setMCQsOnTextViews();
            }else{
                Toast.makeText(this, "No more MCQs", Toast.LENGTH_SHORT).show();
            }
        });

        //Back Button
        binding.backBtn.setOnClickListener(view -> {
            if (position>0){
                position--;
                setMCQsOnTextViews();
            }else{
                Toast.makeText(this, "No more MCQs", Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.dismiss();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setMCQsOnTextViews(){
        MCQS mcqs = mcqsList.get(position);
        mcqs_statement.setText(Html.fromHtml(mcqs.getStatement()));
        OptionA.setText(Html.fromHtml(mcqs.getOptA()));
        OptionB.setText(Html.fromHtml(mcqs.getOptB()));
        OptionC.setText(Html.fromHtml(mcqs.getOptC()));
        OptionD.setText(Html.fromHtml(mcqs.getOptD()));

        //Unselect all options for new MCQs
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

        //Update question number on the top right corner of Toolbar
        questionNum.setText(position+1+"/"+numberOfMCQs);

    }


    @Override
    public void onBackPressed() {
        alertdialog = new AlertDialog.Builder(MCQsActivity.this);
        alertdialog.setMessage("Do you really want to end test?")
                .setPositiveButton("Yes", (dialog, which) ->
                        MCQsActivity.super.onBackPressed())
                .setNegativeButton("No", (dialog, which) -> {}).show();

    }

}