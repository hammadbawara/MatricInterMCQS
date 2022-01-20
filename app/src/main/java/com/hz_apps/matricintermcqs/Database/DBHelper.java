package com.hz_apps.matricintermcqs.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {

    private String DB_Name, TableName, DBPath;
    private Context context;

    public DBHelper(@Nullable Context context, String db_name,@Nullable String tableName) {
        super(context, db_name, null, 1);
        DB_Name = db_name;
        TableName = tableName;
        this.context = context;
        DBPath = "data/data/"+ context.getPackageName()+"/databases/";
    }

    public boolean isDatabaseExist(){
        File file = new File(DBPath+DB_Name);
        return file.exists();
    }
    public void copyFileFromAssetToDatabaseFolder() throws IOException {
        InputStream inputStream = context.getAssets().open(DB_Name);
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
}
