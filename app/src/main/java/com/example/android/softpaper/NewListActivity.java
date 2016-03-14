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

import java.io.File;
import java.io.FileOutputStream;

public class NewListActivity extends AppCompatActivity {

    EditText title;
    EditText list1;
    CheckBox box1;
    FloatingActionButton fab;
    LinearLayout linear;

    final TextView[] listTextView = new TextView[25];
    final CheckBox[] listCheckBox = new CheckBox[25];
    int noOfTextBox = 0;

    static final String filename = "listsFile";
    FileOutputStream outputStream;
    File listsFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        title = (EditText) findViewById(R.id.edit_listTitle);
        list1 = (EditText) findViewById(R.id.edit_List1);
        box1 = (CheckBox) findViewById(R.id.check_List1);
        fab = (FloatingActionButton) findViewById(R.id.listFab);

        linear = (LinearLayout)findViewById(R.id.addToList);
        listsFile = new File(getFilesDir(), filename);

        listTextView[0] = list1;
        listCheckBox[0] = box1;
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
            String textBuffer = "`";
            textBuffer += "`";
            textBuffer += title.getText().toString();
            textBuffer += "\n";

            for (int i=0; i <= noOfTextBox; i++){
                textBuffer += listTextView[i].getText().toString();
                textBuffer += "\n";
                if (listCheckBox[i].isChecked()) textBuffer += "1"; else textBuffer += "0";
                textBuffer += "\n";
            }

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

    public void addToList(View view){
        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
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
}
