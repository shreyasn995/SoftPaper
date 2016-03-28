package com.example.android.softpaper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Shreyas on 28/03/2016.
 */
abstract class FileHandler<T,U> extends AppCompatActivity{

    final static String savedNotesTitles = "savedNotes";
    final static String savedListsTitles = "savedLists";

    FileOutputStream outputStream;
    FileInputStream inputStream;

    File fileToPerformAction;

    Context context;

    abstract boolean deleteDataFile(String fileToDelete);

    abstract boolean saveDataFile(U dataObject);

    abstract T loadContent(String fileToLoad);
}
