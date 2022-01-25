package com.hz_apps.matricintermcqs.home.MCQS;

public class MCQS {
    private int id;
    private String statement, optA, optB, optC, optD;
    private char ans;

    public MCQS(){

    }

    public MCQS(int number, String statement, String optA, String optB, String optC, String optD, char ans) {
        this.id = number;
        this.statement = statement;
        this.optA = optA;
        this.optB = optB;
        this.optC = optC;
        this.optD = optD;
        this.ans = ans;
    }

    public int getId() {
        return id;
    }

    public void setId(int number) {
        this.id = number;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public String getOptA() {
        return optA;
    }

    public void setOptA(String optA) {
        this.optA = optA;
    }

    public String getOptB() {
        return optB;
    }

    public void setOptB(String optB) {
        this.optB = optB;
    }

    public String getOptC() {
        return optC;
    }

    public void setOptC(String optC) {
        this.optC = optC;
    }

    public String getOptD() {
        return optD;
    }

    public void setOptD(String optD) {
        this.optD = optD;
    }

    public char getAns() {
        return ans;
    }

    public void setAns(char ans) {
        this.ans = ans;
    }
}
