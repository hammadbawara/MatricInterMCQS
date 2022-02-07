package com.hz_apps.matricintermcqs.Database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = SavedTest.class, version = 1)
public abstract class RoomDB extends RoomDatabase {

    abstract DBDao dbDao();

    private static volatile RoomDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RoomDB getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (RoomDatabase.class){
                if (INSTANCE == null)
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                        RoomDB.class, "userDB.db")
                        .fallbackToDestructiveMigration()
                        .addCallback(sRoomDatabaseCallBack)
                        .build();
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback sRoomDatabaseCallBack = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            executorService.execute(() -> {
                DBDao dbDao = INSTANCE.dbDao();
                dbDao.deleteAllFromSavedTests();
            });
        }
    };
}
