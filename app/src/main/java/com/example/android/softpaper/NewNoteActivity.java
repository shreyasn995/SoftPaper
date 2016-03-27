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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class NewNoteActivity extends AppCompatActivity {

    EditText title;
    EditText note;
    static final String filename = "savedNotes";
    FileOutputStream outputStream;
    FileInputStream inputStream;
    File notesFile;
    File savedNotes; //List of existing files

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
            String filenameNote = title.getText().toString();
            if (filenameNote.equals("")){
                Toast.makeText(this, "Enter title for the note", Toast.LENGTH_SHORT).show();
                return true;
            }
            notesFile = new File(getFilesDir(), filenameNote);
            String textBuffer = note.getText().toString();
            try {
                outputStream = openFileOutput(filenameNote, Context.MODE_PRIVATE);
                outputStream.write(textBuffer.getBytes());
                outputStream.close();

                //Add saved file's name to list of existing files
                //If file already exists and is being saved after editing,
                // check if file already exists before adding to this list
                inputStream = openFileInput(filename);
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                Boolean checkIfFileExists = Boolean.FALSE;
                while ((line = input.readLine()) != null){
                    if (line.equals(filenameNote))checkIfFileExists = Boolean.TRUE;
                }
                if (!checkIfFileExists){ //Add new file name to existing list of file names
                    outputStream = openFileOutput(filename, Context.MODE_APPEND);
                    filenameNote += "\n";
                    outputStream.write(filenameNote.getBytes());
                    outputStream.close();
                }
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                e.printStackTrace();
            }
            Intent viewNotesIntent = new Intent(this, ViewNotesActivity.class);
            startActivity(viewNotesIntent);
            return true;
        }

        // delete the entered text.
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

    void handleEditNote(Intent intent){
        final String filenameReceived = intent.getStringExtra("filename");
        if (filenameReceived != null) {
            title.setText(filenameReceived);
            try {
                //Read existing note and populate text fields with its contents
                inputStream = openFileInput(filenameReceived);
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder reader = new StringBuilder();
                String line;
                while ((line = input.readLine()) != null){
                    reader.append(line);
                }
                note.setText(reader);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //Delete note file and return to view activity
    void handleDeleteNote(Intent intent){
        final String filenameReceived = intent.getStringExtra("filename");
        if (filenameReceived != null) {
            File fileToDelete = new File(getFilesDir(),filenameReceived);
            boolean deleted = fileToDelete.delete();
            if (deleted){ //Remove file name from the list of saved files
                try{
                    inputStream = openFileInput(filename);
                    BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder reader = new StringBuilder();
                    String line;
                    while ((line = input.readLine()) != null){
                        if (!line.equals(filenameReceived)){
                            reader.append(line);
                            reader.append("\n");
                        }
                    }
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(reader.toString().getBytes());
                    outputStream.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
                Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }
        Intent viewNotesIntent = new Intent(this, ViewNotesActivity.class);
        startActivity(viewNotesIntent);
    }
}
