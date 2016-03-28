/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
package com.example.android.softpaper;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class provides methods for all required file operations on Lists.
 * Lists are stored in XML documents.
 * Each list is stored as separate document.
 * The document name will be the title of the list.
 */
public class ListFileHandler extends FileHandler<String[],ListDataObject>{

    private static final String ITEM = "item";
    private static final String TEXT = "text";
    private static final String CHECKBOX = "checkbox";
    String[] text;
    Boolean[] checkbox;
    int noOfItems;

    //Delete a given List file
    boolean deleteDataFile(String fileToDelete){
        if (fileToDelete == null) return Boolean.FALSE;
        fileToPerformAction = new File(context.getFilesDir(),fileToDelete);
        if (!fileToPerformAction.exists())return Boolean.FALSE;
        boolean deleted = fileToPerformAction.delete();
        if (deleted) { //Remove file name from the list of saved files
            try {
                inputStream = context.openFileInput(savedListsTitles);
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder reader = new StringBuilder();
                String line;
                while ((line = input.readLine()) != null) {
                    String fileName = line + ".xml";
                    if (!fileName.equals(fileToDelete)) {
                        reader.append(line);
                        reader.append("\n");
                    }
                }
                outputStream = context.openFileOutput(savedListsTitles, Context.MODE_PRIVATE);
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

    //Save a List Object in a XML file.
    boolean saveDataFile(ListDataObject listDataObject){
        if (listDataObject == null) return Boolean.FALSE;
        text = listDataObject.getContent();
        checkbox = listDataObject.getCheckBoxes();
        noOfItems = listDataObject.getSize();
        String filenameList = listDataObject.getTitle() + ".xml";
        String listTitle = listDataObject.getTitle();
        fileToPerformAction = new File(context.getFilesDir(), filenameList);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;

        try {
            //Make XML tree
            Element[] items = new Element[noOfItems];
            db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            Element root = doc.createElement("ROOT");
            for(int i=0; i<noOfItems; i++) {
                items[i] = doc.createElement(ITEM);

                Element e = doc.createElement(TEXT);
                e.appendChild(doc.createTextNode(text[i]));
                items[i].appendChild(e);

                e = doc.createElement(CHECKBOX);
                e.appendChild(doc.createTextNode(Boolean.toString(checkbox[i])));
                items[i].appendChild(e);

                root.appendChild(items[i]);
            }
            doc.appendChild(root);
            //Save the XML  file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(fileToPerformAction);
            transformer.transform(source, result);

            //Add saved file's name to list of existing files
            //If file already exists and is being saved after editing,
            //check if file already exists before adding to this list
            fileToPerformAction = new File(context.getFilesDir(),savedListsTitles);
            Boolean checkIfFileExists = Boolean.FALSE;
            if(fileToPerformAction.exists()){
                inputStream = context.openFileInput(savedListsTitles);
                BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = input.readLine()) != null){
                    if (line.equals(listTitle))checkIfFileExists = Boolean.TRUE;
                }
            }
            if (!checkIfFileExists){ //Add new file name to existing list of file names
                outputStream = context.openFileOutput(savedListsTitles, Context.MODE_APPEND);
                listTitle += "\n";
                outputStream.write(listTitle.getBytes());
                outputStream.close();
            }
            return Boolean.TRUE;
        }catch(Exception e){
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    //Return an array containing the TextView elements of a saved list.
    String[] loadContent(String fileToLoad){
        fileToPerformAction = new File(context.getFilesDir(), fileToLoad);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        String[] listContent;
        //Peel apart the nodes till you get the TextView.
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(fileToPerformAction);
            Node root = doc.getFirstChild();
            NodeList items = root.getChildNodes();
            noOfItems = items.getLength();
            listContent = new String[noOfItems];
            for(int i=0; i<noOfItems; i++){
                Node item = items.item(i);
                NodeList itemContents = item.getChildNodes();
                Node text = itemContents.item(0);
                listContent[i] = text.getTextContent();
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return listContent;
    }

    //Return an array that says which of the CheckBoxes in a list are ticked.
    Boolean[] loadIsChecked(String fileToLoad){
        fileToPerformAction = new File(context.getFilesDir(), fileToLoad);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Boolean[] listCheckboxes;
        //Peel apart the nodes till you get the boolean indicating if CheckBox is ticked.
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(fileToPerformAction);
            Node root = doc.getFirstChild();
            NodeList items = root.getChildNodes();
            noOfItems = items.getLength();
            listCheckboxes = new Boolean[noOfItems];
            for(int i=0; i<noOfItems; i++){
                Node item = items.item(i);
                NodeList itemContents = item.getChildNodes();
                Node checkBox = itemContents.item(1);
                listCheckboxes[i] = Boolean.valueOf(checkBox.getTextContent());
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return listCheckboxes;
    }

    //Returns the list of all the saved titles of the lists.
    ArrayList<String> getListTitles(){
        ArrayList<String> listTitles = new ArrayList<>();
        try {
            inputStream = context.openFileInput(savedListsTitles);
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = input.readLine()) != null){
                listTitles.add(line);
            }
        }catch(Exception e){
            e.printStackTrace();
            return listTitles;
        }
        return listTitles;
    }

    //Constructor
    public ListFileHandler(Context context){
        this.context = context;
    }
}
