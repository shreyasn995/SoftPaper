/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
package com.example.android.softpaper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import android.view.View;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

/**
 * This is the first screen a user sees. It prompts the user to  set a password the first time.
 * The user will enter the password to access the app.
 */

public class LoginActivity extends AppCompatActivity {

    TextView welcomeText;
    EditText password;
    TextView loginError;

    String presetPassphrase = null;

    /* File for storing the login passphrase */
    static final String filename_password = "loginPassword";
    FileInputStream inputStream;
    FileOutputStream outputStream;
    File passwordFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        welcomeText = (TextView) findViewById(R.id.text_welcomeText);
        password = (EditText) findViewById(R.id.edit_password);
        loginError = (TextView) findViewById(R.id.text_loginError);

        /* Get time from Android to display correct welcome text */
        String textBuffer = welcomeText.getText().toString();
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY); //Get current time in hours
        if (hour < 12) textBuffer += " Morning!"; //It is before 12 noon
        else if (hour < 18) textBuffer += " Afternoon!"; //It id before 6 p.m.
        else textBuffer += " Evening!"; //It is After 6 p.m.
        welcomeText.setText(textBuffer); //Set welcome text based on current time

        /* Open file and read preset password */
        passwordFile = new File(getFilesDir(), filename_password);
        if (passwordFile.exists()) {
            try {
                inputStream = openFileInput(filename_password);
                BufferedReader fetchPassword = new BufferedReader(new InputStreamReader(inputStream));
                presetPassphrase = fetchPassword.readLine(); //Store the preset password in the PresetPassword string.
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /* Compare retrieved password to entered password */
    public void comparePassword(View view){
        String enteredPassword = password.getText().toString();
        /* If password never set before, write new password to file and launch the ViewNotesActivity Activity */
        if (presetPassphrase == null){
            try{
                outputStream = openFileOutput(filename_password, Context.MODE_PRIVATE);
                outputStream.write(enteredPassword.getBytes());
                outputStream.close();
                /* Launch ViewNotesActivity Activity */
                Intent launchViewNotesIntent = new Intent(this, ViewNotesActivity.class);
                startActivity(launchViewNotesIntent);
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        else{
            if (enteredPassword.equals(presetPassphrase)){
                /* Launch ViewNotesActivity Activity */
                Intent launchViewNotesIntent = new Intent(this, ViewNotesActivity.class);
                startActivity(launchViewNotesIntent);
            }
            /* else display error message */
            else{
                loginError.setText("Wrong passphrase entered. Try again.");
            }
        }
    }
}
