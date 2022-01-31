package com.hz_apps.matricintermcqs.Home.TestSetup;

public class Test {
    private String title;
    private int numberOfQuestions;
    private int startPosition;
    private int endPosition;

    public Test(){

    }

    public Test(String title, int numberOfQuestions, int startPosition, int endPosition) {
        this.title = title;
        this.numberOfQuestions = numberOfQuestions;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
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

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }
}
