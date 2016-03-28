package com.example.android.softpaper;

import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by Shreyas on 28/03/2016.
 */
public class ListDataObject extends DataObject<String[]> {

    TextView[] content;
    CheckBox[] checkBoxes;
    int size;

    public ListDataObject(String listTitle, TextView[] listContent, CheckBox[] listCheckBoxes, int listSize){
        this.title = listTitle;
        this.content = listContent;
        this.checkBoxes = listCheckBoxes;
        this.size = listSize + 1;
    }

    public ListDataObject(){

    }

    public String[] getContent(){
        String[] listContent = new String[size];
        for (int i=0; i<size; i++){
            listContent[i] = content[i].getText().toString();
        }
        return listContent;
    }

    public String getTitle(){
        return title;
    }

    public Boolean[] getCheckBoxes(){
        Boolean[] isChecked = new Boolean[size];
        for (int i=0; i<size; i++){
            isChecked[i] = checkBoxes[i].isChecked();
        }
        return  isChecked;
    }

    public int getSize(){
        return size;
    }
}
