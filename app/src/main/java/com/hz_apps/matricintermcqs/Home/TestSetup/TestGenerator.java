package com.hz_apps.matricintermcqs.Home.TestSetup;

public class TestGenerator {

    /*
    This method take total number of questions and generate 2D array. In each array one element is test starting question
    other element is test ending question
     */

    public Test[] generateTest(int totalQuestions){

        int TotalNumberOfTests = (totalQuestions/12)+1;
        Test[] tests = new Test[TotalNumberOfTests];

        int start = 0;
        for (int i = 0; i < TotalNumberOfTests-2; i++) {
            tests[i] = new Test("Test " + (i+1), 12, start+1, start+12);
            start+=12;
        }

        tests[TotalNumberOfTests-2] = new Test("Test " +(TotalNumberOfTests-2), totalQuestions-start, start+1, totalQuestions);
        tests[TotalNumberOfTests-1] = new Test("Complete chapter MCQs", totalQuestions, 1, totalQuestions);
        return tests;
    }

}
