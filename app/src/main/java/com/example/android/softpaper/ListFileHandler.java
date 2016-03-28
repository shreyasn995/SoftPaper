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
 * Created by Shreyas on 28/03/2016.
 */
public class ListFileHandler extends FileHandler<String[],ListDataObject>{

    private static final String ITEM = "item";
    private static final String TEXT = "text";
    private static final String CHECKBOX = "checkbox";
    String[] text;
    Boolean[] checkbox;
    int noOfItems;

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
                    if (!line.equals(fileToDelete)) {
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
            for(int i=0; i<noOfItems; i++){
                db = dbf.newDocumentBuilder();
                Document doc = db.newDocument();
                items[i] = doc.createElement(ITEM);

                Element e = doc.createElement(TEXT);
                e.appendChild(doc.createTextNode(text[i]));
                items[i].appendChild(e);

                e = doc.createElement(CHECKBOX);
                e.appendChild(doc.createTextNode(Boolean.toString(checkbox[i])));
                items[i].appendChild(e);

                doc.appendChild(items[i]);
                //Save the XML  file
                TransformerFactory transformerFactory = TransformerFactory
                        .newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(openFileOutput(filenameList, Context.MODE_APPEND));
                transformer.transform(source, result);
            }
            //Add saved file's name to list of existing files
            //If file already exists and is being saved after editing,
            // check if file already exists before adding to this list
            inputStream = context.openFileInput(savedListsTitles);
            BufferedReader input = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            Boolean checkIfFileExists = Boolean.FALSE;
            while ((line = input.readLine()) != null){
                if (line.equals(listTitle))checkIfFileExists = Boolean.TRUE;
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

    String[] loadContent(String fileToLoad){
        fileToPerformAction = new File(context.getFilesDir(), fileToLoad);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        String[] listContent;
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(fileToPerformAction);
            NodeList nodeList = doc.getChildNodes();
            noOfItems = nodeList.getLength();
            listContent = new String[noOfItems];
            for(int i=0; i<noOfItems; i++){
                Node n = nodeList.item(i);
                NodeList nl = n.getChildNodes();
                Node n2 = nl.item(0);
                listContent[i] = n2.getTextContent();
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return listContent;
    }

    Boolean[] loadIsChecked(String fileToLoad){
        fileToPerformAction = new File(context.getFilesDir(), fileToLoad);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        Boolean[] listCheckboxes;
        try {
            db = dbf.newDocumentBuilder();
            Document doc = db.parse(fileToPerformAction);
            NodeList nodeList = doc.getChildNodes();
            noOfItems = nodeList.getLength();
            listCheckboxes = new Boolean[noOfItems];
            for(int i=0; i<noOfItems; i++){
                Node n = nodeList.item(i);
                NodeList nl = n.getChildNodes();
                Node n2 = nl.item(1);
                listCheckboxes[i] = Boolean.valueOf(n2.getTextContent());
            }
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return listCheckboxes;
    }

    ArrayList<String> getNoteTitles(){
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
            return null;
        }
        return listTitles;
    }

    public ListFileHandler(Context context){
        this.context = context;
    }
}
