package com.example.android.softpaper;

/**
 * Created by Shreyas on 28/03/2016.
 */
abstract class DataObject<T> {
    String title;
    abstract T getContent();
    abstract String getTitle();
}
