package com.example.bartek.roomlivedata.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteModelDao dao;
    private LiveData<List<NoteModel>> allNotes;



    public NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        dao = db.noteModelDao();
        allNotes = dao.getAllNotes();
    }

    public LiveData<List<NoteModel>> getAllNotes(){
        return allNotes;
    }

    public void insert (NoteModel noteModel){
        new InsertAsyncTask(dao).execute(noteModel);
    }
    public void delete (int id){new DeleteAsyncTask(dao).execute(id);}
    public void deleteAll() {new DeteleAllAsyncTask(dao).execute();}

}
