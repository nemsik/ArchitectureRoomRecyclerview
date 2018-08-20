package com.example.bartek.roomlivedata;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.bartek.roomlivedata.ViewModel.NoteViewModel;
import com.example.bartek.roomlivedata.database.NoteDatabase;
import com.example.bartek.roomlivedata.database.NoteModel;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private final LayoutInflater layoutInflater;
    private List<NoteModel> noteModelList;
    private NoteViewModel viewModel;
    private Context context;

    private final String TAG = "ListAdapter";

    NoteListAdapter(Context context, NoteViewModel viewModel) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if (noteModelList != null) {
            NoteModel current = noteModelList.get(position);
            holder.noteItemView.setText(current.getNoteTitle());
            holder.noteItemViewDesc.setText(current.getNoteDescription());
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    buildAlertDialogDeleteAll(position);
                    return false;
                }
            });

        } else {
            holder.noteItemView.setText("No note");
        }
    }

    void setNotes(List<NoteModel> notes) {
        noteModelList = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (noteModelList != null) return noteModelList.size();
        else return 0;
    }


    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteItemView;
        private final TextView noteItemViewDesc;
        private final CardView cardView;

        private NoteViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.textView);
            noteItemViewDesc = itemView.findViewById(R.id.textViewDesc);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    private void buildAlertDialogDeleteAll(int position){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.delete(noteModelList.get(position).getId());
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
