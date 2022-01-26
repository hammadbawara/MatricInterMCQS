package com.hz_apps.matricintermcqs.home.MCQS;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.hz_apps.matricintermcqs.Database.DBHelper;
import com.hz_apps.matricintermcqs.R;
import com.hz_apps.matricintermcqs.databinding.ActivityMcqsBinding;

import java.util.List;

public class MCQsActivity extends AppCompatActivity {

    private TextView OptionA, OptionB, OptionC, OptionD, mcqs_statement;
    private int selectedOption = 0;
    private AlertDialog.Builder alertdialog;
    ActivityMcqsBinding binding;
    private int position;
    private int selectedClass, selectedBook, selectedChapter;
    private List<MCQS> mcqsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMcqsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading MCQs");
        progressDialog.setCancelable(false);

        OptionA = findViewById(R.id.OptionA);
        OptionB = findViewById(R.id.OptionB);
        OptionC = findViewById(R.id.OptionC);
        OptionD = findViewById(R.id.OptionD);
        mcqs_statement = findViewById(R.id.mcqs_statement);
        ClickEffectOnOptions();

        DBHelper dbHelper = new DBHelper(this, "MCQS.db");
        selectedClass = getIntent().getIntExtra("selectedClass", 1);
        selectedBook = getIntent().getIntExtra("selectedBook", 1);
        selectedChapter = getIntent().getIntExtra("selectedChapter", 1);
        String tableCode = "10" + selectedClass + "0" + selectedBook +"0" + selectedChapter;
        mcqsList = dbHelper.getMCQSFromDB(tableCode);
        position = 0;
        showMCQSonTextView();

        binding.nextBtn.setOnClickListener(view -> {
            showMCQSonTextView();
        });
        progressDialog.dismiss();
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
        alertdialog = new AlertDialog.Builder(MCQsActivity.this);
        alertdialog.setMessage("Do you really want to end test?")
                .setPositiveButton("Yes", (dialog, which) ->
                        MCQsActivity.super.onBackPressed())
                .setNegativeButton("No", (dialog, which) -> {}).show();

    }

    private void showMCQSonTextView(){
        if (position<mcqsList.size()-1) {
            MCQS mcqs = mcqsList.get(position);
            mcqs_statement.setText(Html.fromHtml(mcqs.getStatement()));
            OptionA.setText(Html.fromHtml(mcqs.getOptA()));
            OptionB.setText(Html.fromHtml(mcqs.getOptB()));
            OptionC.setText(Html.fromHtml(mcqs.getOptC()));
            OptionD.setText(Html.fromHtml(mcqs.getOptD()));
            position++;
        }
        else{
            Toast.makeText(this, "No More MCQs", Toast.LENGTH_SHORT).show();
        }
    }
}