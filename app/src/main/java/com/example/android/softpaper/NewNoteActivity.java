package com.example.android.softpaper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class NewNoteActivity extends AppCompatActivity {

    EditText title;
    EditText note;
    static final String filename = "savedNotes";
    FileOutputStream outputStream;
    File notesFile;
    File savedNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        title = (EditText) findViewById(R.id.edit_newListTitle);
        note = (EditText) findViewById(R.id.edit_newNoteNote);

        savedNotes = new File(getFilesDir(), filename);

        /**
         * Get intent, action and MIME type.
         * Code to get text from other apps sourced from:
         * http://developer.android.com/training/sharing/receive.html
         */
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_view_notes, menu);
        getMenuInflater().inflate(R.menu.menu_new_note_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent launchAboutActivityIntent = new Intent(this, AboutActivity.class);
            startActivity(launchAboutActivityIntent);
            return true;
        }

        if (id == R.id.action_save) {
            String filenameNote = title.getText().toString();
            if (filenameNote.equals("")){
                Toast.makeText(this, "Enter title for the note", Toast.LENGTH_SHORT).show();
                Log.i("SaveNote", "ToastDisplayed");
                return true;
            }
            notesFile = new File(getFilesDir(), filenameNote);
            String textBuffer = note.getText().toString();
            try {
                outputStream = openFileOutput(filenameNote, Context.MODE_PRIVATE);
                outputStream.write(textBuffer.getBytes());
                outputStream.close();
                outputStream = openFileOutput(filename, Context.MODE_APPEND);
                filenameNote += "\n";
                outputStream.write(filenameNote.getBytes());
                outputStream.close();
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                e.printStackTrace();
            }
            Intent viewNotesIntent = new Intent(this, ViewNotesActivity.class);
            startActivity(viewNotesIntent);
            return true;
        }

        if (id == R.id.action_delete) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            note.setText(sharedText);
        }
    }
}
