/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
package com.example.android.softpaper;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 *This is a nested fragment within the ViewNotesFragment.
 * It appears to the right of the screen.
 * It displays the contents of the saved notes.
 */

public class NotesContentFragment extends Fragment {

    TextView noteContent;
    String filename;
    NoteFileHandler noteFileHandler;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notes_content, container, false);

        noteContent = (TextView) rootView.findViewById(R.id.text_noteContent);
        noteFileHandler = new NoteFileHandler(container.getContext()); //Used to perform operations of files that save notes.

        setHasOptionsMenu(Boolean.TRUE);

        /**
         * This fragment is created from the parent fragment with arguments passed to it.
         * Get the filename of the note that has to be displayed from the arguments.
         * Display the contents of the note in this fragment.
         */
        Bundle args = getArguments();
        if (args != null) {
            filename = args.getString("noteTitle");
            noteContent.setText(noteFileHandler.loadContent(filename));
        }
        //ActivityCompat.invalidateOptionsMenu(this.getActivity());
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection from AppBar menu
        switch (item.getItemId()) {
            // Both Edit and Delete are handled by the NewNoteActivity (control class)
            case R.id.action_delete_note:
                Intent intentDelete = new Intent(getContext(), NewNoteActivity.class);
                intentDelete.putExtra("filename",filename);
                intentDelete.setAction(intentDelete.ACTION_DELETE);
                startActivity(intentDelete);
                return true;
            case R.id.action_edit_note:
                Intent intentEdit = new Intent(getContext(), NewNoteActivity.class);
                intentEdit.putExtra("filename",filename);
                intentEdit.setAction(intentEdit.ACTION_EDIT);
                startActivity(intentEdit);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
