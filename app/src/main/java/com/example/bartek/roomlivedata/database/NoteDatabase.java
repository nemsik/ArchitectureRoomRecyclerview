package com.example.bartek.roomlivedata.database;

import android.app.Application;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = NoteModel.class, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase INSTANCE;

    public static NoteDatabase getDatabase(final Application application){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(application.getApplicationContext(), NoteDatabase.class, "notes_db")
                    .fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    public static void destroyInstances(){
        INSTANCE = null;
    }

    public abstract NoteModelDao noteModelDao();
}
