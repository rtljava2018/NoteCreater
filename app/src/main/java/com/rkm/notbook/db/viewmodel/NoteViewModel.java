package com.rkm.notbook.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rkm.notbook.db.model.NoteModel;
import com.rkm.notbook.db.repositry.NoteRepositry;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepositry repositry;
    private LiveData<NoteModel> noteModelLiveData;
    private LiveData<List<NoteModel>> noteListLiveData;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repositry=new NoteRepositry(application);
        noteListLiveData=repositry.getListOfLiveData();
    }

    public LiveData<List<NoteModel>> getLiveList(){
        return noteListLiveData;
    }

    public LiveData<NoteModel> getMOdelItem(String id){
        return repositry.getLiveModel(id);
    }

    public void inserData(NoteModel model){
        repositry.insertItem(model);
    }
}
