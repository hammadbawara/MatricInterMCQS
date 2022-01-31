package com.hz_apps.matricintermcqs.Home.MCQS;

import android.widget.TextView;

import com.hz_apps.matricintermcqs.R;

public class MCQsFunctionality {

    public MCQsFunctionality(){

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

    private void setCorrectOptionSelected(TextView[] options, char target){
        setCorrectOptionSelected(getOptionByChar(options, target));
    }

    private void setWrongOptionSelected(TextView option){
        option.setBackgroundResource(R.drawable.wrong_mcqs_option);
    }

    private void setWrongOptionSelected(TextView[] options, char target){
        setWrongOptionSelected(getOptionByChar(options, target));
    }

    public TextView getOptionByChar(TextView[] allOptions, char target){
        switch (target){
            case 'A':
                return allOptions[0];
            case 'B':
                return allOptions[1];
            case 'C':
                return allOptions[2];
            case 'D':
                return allOptions[3];
        }
        return null;
    }



    /*
    This method take all options, user selected option and correct answer.
    Fist it set 'red' color on user selected option and then it set 'green' color on right option.
     */
    public void checkMCQsOption(TextView[] AllOptions, char chosen, char correctAnswer){
        setAllOptionsUnselected(AllOptions);
        setWrongOptionSelected(AllOptions, chosen);
        setCorrectOptionSelected(AllOptions, correctAnswer);
    }

}
