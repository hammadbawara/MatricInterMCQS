package com.hz_apps.matricintermcqs.saved.SavedTest;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hz_apps.matricintermcqs.Database.DBRepository;
import com.hz_apps.matricintermcqs.Database.SavedTest;

import java.util.List;

public class SavedTestFragmentModel extends ViewModel {

    private DBRepository dbRepository;

    public LiveData<List<SavedTest>> getAllSavedTests(Application application){
        dbRepository = new DBRepository(application);
        return dbRepository.getGetAllSavedTests();
    }

    public void deleteSavedTest(int id){
        dbRepository.deleteSavedTest(id);
    }

}
