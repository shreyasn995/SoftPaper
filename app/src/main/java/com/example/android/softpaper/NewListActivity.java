package com.example.android.softpaper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class NewListActivity extends AppCompatActivity {

    EditText title;
    EditText list1;
    EditText list2;
    EditText list3;
    EditText list4;
    EditText list5;
    EditText list6;
    EditText list7;
    EditText list8;
    EditText list9;
    EditText list10;
    EditText list11;
    EditText list12;
    EditText list13;

    CheckBox box1;
    CheckBox box2;
    CheckBox box3;
    CheckBox box4;
    CheckBox box5;
    CheckBox box6;
    CheckBox box7;
    CheckBox box8;
    CheckBox box9;
    CheckBox box10;
    CheckBox box11;
    CheckBox box12;
    CheckBox box13;

    static final String filename = "notesFile";
    FileOutputStream outputStream;
    File listsFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        title = (EditText) findViewById(R.id.edit_newListTitle);
        list1 = (EditText) findViewById(R.id.edit_List1);
        list2 = (EditText) findViewById(R.id.edit_List2);
        list3 = (EditText) findViewById(R.id.edit_List3);
        list4 = (EditText) findViewById(R.id.edit_List4);
        list5 = (EditText) findViewById(R.id.edit_List5);
        list6 = (EditText) findViewById(R.id.edit_List6);
        list7 = (EditText) findViewById(R.id.edit_List7);
        list8 = (EditText) findViewById(R.id.edit_List8);
        list9 = (EditText) findViewById(R.id.edit_List9);
        list10 = (EditText) findViewById(R.id.edit_List10);
        list11 = (EditText) findViewById(R.id.edit_List11);
        list12 = (EditText) findViewById(R.id.edit_List12);
        list13 = (EditText) findViewById(R.id.edit_List13);

        box1 = (CheckBox) findViewById(R.id.check_List1);
        box2 = (CheckBox) findViewById(R.id.check_List2);
        box3 = (CheckBox) findViewById(R.id.check_List3);
        box4 = (CheckBox) findViewById(R.id.check_List4);
        box5 = (CheckBox) findViewById(R.id.check_List5);
        box6 = (CheckBox) findViewById(R.id.check_List6);
        box7 = (CheckBox) findViewById(R.id.check_List7);
        box8 = (CheckBox) findViewById(R.id.check_List8);
        box9 = (CheckBox) findViewById(R.id.check_List9);
        box10 = (CheckBox) findViewById(R.id.check_List10);
        box11 = (CheckBox) findViewById(R.id.check_List11);
        box12 = (CheckBox) findViewById(R.id.check_List12);
        box13 = (CheckBox) findViewById(R.id.check_List13);

        listsFile = new File(getFilesDir(), filename);
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
            String textBuffer = title.getText().toString();
            textBuffer += "\n";

            textBuffer += list1.getText().toString();
            textBuffer += "\n";
            if (box1.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list2.getText().toString();
            textBuffer += "\n";
            if (box2.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list3.getText().toString();
            textBuffer += "\n";
            if (box3.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list4.getText().toString();
            textBuffer += "\n";
            if (box4.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list5.getText().toString();
            textBuffer += "\n";
            if (box5.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list6.getText().toString();
            textBuffer += "\n";
            if (box1.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list6.getText().toString();
            textBuffer += "\n";
            if (box1.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list7.getText().toString();
            textBuffer += "\n";
            if (box7.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list8.getText().toString();
            textBuffer += "\n";
            if (box8.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list9.getText().toString();
            textBuffer += "\n";
            if (box9.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list10.getText().toString();
            textBuffer += "\n";
            if (box10.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list11.getText().toString();
            textBuffer += "\n";
            if (box11.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list12.getText().toString();
            textBuffer += "\n";
            if (box12.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";
            textBuffer += list13.getText().toString();
            textBuffer += "\n";
            if (box13.isChecked()) textBuffer += "0"; else textBuffer += "1";
            textBuffer += "\n";

            try {
                outputStream = openFileOutput(filename, Context.MODE_APPEND);
                outputStream.write(textBuffer.getBytes());
                outputStream.close();
                Toast.makeText(this, "List saved", Toast.LENGTH_SHORT).show();
            }catch(Exception e){
                e.printStackTrace();
            }
            finish();

            return true;
        }

        if (id == R.id.action_delete) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
