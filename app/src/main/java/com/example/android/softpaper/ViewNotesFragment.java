/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
package com.example.android.softpaper;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ViewNotesFragment is a fragment within ViewNotesActivity. It houses other fragments that displays saved notes.
 * This fragment has no UI.
 * It houses two child fragments: NotesTitlesFragment (to display the titles of saved notes) and NotesContentFragment (to display notes content).
 * NotesContentFragment is updated dynamically depending on the title the user selects on the NotesTitleFragment.
 */

public class ViewNotesFragment extends Fragment{
    Boolean notesContentFragmentActive = Boolean.FALSE;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_notes, container, false);

        //Set the NotesTitleFragment to show all saved notes
        Fragment notesTitlesFragment = new NotesTitlesFragment();
        notesTitlesFragment.setTargetFragment(this, 0);
        android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.child_fragment, notesTitlesFragment);
        transaction.commit();

        return rootView;
    }

    /**
     *When a note title is selected in the NotesTitleFragment, this fragment sends a message to its parent fragment (this one).
     *Intercept the message and set the NotesContentFragment to show the content of the selected note.
     */

    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                String value = data.getStringExtra("message");
                if(value != null) {
                    Fragment notesContentFragment = new NotesContentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("noteTitle", value);
                    notesContentFragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    if (notesContentFragmentActive)transaction.replace(R.id.child_fragment2, notesContentFragment);
                    else {
                        transaction.add(R.id.child_fragment2, notesContentFragment);
                        notesContentFragmentActive = Boolean.TRUE;
                    }
                    transaction.commit();
                }
            }
        }
    }
}
