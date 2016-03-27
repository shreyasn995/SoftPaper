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
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Shreyas on 27/03/2016.
 */
public class ListsContentFragment extends Fragment {

    TextView listContent;
    FileInputStream inputStream;
    File savedList;
    String filename;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lists_content, container, false);

        listContent = (TextView) rootView.findViewById(R.id.text_listContent);

        setHasOptionsMenu(Boolean.TRUE);

        /**
         * This fragment is created from the parent fragment with arguments passed to it.
         * Get the filename of the list that has to be displayed from the arguments.
         * Display the contents of the list in this fragment.
         */
        Bundle args = getArguments();
        if (args != null) {
            filename = args.getString("listTitle");
            savedList = new File(getContext().getFilesDir(), filename);
            if (savedList.exists()) {
                try {
                    inputStream = getContext().openFileInput(filename);
                    BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder reader = new StringBuilder();
                    String line;
                    while ((line = input.readLine()) != null){
                        reader.append(line);
                    }
                    listContent.setText(reader);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //ActivityCompat.invalidateOptionsMenu(this.getActivity());
        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            // Both Edit and Delete are handled by the NewListActivity (control class)
            case R.id.action_delete_list:
                Intent intentDelete = new Intent(getContext(), NewListActivity.class);
                intentDelete.putExtra("filename",filename);
                intentDelete.setAction(intentDelete.ACTION_DELETE);
                startActivity(intentDelete);
                return true;
            case R.id.action_edit_list:
                Intent intentEdit = new Intent(getContext(), NewListActivity.class);
                intentEdit.putExtra("filename",filename);
                intentEdit.setAction(intentEdit.ACTION_EDIT);
                startActivity(intentEdit);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
