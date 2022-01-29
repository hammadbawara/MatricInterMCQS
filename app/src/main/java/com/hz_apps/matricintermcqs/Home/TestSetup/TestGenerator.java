package com.hz_apps.matricintermcqs.Home.TestSetup;

public class TestGenerator {

    Test[] TestsWithName;
    /*
    This method take total number of questions and generate 2D array. In each array one element is test starting question
    other element is test ending question
     */

    public int[][] generateTest(int totalQuestions){

        int TotalNumberOfTests = (totalQuestions/12)+1;
        TestsWithName = new Test[TotalNumberOfTests];

        int[][] all_tests = new int[TotalNumberOfTests][];
        int start = 0;
        for (int i = 0; i < TotalNumberOfTests-2; i++) {
            all_tests[i] = new int[] {start+1, start+12};
            TestsWithName[i] = new Test("Test " + (i+1), 12);
            start+=12;
        }

        int secondLastTest = TotalNumberOfTests-2;
        all_tests[secondLastTest] = new int[] {start+1, totalQuestions};
        all_tests[secondLastTest+1] = new int[] {1, totalQuestions};

        TestsWithName[secondLastTest] = new Test("Test " +secondLastTest, totalQuestions-start);
        TestsWithName[secondLastTest+1] = new Test("Complete chapter MCQs", totalQuestions);
        return all_tests;
    }

}
