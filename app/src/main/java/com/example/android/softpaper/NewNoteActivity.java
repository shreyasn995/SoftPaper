/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
package com.example.android.softpaper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is the control class that deals with Notes.
 */

public class NewNoteActivity extends AppCompatActivity {

    EditText title;
    EditText note;
    NoteFileHandler noteFileHandler = new NoteFileHandler(this); //Used to perform operations of files that save notes.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        title = (EditText) findViewById(R.id.edit_newListTitle);
        note = (EditText) findViewById(R.id.edit_newNoteNote);

        /**
         * Get intent, action and MIME type.
         * Code to get text from other apps sourced from:
         * http://developer.android.com/training/sharing/receive.html
         */
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        //If receiving data from other apps
        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            }
        }
        //If edit called from NotesContentFragment
        else if (Intent.ACTION_EDIT.equals(action)) {
            handleEditNote(intent);
        }
        //If delete called from NotesContentFragment
        else if (Intent.ACTION_DELETE.equals(action)) {
            handleDeleteNote(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_note_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //launch About activity
        if (id == R.id.action_about) {
            Intent launchAboutActivityIntent = new Intent(this, AboutActivity.class);
            startActivity(launchAboutActivityIntent);
            return true;
        }

        //save the note
        if (id == R.id.action_save) {
            if (title.getText().toString().equals("")){
                Toast.makeText(this, "Enter title for the note", Toast.LENGTH_SHORT).show();
                return true;
            }
            NoteDataObject noteDataObject = new NoteDataObject(title.getText().toString(), note);
            Boolean noteSaved = noteFileHandler.saveDataFile(noteDataObject);
            if (noteSaved) Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Error. Note not saved", Toast.LENGTH_SHORT).show();
            Intent viewNotesIntent = new Intent(this, ViewNotesActivity.class);
            startActivity(viewNotesIntent);
            return true;
        }

        // delete the entered text.
        if (id == R.id.action_delete) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item); // If none of the above. Default case.
    }

    //Handle text shared from outside sources such as Wikipedia or Facebook.
    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            note.setText(sharedText);
        }
    }

    //Populate text fields with previously saved contents
    void handleEditNote(Intent intent){
        final String filenameReceived = intent.getStringExtra("filename");
        if (filenameReceived != null) {
            title.setText(filenameReceived);
            note.setText(noteFileHandler.loadContent(filenameReceived));
        }
    }

    //Delete note file and return to view activity
    void handleDeleteNote(Intent intent){
        final String filenameReceived = intent.getStringExtra("filename");
        Boolean fileDeleted = noteFileHandler.deleteDataFile(filenameReceived);
        if (fileDeleted) Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Error. Note not deleted", Toast.LENGTH_SHORT).show();
        Intent viewNotesIntent = new Intent(this, ViewNotesActivity.class);
        startActivity(viewNotesIntent);
    }
}
