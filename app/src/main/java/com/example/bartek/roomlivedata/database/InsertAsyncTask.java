package com.example.bartek.roomlivedata.database;

import android.os.AsyncTask;
import android.util.Log;

public class InsertAsyncTask extends AsyncTask<NoteModel, Void, Void> {

    private NoteModelDao asyncTaskDao;
    private String TAG = "InsertAsync";

    InsertAsyncTask(NoteModelDao dao){
        asyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(final NoteModel... params) {
        NoteModel noteModel = params[0];
        Log.d(TAG, "doInBackground: " + noteModel.getNoteTitle() + noteModel.getNoteDescription());
        asyncTaskDao.insertNote(params[0]);
        return null;
    }
}
