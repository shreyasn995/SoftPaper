package com.example.android.softpaper;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewNotesFragment extends Fragment{
    Fragment fragToRemove;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_notes, container, false);

        Fragment notesTitlesFragment = new NotesTitlesFragment();
        notesTitlesFragment.setTargetFragment(this, 0);
        android.support.v4.app.FragmentTransaction transaction1 = getChildFragmentManager().beginTransaction();
        transaction1.add(R.id.child_fragment, notesTitlesFragment).commit();

        return rootView;
    }

    @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                String value = data.getStringExtra("message");
                if(value != null) {
                    if (fragToRemove != null){
                        android.support.v4.app.FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                        transaction3.remove(fragToRemove).commit();
                        fragToRemove = null;
                    }
                    Fragment notesContentFragment = new NotesContentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("noteTitle", value);
                    notesContentFragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction transaction2 = getChildFragmentManager().beginTransaction();
                    transaction2.add(R.id.child_fragment2, fragToRemove = notesContentFragment).commit();
                }
            }
        }
    }
}
