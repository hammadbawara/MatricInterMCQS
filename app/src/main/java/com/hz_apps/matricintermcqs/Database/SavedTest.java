package com.hz_apps.matricintermcqs.Database;

public class SavedTest {
    private String TestTitle;
    private String className;
    private String subject;
    private String tableName;
    private int position;

    public SavedTest(){

    }

    public SavedTest(String className, String subject, String tableName) {
        this.className = className;
        this.subject = subject;
        this.tableName = tableName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTestTitle() {
        return TestTitle;
    }

    public void setTestTitle(String testTitle) {
        TestTitle = testTitle;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
