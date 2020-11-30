package com.rkm.notbook.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rkm.notbook.db.dao.NoteDao;
import com.rkm.notbook.db.model.NoteModel;

@Database(entities = {NoteModel.class}, version = 1,exportSchema = false)
public abstract class NoteDB extends RoomDatabase {

    private static NoteDB INSTANCE;
    private static String DB_NAME = "not_track";

    public abstract NoteDao noteDao();

    public static NoteDB getINSTANCE(final Context mContext) {

        if (INSTANCE == null) {
            synchronized (NoteDB.class){
                INSTANCE= Room.databaseBuilder(mContext.getApplicationContext(),NoteDB.class,DB_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }

        return INSTANCE;
    }


}
