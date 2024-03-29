package com.hz_apps.matricintermcqs.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hz_apps.matricintermcqs.Home.MCQS.MCQS;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class UserDatabase extends SQLiteOpenHelper {

    private final String SAVED_TEST_TABLE = "savedTest";
    private final Context context;

    SQLiteDatabase db;
    public UserDatabase(@Nullable Context context) {
        super(context, "userDB.db", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `savedTest` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `TestTitle` TEXT, `className` TEXT, `BookIcon` INTEGER NOT NULL, `tableName` TEXT, `position` INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<SavedTest> getAllSavedTestsList(){
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + SAVED_TEST_TABLE, null);
        List<SavedTest> savedTestList = new ArrayList<>();
        if (cursor.getCount() != 0){
            while (cursor.moveToNext()){
                SavedTest test = new SavedTest();
                test.setId(cursor.getInt(0));
                test.setTestTitle(cursor.getString(1));
                test.setClassName(cursor.getString(2));
                test.setBookIcon(cursor.getInt(3));
                test.setTableName(cursor.getString(4));
                test.setPosition(cursor.getInt(5));
                savedTestList.add(test);
            }
        }
        return savedTestList;
    }

    public void saveTest(List<MCQS> mcqsList, String TestTitle, int position, String ClassName, int BookIcon){
        db = getWritableDatabase();
        Calendar calendar = new GregorianCalendar();
        String tableName = "st" + calendar.getTimeInMillis();

        db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, statement TEXT, optA TEXT, optB TEXT, optC TEXT, optD TEXT, ans TEXT, userAns TEXT)");
        for (MCQS mcqs : mcqsList){
            ContentValues values = new ContentValues();
            values.put("statement", mcqs.getStatement());
            values.put("optA", mcqs.getOptA());
            values.put("optB", mcqs.getOptB());
            values.put("optC", mcqs.getOptC());
            values.put("optD", mcqs.getOptD());
            String ans = String.valueOf(mcqs.getAns());
            values.put("ans", ans);
            String userAns = String.valueOf(mcqs.getUserAns());
            values.put("userAns", userAns);
            db.insert(tableName, null, values);
        }
        ContentValues values = new ContentValues();
        values.put("TestTitle", TestTitle);
        values.put("className", ClassName);
        values.put("BookIcon", BookIcon);
        values.put("tableName", tableName);
        values.put("position", position);
        db.insert(SAVED_TEST_TABLE, null, values);
    }

    public List<MCQS> getSavedTest(String tableName){
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        List<MCQS> mcqsList = new ArrayList<>();
        if (cursor.getCount()!=0) {
            while (cursor.moveToNext()) {
                MCQS mcqs = new MCQS();
                mcqs.setStatement(cursor.getString(1));
                mcqs.setOptA(cursor.getString(2));
                mcqs.setOptB(cursor.getString(3));
                mcqs.setOptC(cursor.getString(4));
                mcqs.setOptD(cursor.getString(5));
                mcqs.setAns(cursor.getString(6).toCharArray()[0]);
                mcqs.setUserAns(cursor.getString(7).toCharArray()[0]);
                mcqsList.add(mcqs);
            }
        }else{
            Toast.makeText(context, "Test not found", Toast.LENGTH_SHORT).show();
        }

        return mcqsList;

    }

    public void deleteSavedTest(int id, String tableName){
        db = getWritableDatabase();
        db.execSQL("DELETE FROM savedTest WHERE id=" + id );
        db.execSQL("DROP TABLE " + tableName);
    }

    public void updateSavedTest(List<MCQS> mcqsList, int position, int id, String tableName){

        db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Position", position);
        db.update(SAVED_TEST_TABLE, values, " id = " + id, null);

        for (int i=0; i<mcqsList.size()-1;i++){
            ContentValues value = new ContentValues();
        }
    }


}
