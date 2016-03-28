package com.example.android.softpaper;

import android.widget.TextView;

/**
 * Created by Shreyas on 28/03/2016.
 */
public class NoteDataObject extends DataObject<String> {

    TextView content;

    public NoteDataObject(String noteTitle, TextView noteContent){
        this.title = noteTitle;
        this.content = noteContent;
    }

    public NoteDataObject(){

    }

    public String getContent(){
        return content.getText().toString();
    }

    public String getTitle(){
        return title;
    }
}
