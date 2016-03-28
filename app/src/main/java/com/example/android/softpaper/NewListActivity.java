/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
package com.example.android.softpaper;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the control class that deals with Lists.
 */

public class NewListActivity extends AppCompatActivity {

    EditText title;
    EditText list1;
    CheckBox box1;
    FloatingActionButton fab;
    LinearLayout linear;

    ListFileHandler listFileHandler = new ListFileHandler(this); //Used to perform operations of files that save notes.

    //Number of items a user can add is limited to 25.
    final TextView[] listTextView = new TextView[25];
    final CheckBox[] listCheckBox = new CheckBox[25];
    int noOfTextBox = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        title = (EditText) findViewById(R.id.edit_listTitle);
        list1 = (EditText) findViewById(R.id.edit_List1);
        box1 = (CheckBox) findViewById(R.id.check_List1);
        fab = (FloatingActionButton) findViewById(R.id.listFab);

        linear = (LinearLayout)findViewById(R.id.addToList);

        listTextView[noOfTextBox] = list1;
        listCheckBox[noOfTextBox] = box1;

        //Recieve intent from parent fragment to display list content
        Intent intent = getIntent();
        String action = intent.getAction();
        //If edit called from NotesContentFragment
        if (Intent.ACTION_EDIT.equals(action)) {
            handleEditList(intent);
        }
        //If delete called from NotesContentFragment
        else if (Intent.ACTION_DELETE.equals(action)) {
            handleDeleteList(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_note_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            Intent launchAboutActivityIntent = new Intent(this, AboutActivity.class);
            startActivity(launchAboutActivityIntent);
            return true;
        }

        if (id == R.id.action_save) {
            if (title.getText().toString().equals("")){
                Toast.makeText(this, "Enter title for the list", Toast.LENGTH_SHORT).show();
                return true;
            }
            ListDataObject listDataObject = new ListDataObject(title.getText().toString(), listTextView, listCheckBox, noOfTextBox);
            Boolean noteSaved = listFileHandler.saveDataFile(listDataObject);
            if (noteSaved) Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            else Toast.makeText(this, "Error. Note not saved", Toast.LENGTH_SHORT).show();
            Intent viewNotesIntent = new Intent(this, ViewNotesActivity.class);
            startActivity(viewNotesIntent);
            return true;
        }

        if (id == R.id.action_delete) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //The floating action button dynamically adds more EditTexts and Buttons.
    public void addToList(View view){
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (noOfTextBox == 24) return;
        noOfTextBox++;

        CheckBox checkBox = new CheckBox(this);
        checkBox.setText("");

        EditText editText = new EditText(this);
        editText.setLayoutParams(lparams);
        editText.setHint("Touch here to type");

        listCheckBox[noOfTextBox] = checkBox;
        listTextView[noOfTextBox] = editText;

        linear.addView(checkBox);
        linear.addView(editText);
    }

    //Populate text fields and buttons with previously saved contents
    void handleEditList(Intent intent) {
        final String filenameReceived = intent.getStringExtra("filename") + ".xml";
        if (intent.getStringExtra("filename") != null) {
            title.setText(intent.getStringExtra("filename"));
            String[] listContent = listFileHandler.loadContent(filenameReceived);
            Boolean[] boxIsChecked = listFileHandler.loadIsChecked(filenameReceived);

            if(boxIsChecked[0]) box1.setChecked(Boolean.TRUE);
            list1.setHint("");
            list1.setText(listContent[0]);

            for(int i=1; i<listContent.length; i++){
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText("");
                if(boxIsChecked[i]) checkBox.setChecked(Boolean.TRUE);

                EditText editText = new EditText(this);
                editText.setLayoutParams(lparams);
                editText.setText(listContent[i]);

                listCheckBox[i] = checkBox;
                listTextView[i] = editText;
                linear.addView(checkBox);
                linear.addView(editText);
            }
            noOfTextBox = listContent.length - 1;
        }
    }

    //Delete list file and return to view activity
    void handleDeleteList(Intent intent) {
        final String filenameReceived = intent.getStringExtra("filename") + ".xml";
        Boolean fileDeleted = listFileHandler.deleteDataFile(filenameReceived);
        if (fileDeleted) Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
        else Toast.makeText(this, "Error. Note not deleted", Toast.LENGTH_SHORT).show();
        Intent viewNotesIntent = new Intent(this, ViewNotesActivity.class);
        startActivity(viewNotesIntent);
    }
}
