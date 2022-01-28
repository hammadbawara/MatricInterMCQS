package com.hz_apps.matricintermcqs.Home.MCQS;

import android.widget.TextView;

import com.hz_apps.matricintermcqs.R;

public class MCQsFunctionality {


    public MCQsFunctionality() {
    }

    public void setOptionSelected(TextView option){
        option.setBackgroundResource(R.drawable.selected_mcqs_option);
    }

    public void setOptionUnSelected(TextView option){
        option.setBackgroundResource(R.drawable.unselected_option);
    }

    public void setAllOptionsUnselected(TextView[] options){
        for(TextView option: options){
            setOptionUnSelected(option);
        }
    }

    private void setCorrectOptionSelected(TextView option){
        option.setBackgroundResource(R.drawable.correct_mcqs_option);
    }

    private void setWrongOptionSelected(TextView option){
        option.setBackgroundResource(R.drawable.wrong_mcqs_option);
    }

    public void checkMCQsOption(TextView[] AllOptions, short chosen, char correctAnswer){

        setWrongOptionSelected(AllOptions[chosen-1]);

        switch (correctAnswer){
            case 'A':
                setCorrectOptionSelected(AllOptions[0]);
                break;
            case 'B':
                setCorrectOptionSelected(AllOptions[1]);
                break;
            case 'C':
                setCorrectOptionSelected(AllOptions[2]);
                break;
            case 'D':
                setCorrectOptionSelected(AllOptions[3]);
                break;
        }

    }

}
