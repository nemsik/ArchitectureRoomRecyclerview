package com.example.bartek.roomlivedata.database;

import android.arch.persistence.room.Delete;
import android.os.AsyncTask;

public class DeleteAsyncTask extends AsyncTask<Integer, Void, Void> {
    private NoteModelDao asyncTaskDao;
    private String TAG = "InsertAsync";

    DeleteAsyncTask(NoteModelDao dao){
        asyncTaskDao = dao;
    }

    @Override
    protected Void doInBackground(Integer... integers) {
        asyncTaskDao.deleteById(integers[0]);
        return null;
    }
}
