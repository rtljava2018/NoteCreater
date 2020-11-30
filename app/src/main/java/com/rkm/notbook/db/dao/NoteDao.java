package com.rkm.notbook.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.rkm.notbook.db.model.NoteModel;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(NoteModel item);

    @Query("SELECT * from note_details order by pinNote is 0 ,timeForaddNote DESC")
    LiveData<List<NoteModel>> getListOfPlantsLivedata();
    @Query("SELECT * from note_details where notId =:notID Limit 1")
    LiveData<NoteModel> getNoteModelDetailsById(String notID);

}
