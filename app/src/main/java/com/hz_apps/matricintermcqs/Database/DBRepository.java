package com.hz_apps.matricintermcqs.Database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DBRepository {

    private final DBDao dbDao;
    private final LiveData<List<SavedTest>> getAllSavedTests;

    public DBRepository(Application application) {
        RoomDB database = RoomDB.getInstance(application);
        dbDao  = database.dbDao();
        getAllSavedTests = dbDao.getAllSavedTests();
    }

    public LiveData<List<SavedTest>> getGetAllSavedTests() {
        return getAllSavedTests;
    }

    public void deleteSavedTest(int id){
        RoomDB.executorService.execute(() -> dbDao.deleteSavedTestById(id));
    }
}
