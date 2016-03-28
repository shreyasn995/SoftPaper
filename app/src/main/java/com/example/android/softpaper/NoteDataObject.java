/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
package com.example.android.softpaper;

import android.widget.TextView;

/**
 * This the Model class concerned with storing information in a Note type DataObject.
 */
public class NoteDataObject extends DataObject<String> {

    TextView content;//Content of the note

    //Constructor
    public NoteDataObject(String noteTitle, TextView noteContent){
        this.title = noteTitle;
        this.content = noteContent;
    }

    public NoteDataObject(){

    }

    //Returns the content of the note.
    public String getContent(){
        return content.getText().toString();
    }

    ////Returns the title of the note.
    public String getTitle(){
        return title;
    }
}
