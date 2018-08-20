package com.example.bartek.roomlivedata.ViewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.bartek.roomlivedata.database.NoteModel;
import com.example.bartek.roomlivedata.database.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<NoteModel>> allNote;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteRepository = new NoteRepository(application);
        allNote = noteRepository.getAllNotes();
    }

    public LiveData<List<NoteModel>> getAllNotes() {return allNote;}

    public void insert(NoteModel noteModel){noteRepository.insert(noteModel);}

    public void delete(int id){noteRepository.delete(id);}

    public void deleteAll(){noteRepository.deleteAll();}


}
