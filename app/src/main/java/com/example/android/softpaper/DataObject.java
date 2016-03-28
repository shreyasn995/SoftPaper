/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
package com.example.android.softpaper;

/**
 * This is an abstract class for defining different types of data.
 * Data Object classes are the Model classes for this app. They are only concerned about defining and storing the data.
 * Currently, Note type and List types are derived from this class.
 */
abstract class DataObject<T> {
    String title; // Title of the Note or List
    abstract T getContent();
    abstract String getTitle();
}
