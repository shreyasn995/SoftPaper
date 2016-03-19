package com.example.android.softpaper;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class NotesContentFragment extends Fragment {

    TextView noteContent;
    FileInputStream inputStream;
    File savedNote;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notes_content, container, false);

        noteContent = (TextView) rootView.findViewById(R.id.text_noteContent);

        Bundle args = getArguments();
        if (args != null) {
            final String filename = args.getString("noteTitle");
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
        return rootView;
    }
}
