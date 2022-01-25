package com.hz_apps.matricintermcqs.home.SelectChapter;

public class BookChapter {
    private int chapterNo;
    private String chapterName;
    private int numberOfQuestion;

    public BookChapter(){

    }

    public BookChapter(int chapterNo, String chapterName, int numberOfQuestion) {
        this.chapterNo = chapterNo;
        this.chapterName = chapterName;
        this.numberOfQuestion = numberOfQuestion;
    }

    public int getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public int getNumberOfQuestion() {
        return numberOfQuestion;
    }

    public void setNumberOfQuestion(int numberOfQuestion) {
        this.numberOfQuestion = numberOfQuestion;
    }
}
