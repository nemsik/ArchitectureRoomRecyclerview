package com.example.bartek.roomlivedata.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface NoteModelDao {

    @Query("SELECT * FROM NoteModel")
    LiveData<List<NoteModel>> getAllNotes();

    @Query("SELECT * FROM NoteModel WHERE id = :id")
    LiveData<NoteModel> getNoteModel(int id);

    @Insert(onConflict = REPLACE)
    void insertNote(NoteModel notemodel);

    @Query("DELETE FROM NoteModel WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM NoteModel")
    void deleteAll();
}
