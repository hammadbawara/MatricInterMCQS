package com.hz_apps.matricintermcqs.Database;

public class SavedTest {
    private String TestTitle, ClassName, BookName, TableName;
    private int position;



    public SavedTest() {
    }

    public String getTestTitle() {
        return TestTitle;
    }

    public void setTestTitle(String testTitle) {
        TestTitle = testTitle;
    }

    public String getClassName() {
        return ClassName;
    }

    public void setClassName(String className) {
        ClassName = className;
    }

    public String getBookName() {
        return BookName;
    }

    public void setBookName(String bookName) {
        BookName = bookName;
    }

    public String getTableName() {
        return TableName;
    }

    public void setTableName(String tableName) {
        TableName = tableName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
