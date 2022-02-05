package com.hz_apps.matricintermcqs.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.hz_apps.matricintermcqs.Home.MCQS.MCQS;
import com.hz_apps.matricintermcqs.Home.SelectChapter.BookChapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DBHelper extends SQLiteOpenHelper {

    private final String DB_Name;
    private final String DBPath;
    private final Context context;
    private SQLiteDatabase db;

    public DBHelper(@Nullable Context context, String db_name) {
        super(context, db_name, null, 1);
        DB_Name = db_name;
        this.context = context;
        assert context != null;
        DBPath = "data/data/"+ context.getPackageName()+"/databases/";
    }

    public boolean isDatabaseExist(){
        File file = new File(DBPath+DB_Name);
        return file.exists();
    }

    /*
    This function runs when database does not exist.
    It simply copy database file from the 'Assets' of app and then paste it in this location
    data/data/<package_name>
     */
    public void copyFileFromAssetToDatabaseFolder() throws IOException {
        InputStream inputStream = context.getAssets().open(DB_Name);
        new File(DBPath).mkdir();
        OutputStream outputStream = new FileOutputStream(DBPath+DB_Name);
        byte[] buffer = new byte[1024];
        int length;
        while((length = inputStream.read(buffer))>0){
            outputStream.write(buffer, 0, length);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*
    As the name suggest this function get chapters list from the database on the basis
    of class and book.
     */
    @SuppressLint("Recycle")
    public List<BookChapter> getBookChapters(int Class, int Book){
        String tableName = generateTableName(Class, Book);
        Cursor cursor = null;
        db = getReadableDatabase();
        if (db!=null){
            cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        }
        List<BookChapter> chapterList = new ArrayList<>();
        assert cursor != null;
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                BookChapter chapter = new BookChapter();
                chapter.setChapterNo(cursor.getInt(0));
                chapter.setChapterName(cursor.getString(1));
                chapter.setNumberOfQuestion(cursor.getInt(2));
                chapterList.add(chapter);
            }
        }
        return chapterList;
    }

    /*
    This function is used to get number of MCQs of the chapter.
    These number of MCQs later used in creating user test.
     */
    public int getNumberOfMCQs(String tableName){
        int numberOfMCQs = 0;
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        if (cursor.getCount()!=0){
            while(cursor.moveToNext())
                numberOfMCQs = cursor.getInt(0);
        }
        return numberOfMCQs;
    }

    /*
    This function take starting row and last row and then it fetch all all the mcqs within these rows.
    If user want to give the test1 then it will only get first 12 mcqs from the from the chapter mcqs.
     */
    public List<MCQS> getMCQsWithRowId(String tableName, int start, int end){
        db = getReadableDatabase();
        Cursor cursor = null;
        if (db!=null){
            cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE rowid BETWEEN " + start + " AND " + end, null);
        }
        List<MCQS> mcqsList = new ArrayList<>();
        if (cursor!=null){
            while (cursor.moveToNext()){
                MCQS mcqs = new MCQS();
                mcqs.setId(cursor.getInt(0));
                mcqs.setStatement(cursor.getString(1));
                mcqs.setOptA(cursor.getString(2));
                mcqs.setOptB(cursor.getString(3));
                mcqs.setOptC(cursor.getString(4));
                mcqs.setOptD(cursor.getString(5));
                char[] ans = cursor.getString(6).toCharArray();
                mcqs.setAns(ans[0]);
                mcqsList.add(mcqs);
            }
        }
        return mcqsList;
    }

    public String[] getQuote(){
        db = getReadableDatabase();
        Random random = new Random();
        int quoteNumber = random.nextInt(90);
        Cursor cursor = null;
        if (db !=null){
            cursor = db.rawQuery("SELECT quote, author FROM quotes WHERE rowid = " + ++quoteNumber, null);
        }
        String[] quote = new String[2];
        if (cursor!=null){
            while (cursor.moveToNext()){
                quote[0] = cursor.getString(0);
                quote[1] = cursor.getString(1);
            }
        }
        return quote;
    }

    /*
    This function is overloaded.
    This function is made for when user select multiple chapters so it count all the mcqs in those chapters
     */
    public int getNumberOfMCQs(BookChapter[] chapterList, int selectedBook, int selectedClass){
        int numberOfMCQs = 0;
        for(int i=0; i< chapterList.length; i++){
            if (chapterList[i] != null) {
                String tableName = generateTableName(selectedClass, selectedBook, chapterList[i].getChapterNo());
                numberOfMCQs += getNumberOfMCQs(tableName);
            }
        }
        return numberOfMCQs;
    }


    public String generateTableName(int selectedClass, int selectedBook, int selectedChapter){
        String tableCode;
        if (selectedChapter<10) tableCode = "10" + selectedClass + "0" + selectedBook +"0" + selectedChapter;
        else tableCode = "10" + selectedClass + "0" + selectedBook + selectedChapter;
        long x = Long.parseLong(tableCode);
        x ^= (x << 21);
        x ^= (x >> 35);
        x ^= (x << 4);
        return "hz" + x;
    }

    /*
    This function get mcqs from the multiple chapters.
     */
    public List<MCQS> getMCQsOfChapters(BookChapter[] bookChapters, int numberOfMCQs, int selectedClass, int selectedBook){
        List<MCQS> allMCQsList = new ArrayList<>(numberOfMCQs);
        for (int i=0; i<bookChapters.length;i++){
            if (bookChapters[i] != null) {
                String tableName = generateTableName(selectedClass, selectedBook, bookChapters[i].getChapterNo());
                List<MCQS> mcqsList = getChapterMCQs(tableName);
                allMCQsList.addAll(mcqsList);
            }
        }
        return allMCQsList;
    }

    private List<MCQS> getChapterMCQs(String tableName){
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        List<MCQS> mcqsList = new ArrayList<>();
        if (cursor!=null){
            while (cursor.moveToNext()){
                MCQS mcqs = new MCQS();
                mcqs.setId(cursor.getInt(0));
                mcqs.setStatement(cursor.getString(1));
                mcqs.setOptA(cursor.getString(2));
                mcqs.setOptB(cursor.getString(3));
                mcqs.setOptC(cursor.getString(4));
                mcqs.setOptD(cursor.getString(5));
                char[] ans = cursor.getString(6).toCharArray();
                mcqs.setAns(ans[0]);
                mcqsList.add(mcqs);
            }
        }
        return mcqsList;
    }

    public String generateTableName(int selectedClass, int selectedBook){
        long x = Long.parseLong("10" + selectedClass +"0"+ selectedBook);
        x ^= (x << 21);
        x ^= (x >> 35);
        x ^= (x << 4);
        return "hz" + x;
    }

}
