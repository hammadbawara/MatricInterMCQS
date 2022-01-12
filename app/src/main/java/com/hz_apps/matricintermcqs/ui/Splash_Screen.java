package com.hz_apps.matricintermcqs.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hz_apps.matricintermcqs.MainActivity;
import com.hz_apps.matricintermcqs.R;

import java.io.IOException;

public class Splash_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Thread(){
            @Override
            public void run(){
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Importing Database
                try {
                    ImportDatabaseFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startActivity(new Intent(Splash_Screen.this, MainActivity.class));
                Splash_Screen.this.finish();
            }
        }.start();

    }
    private void ImportDatabaseFile() throws IOException {
        DBHelper dbHelper = new DBHelper(Splash_Screen.this, "MCQS.db", null);
        if (!dbHelper.isDatabaseExist()) {
            dbHelper.copyFileFromAssetToDatabaseFolder();
        }
    }
}