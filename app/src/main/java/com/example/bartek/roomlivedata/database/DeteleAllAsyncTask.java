package com.example.bartek.roomlivedata.database;

import android.os.AsyncTask;

class DeteleAllAsyncTask extends AsyncTask<Void, Void, Void> {
    private NoteModelDao dao;

    public DeteleAllAsyncTask(NoteModelDao dao) {
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.deleteAll();
        return null;
    }
}
