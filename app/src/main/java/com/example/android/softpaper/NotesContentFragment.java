package com.example.android.softpaper;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class NotesContentFragment extends Fragment implements View.OnClickListener {

    TextView noteContent;
    FileInputStream inputStream;
    File savedNote;
    String filename;
    Button editButton;
    Button deleteButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notes_content, container, false);

        noteContent = (TextView) rootView.findViewById(R.id.text_noteContent);

        Bundle args = getArguments();
        if (args != null) {
            filename = args.getString("noteTitle");
            savedNote = new File(getContext().getFilesDir(), filename);
            if (savedNote.exists()) {
                try {
                    inputStream = getContext().openFileInput(filename);
                    BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder reader = new StringBuilder();
                    String line;
                    while ((line = input.readLine()) != null){
                        reader.append(line);
                    }
                    noteContent.setText(reader);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        editButton = (Button) inflater.inflate(R.layout.fragment_notes_content, container, false).findViewById(R.id.button_noteContentEdit);
        editButton.setOnClickListener(this);
        deleteButton = (Button) inflater.inflate(R.layout.fragment_notes_content, container, false).findViewById(R.id.button_noteContentDelete);
        deleteButton.setOnClickListener(this);

        return rootView;
    }


    //Handle the Edit and Delete button clicks
    @Override
    public void onClick(View v){
        if (v.getId() == R.id.button_noteContentEdit){
            editNote(v);
            //Intent intent = new Intent(getContext(), NewNoteActivity.class);
            //intent.putExtra("filename",filename);
            //intent.setAction(intent.ACTION_EDIT);
            //startActivity(intent);
        }
        else if (v.getId() == R.id.button_noteContentDelete) {
            Intent intent = new Intent(getContext(), NewNoteActivity.class);
            intent.putExtra("filename",filename);
            intent.setAction(intent.ACTION_DELETE);
            startActivity(intent);
        }
    }

    public void editNote (View v){
        Intent intent = new Intent(getContext(), NewNoteActivity.class);
        intent.putExtra("filename",filename);
        intent.setAction(intent.ACTION_EDIT);
        startActivity(intent);
    }
}
