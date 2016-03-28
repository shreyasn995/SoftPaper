/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
package com.example.android.softpaper;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * This is ab abstract class. The File Handler classes are facades.
 * They keep the complexities of handling files hidden from the rest of the app.
 * NoteFileHandler and ListFileHandler are the two classes derived from this class.
 * They provide methods to Save, Delete and retrieve contents from files.
 */
abstract class FileHandler<T,U> extends AppCompatActivity{

    final static String savedNotesTitles = "savedNotes";//Mater File with the titles of all saved Notes.
    final static String savedListsTitles = "savedLists";////Mater File with the titles of all saved Lists.

    FileOutputStream outputStream;
    FileInputStream inputStream;

    File fileToPerformAction;

    Context context;

    abstract boolean deleteDataFile(String fileToDelete);

    abstract boolean saveDataFile(U dataObject);

    abstract T loadContent(String fileToLoad);
}
