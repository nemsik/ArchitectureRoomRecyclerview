package com.example.bartek.roomlivedata;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bartek.roomlivedata.ViewModel.NoteViewModel;
import com.example.bartek.roomlivedata.database.NoteModel;
import com.example.bartek.roomlivedata.database.NoteRepository;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private NoteViewModel viewModel;
    private Intent newNoteActivityIntent;
    private String TAG = "noteActivity";
    private Context context;
    private RandomNotes randomNotes;
    private NoteListAdapter adapter;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        context = getApplicationContext();

        randomNotes = new RandomNotes(context);

        viewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new NoteListAdapter(this, viewModel);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getAllNotes().observe(this, new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(@Nullable List<NoteModel> noteModels) {
                adapter.setNotes(noteModels);
                recyclerView.scrollToPosition(adapter.getItemCount()-1);
            }
        });
    }

    @OnClick(R.id.fab)
    public void runNewNoteActivity(View view) {
        newNoteActivityIntent = new Intent(MainActivity.this, NewNoteActivity.class);
        startActivityForResult(newNoteActivityIntent, 200);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.button_delete_all){
            buildAlertDialogDeleteAll();
        }else if(itemId == R.id.add_random){
            viewModel.insert(randomNotes.generate());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 200 && resultCode == RESULT_OK) {
            NoteModel noteModel =
                    new NoteModel(data.getStringExtra(NewNoteActivity.EXTRA_REPLY_TITLE),
                            data.getStringExtra(NewNoteActivity.EXTRA_REPLY_DESCRIPTION));

            Log.d(TAG, "onActivityResult: " + noteModel.getNoteTitle() + " " + noteModel.getNoteDescription());
            viewModel.insert(noteModel);
        } else {
            Toast.makeText(this, "can't add entry", Toast.LENGTH_LONG).show();
        }
    }

    private void buildAlertDialogDeleteAll(){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Delete all")
                .setMessage("Are you sure you want to delete all entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteAll();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
