package com.hz_apps.matricintermcqs.Home.MCQS;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hz_apps.matricintermcqs.Database.DBHelper;
import com.hz_apps.matricintermcqs.Database.UserDatabase;
import com.hz_apps.matricintermcqs.Home.TestSetup.Test;
import com.hz_apps.matricintermcqs.databinding.ActivityMcqsBinding;
import com.hz_apps.matricintermcqs.databinding.InputEditTextViewBinding;

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
    public static List<MCQS> mcqsList;
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
        setClickListenerOnAllOptions(AllOptions);

        //Getting information from previous fragment
        String TableName = getIntent().getStringExtra("TableName");
        Test test = (Test) getIntent().getSerializableExtra("TestObject");

        //set chapter name
        binding.chapterNameMcqsActivity.setText(test.getTitle());

        //Get MCQs from database
        DBHelper dbHelper = new DBHelper(this, "MCQS.db");

        /*
        This generate database table code on the basis of user class, book and chapter.
        This table code is used for generating table name and then that table name is used
        to get data from database.
         */
        //Getting MCQs list from Database
        mcqsList = dbHelper.getMCQsWithRowId(TableName, test.getStartPosition(), test.getEndPosition());

        numberOfMCQs = mcqsList.size();
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
        //set option as selected that user last time selected
        TextView option = mcqsFun.getOptionByChar(AllOptions, mcqs.getUserAns());
        if (option != null) mcqsFun.setOptionSelected(option);

        //Update question number on the top right corner of Toolbar
        questionNum.setText(position+1+"/"+numberOfMCQs);

    }


    @Override
    public void onBackPressed() {
        alertdialog = new AlertDialog.Builder(MCQsActivity.this);
        alertdialog.setMessage("Do you really want to end test?")
                .setPositiveButton("Yes", (dialog, which) ->
                        MCQsActivity.super.onBackPressed())
                .setNegativeButton("No", (dialog, which) -> {});
        alertdialog.setNeutralButton("Save Test", (dialogInterface, i) -> {
            askUserSaveTestNameDialog();
        });
        alertdialog.show();

    }

    private void setClickListenerOnAllOptions(TextView[] options){
        char[] choice = new char[] {'A', 'B', 'C', 'D'};
        for (int i=0; i< options.length; i++){
            int finalI = i;
            options[i].setOnClickListener(view -> {
                //Unselect Previous Option
                MCQS mcqs = mcqsList.get(position);
                mcqs.setUserAns(choice[finalI]);
                mcqsFun.checkMCQsOption(options, mcqs.getUserAns(), mcqs.getAns());
            });
        }
    }

    private void askUserSaveTestNameDialog(){
        InputEditTextViewBinding binding = InputEditTextViewBinding.inflate(getLayoutInflater());
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(binding.getRoot());
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.setPositiveButton("Save", (dialogInterface, i) -> {
            String testTitle = binding.inputEditText.getText().toString();
            if (!testTitle.isEmpty()){
                UserDatabase userDatabase = new UserDatabase(this);
                userDatabase.saveTest(mcqsList, testTitle, "9th", "Physics");
            }
            else binding.inputEditText.setError("Assign some name to saved Test");
            MCQsActivity.super.onBackPressed();
            Toast.makeText(this, "Test Saved", Toast.LENGTH_SHORT).show();
        });
        dialog.show();
    }

}