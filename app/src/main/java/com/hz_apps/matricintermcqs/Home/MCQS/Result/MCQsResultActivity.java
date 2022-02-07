package com.hz_apps.matricintermcqs.Home.MCQS.Result;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hz_apps.matricintermcqs.Home.MCQS.MCQS;
import com.hz_apps.matricintermcqs.R;
import com.hz_apps.matricintermcqs.databinding.ActivityMcqsResultBinding;

import java.util.List;

public class MCQsResultActivity extends AppCompatActivity {
    private List<MCQS> mcqsList;
    int TotalQuestions, AnsweredQuestions, CorrectAnswers, WrongAnswers, UnAnswered;
    ActivityMcqsResultBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMcqsResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mcqsList = (List<MCQS>) getIntent().getSerializableExtra("MCQsList");
        getMCQsResult();

        binding.totalQuestionsMcqsResult.setText(String.valueOf(TotalQuestions));
        binding.answeredQuestionsMcqsResult.setText(String.valueOf(AnsweredQuestions));
        binding.correctAnswersMcqsResult.setText(String.valueOf(CorrectAnswers));
        binding.wrongQuestionsMcqsResult.setText(String.valueOf(WrongAnswers));
        binding.unansweredQuestionsMcqsResult.setText(String.valueOf(UnAnswered));

    }

    private void getMCQsResult(){
        TotalQuestions = mcqsList.size();
        for (MCQS mcqs : mcqsList){
            if (mcqs.getUserAns() == 'N'){
                UnAnswered += 1;
            }
            else{
                AnsweredQuestions +=1;
                if (mcqs.getUserAns() == mcqs.getAns()){
                    CorrectAnswers += 1;
                }else{
                    WrongAnswers += 1;
                }
            }
        }
    }
}