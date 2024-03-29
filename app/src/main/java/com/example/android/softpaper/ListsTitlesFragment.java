/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
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

import java.util.ArrayList;

/**
 * This is a child fragment of the ViewListsFragment.
 * This fragment appears on the left of the screen.
 * This fragment shows all the saved titles of lists. The titles are clickable.
 * When the user clicks on a title, the relevant list content is shown on the right of the screen.
 */
public class ListsTitlesFragment extends Fragment {

    LinearLayout linear;
    ListFileHandler listFileHandler;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lists_titles, container, false);

        listFileHandler = new ListFileHandler(container.getContext()); //Used to perform operations on files that save lists.
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

        ArrayList<String> noteTitles = listFileHandler.getListTitles();
        // Display the message below if there are no saved notes yet.
        if (noteTitles.size() == 0){
            TextView textView = new TextView(getContext());
            textView.setLayoutParams(lparams);
            String message = "Nothing to show here.";
            textView.setText(message);
            textView.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
            linear.addView(textView);
            return;
        }
        for (int i=noteTitles.size()-1; i >= 0; i--) {
            final TextView textView = new TextView(getContext());
            textView.setLayoutParams(lparams);
            textView.setText(noteTitles.get(i));
            textView.setClickable(true);
            // Send message to parent fragment that the user wishes to see the contents of the note title clicked on.
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
    }
}
