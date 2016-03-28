package com.example.android.softpaper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Shreyas on 27/03/2016.
 */
public class ListsContentFragment extends Fragment {

    FileInputStream inputStream;
    File savedList;
    String filename;
    String listTitle;
    ListFileHandler listFileHandler;
    LinearLayout linear;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lists_content, container, false);

        linear = (LinearLayout) rootView.findViewById(R.id.list_content_layout);

        listFileHandler = new ListFileHandler(container.getContext()); //Used to perform operations on files that save lists.

        setHasOptionsMenu(Boolean.TRUE);

        /**
         * This fragment is created from the parent fragment with arguments passed to it.
         * Get the filename of the list that has to be displayed from the arguments.
         * Display the contents of the list in this fragment.
         */
        Bundle args = getArguments();
        if (args != null) {
            listTitle = args.getString("listTitle");
            filename = args.getString("listTitle") + ".xml";
            String[] listContent = listFileHandler.loadContent(filename);
            Boolean[] boxIsChecked = listFileHandler.loadIsChecked(filename);
            for(int i=1; i<listContent.length; i++){
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setText("");
                if(boxIsChecked[i]) checkBox.isChecked();

                EditText editText = new EditText(getContext());
                editText.setLayoutParams(lparams);
                editText.setText(listContent[i]);

                linear.addView(checkBox);
                linear.addView(editText);
            }
        }
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            // Both Edit and Delete are handled by the NewListActivity (control class)
            case R.id.action_delete_list:
                Intent intentDelete = new Intent(getContext(), NewListActivity.class);
                intentDelete.putExtra("filename",listTitle);
                intentDelete.setAction(intentDelete.ACTION_DELETE);
                startActivity(intentDelete);
                return true;
            case R.id.action_edit_list:
                Intent intentEdit = new Intent(getContext(), NewListActivity.class);
                intentEdit.putExtra("filename",listTitle);
                intentEdit.setAction(intentEdit.ACTION_EDIT);
                startActivity(intentEdit);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
