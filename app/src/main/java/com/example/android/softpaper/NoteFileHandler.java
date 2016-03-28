package com.example.android.softpaper;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by Shreyas on 28/03/2016.
 */
public class NoteFileHandler extends FileHandler<String,NoteDataObject> {

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
            inputStream = context.openFileInput(savedNotesTitles);
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            Boolean checkIfFileExists = Boolean.FALSE;
            while ((line = input.readLine()) != null){
                if (line.equals(savedListsTitles))checkIfFileExists = Boolean.TRUE;
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
            return null;
        }
        return noteTitles;
    }

    public NoteFileHandler(Context context){
        this.context = context;
    }

}
