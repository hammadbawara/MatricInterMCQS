package com.hz_apps.matricintermcqs.saved.SavedTest;

public class SavedTest {
    private String className;
    private String subject;
    private String tableName;

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
}
