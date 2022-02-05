package com.hz_apps.matricintermcqs.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DBDao {

    @Query("SELECT * FROM savedTest")
    LiveData<List<SavedTest>> getAllSavedTests();

    @Query("DELETE FROM savedTest WHERE id = :id")
    void deleteSavedTestById(int id);

    @Query("DELETE FROM SAVEDTEST")
    void deleteAllFromSavedTests();

}
