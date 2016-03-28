/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
package com.example.android.softpaper;

import android.widget.CheckBox;
import android.widget.TextView;

/**
 *This the Model class concerned with storing information in a List type DataObject.
 */
public class ListDataObject extends DataObject<String[]> {

    TextView[] content; //List of TextViews the user has added.
    CheckBox[] checkBoxes; //List of CheckBoxes the user has added.
    int size; //Number of items. i.e., no of TextView and TextBoxes.

    //Constructor
    public ListDataObject(String listTitle, TextView[] listContent, CheckBox[] listCheckBoxes, int listSize){
        this.title = listTitle;
        this.content = listContent;
        this.checkBoxes = listCheckBoxes;
        this.size = listSize + 1;
    }

    public ListDataObject(){

    }

    //Returns an array of TextBoxes (content of TextBoxes) the user had added.
    public String[] getContent(){
        String[] listContent = new String[size];
        for (int i=0; i<size; i++){
            listContent[i] = content[i].getText().toString();
        }
        return listContent;
    }

    //Return the title of  the list.
    public String getTitle(){
        return title;
    }

    //Returns an array indicating which of the CheckBoxes are ticked.
    public Boolean[] getCheckBoxes(){
        Boolean[] isChecked = new Boolean[size];
        for (int i=0; i<size; i++){
            isChecked[i] = checkBoxes[i].isChecked();
        }
        return  isChecked;
    }

    //Returns the number of items in the list
    public int getSize(){
        return size;
    }
}
