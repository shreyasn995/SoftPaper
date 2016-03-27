package com.example.android.softpaper;

import android.content.Context;
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class NewListActivity extends AppCompatActivity {

    EditText title;
    EditText list1;
    CheckBox box1;
    FloatingActionButton fab;
    LinearLayout linear;

    final TextView[] listTextView = new TextView[25];
    final CheckBox[] listCheckBox = new CheckBox[25];
    int noOfTextBox = 0;

    static final String filename = "savedLists";
    FileOutputStream outputStream;
    FileInputStream inputStream;
    File listsFile;
    File savedLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        title = (EditText) findViewById(R.id.edit_listTitle);
        list1 = (EditText) findViewById(R.id.edit_List1);
        box1 = (CheckBox) findViewById(R.id.check_List1);
        fab = (FloatingActionButton) findViewById(R.id.listFab);

        linear = (LinearLayout)findViewById(R.id.addToList);
        savedLists = new File(getFilesDir(), filename);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about) {
            Intent launchAboutActivityIntent = new Intent(this, AboutActivity.class);
            startActivity(launchAboutActivityIntent);
            return true;
        }

        if (id == R.id.action_save) {
            String filenameList = title.getText().toString();
            if (filenameList.equals("")){
                Toast.makeText(this, "Enter title for the list", Toast.LENGTH_SHORT).show();
                return true;
            }
            listsFile = new File(getFilesDir(), filenameList);
            String textBuffer = "";

            for (int i=0; i <= noOfTextBox; i++){
                textBuffer += listTextView[i].getText().toString();
                textBuffer += "\n";
                if (listCheckBox[i].isChecked()) textBuffer += "1"; else textBuffer += "0";
                textBuffer += "\n";
            }

            try {
                outputStream = openFileOutput(filenameList, Context.MODE_PRIVATE);
                outputStream.write(textBuffer.getBytes());
                outputStream.close();
                outputStream = openFileOutput(filename, Context.MODE_APPEND);
                filenameList += "\n";
                outputStream.write(filenameList.getBytes());
                outputStream.close();
                Toast.makeText(this, "List saved", Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                e.printStackTrace();
            }
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

    void handleEditList(Intent intent) {
        final String filenameReceived = intent.getStringExtra("filename");
        if (filenameReceived != null) {
            title.setText(filenameReceived);
            try {
                //Read existing note and populate text fields with its contents
                inputStream = openFileInput(filenameReceived);
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder reader = new StringBuilder();
                String line;
                while ((line = input.readLine()) != null){
                    reader.append(line);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    void handleDeleteList(Intent intent) {
        final String filenameReceived = intent.getStringExtra("filename");
        if (filenameReceived != null) {
            File fileToDelete = new File(getFilesDir(),filenameReceived);
            boolean deleted = fileToDelete.delete();
            if (deleted){ //Remove file name from the list of saved files
                try{
                    inputStream = openFileInput(filename);
                    BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder reader = new StringBuilder();
                    String line;
                    while ((line = input.readLine()) != null){
                        if (!line.equals(filenameReceived)){
                            reader.append(line);
                            reader.append("\n");
                        }
                    }
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    outputStream.write(reader.toString().getBytes());
                    outputStream.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
                Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }
        Intent viewNotesIntent = new Intent(this, ViewNotesActivity.class);
        startActivity(viewNotesIntent);
    }
}
