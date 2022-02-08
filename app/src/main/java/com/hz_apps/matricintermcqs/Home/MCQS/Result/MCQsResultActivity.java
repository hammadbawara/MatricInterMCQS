package com.hz_apps.matricintermcqs.Home.MCQS.Result;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hz_apps.matricintermcqs.Home.MCQS.MCQS;
import com.hz_apps.matricintermcqs.databinding.ActivityMcqsResultBinding;

import java.util.List;

public class MCQsResultActivity extends AppCompatActivity {
    private List<MCQS> mcqsList;
    int TotalQuestions, Attempted, CorrectAnswers, WrongAnswers, Unattempted;
    int accuracy;
    ActivityMcqsResultBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMcqsResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mcqsList = (List<MCQS>) getIntent().getSerializableExtra("MCQsList");
        getMCQsResult();

        binding.correctAnswersMcqsResult.setText(String.valueOf(CorrectAnswers));
        binding.wrongMcqsResult.setText(String.valueOf(WrongAnswers));
        binding.unattemptedMcqsResult.setText(String.valueOf(Unattempted));
        binding.accuracyResultActivity.setText(accuracy + "%");
        binding.pointsResultActivity.setText(String.valueOf(CorrectAnswers*10));

    }

    private void getMCQsResult(){
        TotalQuestions = mcqsList.size();
        for (MCQS mcqs : mcqsList){
            if (mcqs.getUserAns() == 'N'){
                Unattempted += 1;
            }
            else{
                Attempted +=1;
                if (mcqs.getUserAns() == mcqs.getAns()){
                    CorrectAnswers += 1;
                }else{
                    WrongAnswers += 1;
                }
            }
        }
        //Finding percentage
        float attemptedQuestions = (float) Attempted;
        float correctAnswers = (float) CorrectAnswers;
        accuracy = (int) ((correctAnswers/attemptedQuestions)*100);
    }
}