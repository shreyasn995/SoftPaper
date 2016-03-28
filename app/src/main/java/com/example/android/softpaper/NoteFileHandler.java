/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
package com.example.android.softpaper;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * This class provides methods for all required file operations on Notes.
 * Notes are stored as file documents.
 * Each note is stored as separate document.
 * The document name will be the title of the note.
 */
public class NoteFileHandler extends FileHandler<String,NoteDataObject> {

    //Delete a given List file
    boolean deleteDataFile(String fileToDelete){
        if (fileToDelete == null) return Boolean.FALSE;
        fileToPerformAction = new File(context.getFilesDir(),fileToDelete);
        if (!fileToPerformAction.exists())return Boolean.FALSE;
        boolean deleted = fileToPerformAction.delete();
        if (deleted) { //Remove file name from the list of saved files
            try {
                inputStream = context.openFileInput(savedNotesTitles);
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder reader = new StringBuilder();
                String line;
                while ((line = input.readLine()) != null) {
                    if (!line.equals(fileToDelete)) {
                        reader.append(line);
                        reader.append("\n");
                    }
                }
                outputStream = context.openFileOutput(savedNotesTitles, Context.MODE_PRIVATE);
                outputStream.write(reader.toString().getBytes());
                outputStream.close();
                return deleted;
            } catch (Exception e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }
        }
        else return deleted;
    }

    //Save object of type Note to a file.
    boolean saveDataFile(NoteDataObject noteDataObject){
        if (noteDataObject == null) return Boolean.FALSE;
        String writeBuffer = noteDataObject.getContent();
        String filenameNote = noteDataObject.getTitle();
        try {
            outputStream = context.openFileOutput(filenameNote, Context.MODE_PRIVATE);
            outputStream.write(writeBuffer.getBytes());
            outputStream.close();

            //Add saved file's name to list of existing files
            //If file already exists and is being saved after editing,
            // check if file already exists before adding to this list
            fileToPerformAction = new File(context.getFilesDir(),savedNotesTitles);
            Boolean checkIfFileExists = Boolean.FALSE;
            if (fileToPerformAction.exists()){
                inputStream = context.openFileInput(savedNotesTitles);
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = input.readLine()) != null){
                    if (line.equals(filenameNote))checkIfFileExists = Boolean.TRUE;
                }
            }
            if (!checkIfFileExists){ //Add new file name to existing list of file names
                outputStream = context.openFileOutput(savedNotesTitles, Context.MODE_APPEND);
                filenameNote += "\n";
                outputStream.write(filenameNote.getBytes());
                outputStream.close();
            }
            return Boolean.TRUE;
        }catch(Exception e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    //Return content of note previously saved.
    String loadContent(String fileToLoad){
        try {
            //Read existing note and populate text fields with its contents
            inputStream = context.openFileInput(fileToLoad);
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder reader = new StringBuilder();
            String line;
            while ((line = input.readLine()) != null){
                reader.append(line);
            }
            return reader.toString();
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //Returns the list of all the saved titles of the notes.
    ArrayList<String> getNoteTitles(){
        ArrayList<String> noteTitles = new ArrayList<>();
        try {
            inputStream = context.openFileInput(savedNotesTitles);
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = input.readLine()) != null){
                noteTitles.add(line);
            }
        }catch(Exception e){
            e.printStackTrace();
            return noteTitles;
        }
        return noteTitles;
    }

    //Constructor.
    public NoteFileHandler(Context context){
        this.context = context;
    }
}
