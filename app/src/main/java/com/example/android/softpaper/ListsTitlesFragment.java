package com.example.android.softpaper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Shreyas on 27/03/2016.
 */
public class ListsTitlesFragment extends Fragment {

    static final String filename = "savedLists";
    FileInputStream inputStream;
    File savedLists;
    LinearLayout linear;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lists_titles, container, false);

        linear = (LinearLayout) rootView.findViewById(R.id.fragmentListTitle);
        populateTitles();

        return rootView;
    }

    /**
     * Dynamically add titles to the linear layout within this fragment.
     */
    void populateTitles(){
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        savedLists = new File(getContext().getFilesDir(), filename);
        if (savedLists.exists()) {
            try {
                inputStream = getContext().openFileInput(filename);
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = input.readLine()) != null){
                    final TextView textView = new TextView(getContext());
                    textView.setLayoutParams(lparams);
                    textView.setText(line);
                    textView.setClickable(true);
                    // Send message to parent fragment that the user wishes to see the contents of the list title clicked on.
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
        // Display the message below if there are no saved lists yet.
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
