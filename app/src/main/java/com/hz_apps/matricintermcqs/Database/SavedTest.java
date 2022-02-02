package com.hz_apps.matricintermcqs.Database;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "savedTest")
public class SavedTest {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String TestTitle;
    private String className;
    private int BookIcon;
    private String tableName;
    private int position;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTestTitle() {
        return TestTitle;
    }

    public void setTestTitle(String testTitle) {
        TestTitle = testTitle;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getBookIcon() {
        return BookIcon;
    }

    public void setBookIcon(int bookIcon) {
        BookIcon = bookIcon;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
