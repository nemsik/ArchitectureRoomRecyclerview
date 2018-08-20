package com.example.bartek.roomlivedata;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewNoteActivity extends AppCompatActivity {

    @BindView(R.id.buttonSave)
    Button buttonSave;

    @BindView(R.id.editTextTitle)
    EditText editTextTitle;

    @BindView(R.id.editTextDescription)
    EditText editTextDescription;

    private String TAG = "NewNoteActivity";
    public static final String EXTRA_REPLY_TITLE = "REPLY_TITLE";
    public static final String EXTRA_REPLY_DESCRIPTION = "REPLY_DESCRIPTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.buttonSave)
    public void save(View view){
        Intent intentReply = new Intent();
        if(!TextUtils.isEmpty(editTextTitle.getText()) && !TextUtils.isEmpty(editTextDescription.getText())){
            intentReply.putExtra(EXTRA_REPLY_TITLE, editTextTitle.getText().toString());
            intentReply.putExtra(EXTRA_REPLY_DESCRIPTION, editTextDescription.getText().toString());
            setResult(RESULT_OK, intentReply);
            Log.d(TAG, "send: " + editTextTitle.getText() + " " + editTextDescription.getText());
            finish();
        }else {
            Log.d(TAG, "error");
            Snackbar.make(view, "You must put title and description", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
        }
    }
}
