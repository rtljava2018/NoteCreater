package com.rkm.notbook.db.repositry;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.rkm.notbook.db.NoteDB;
import com.rkm.notbook.db.dao.NoteDao;
import com.rkm.notbook.db.model.NoteModel;

import java.util.List;

public class NoteRepositry {
    private NoteDao mDao;
    private LiveData<List<NoteModel>> listLiveData;
    private LiveData<NoteModel> noteModelLiveData;

    public NoteRepositry(Application application) {
        NoteDB noteDB = NoteDB.getINSTANCE(application);
        mDao = noteDB.noteDao();
    }

    public NoteRepositry(Context context) {
        NoteDB noteDB = NoteDB.getINSTANCE(context);
        mDao = noteDB.noteDao();

    }

    public void insertItem(NoteModel noteModel){
        new InsertAsynctaskItem(mDao,noteModel).execute();
    }
    public void insertListOfItem(List<NoteModel> noteModel){
        new InsertAsynctaskList(mDao,noteModel).execute();
    }

    public LiveData<List<NoteModel>> getListOfLiveData(){
        return mDao.getListOfPlantsLivedata();
    }

    public LiveData<NoteModel> getLiveModel(String id){
        return mDao.getNoteModelDetailsById(id);
    }

    private class InsertAsynctaskList extends AsyncTask<Void, Void, Void> {
        List<NoteModel> value;
        NoteDao mDao;

        public InsertAsynctaskList(NoteDao mDao, List<NoteModel> value) {
            this.value = value;
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (NoteModel item : value) {
                mDao.insertItem(item);
            }

            return null;
        }
    }

    private class InsertAsynctaskItem extends AsyncTask<Void, Void, Void> {
        NoteModel value;
        NoteDao mDao;

        public InsertAsynctaskItem(NoteDao mDao, NoteModel value) {
            this.value = value;
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.insertItem(value);
            return null;
        }
    }
}
