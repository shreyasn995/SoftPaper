package com.example.android.softpaper;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class NotesTitlesFragment extends Fragment {

    static final String filename = "savedNotes";
    FileInputStream inputStream;
    File savedNotes;
    LinearLayout linear;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_notes_titles, container, false);

        linear = (LinearLayout) rootView.findViewById(R.id.fragmentNoteTitle);
        populateTitles();

        return rootView;
    }

    void populateTitles(){
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        savedNotes = new File(getContext().getFilesDir(), filename);
        if (savedNotes.exists()) {
            try {
                inputStream = getContext().openFileInput(filename);
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = input.readLine()) != null){
                    final TextView textView = new TextView(getContext());
                    textView.setLayoutParams(lparams);
                    textView.setText(line);
                    textView.setClickable(true);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view){
                            Intent intent = new Intent();
                            intent.putExtra("message", textView.getText().toString());
                            getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                            getFragmentManager().popBackStack();
                        }
                    });
                    textView.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
                    linear.addView(textView);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(lparams);
            String message = "Nothing to show here.";
            textView.setText(message);
            textView.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
            linear.addView(textView);
        }
    }
}
