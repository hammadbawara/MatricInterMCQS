package com.hz_apps.matricintermcqs.Home.TestSetup;

public class Test {
    private String title;
    private int numberOfQuestions;

    public Test(){

    }

    public Test(String title, int numberOfQuestions) {
        this.title = title;
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }
}
