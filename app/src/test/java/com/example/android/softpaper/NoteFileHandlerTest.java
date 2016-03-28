package com.example.android.softpaper;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.test.mock.MockContext;
import android.widget.EditText;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Shreyas on 29/03/2016.
 */
public class NoteFileHandlerTest extends ActivityInstrumentationTestCase2<NoteFileHandler> {

    NoteFileHandler mActivity;

    @SuppressWarnings("deprecation")
    public NoteFileHandlerTest()
    {
        super("com.main.account.Login",NoteFileHandler.class);
    }

    private Context testCtx;

    String noteTitle = "testNote";
    EditText noteTextView;
    String noteContent = "testNoteContent";

    @Before
    public void setUp() throws Exception {
        super.setUp();
        testCtx = new MockContext();
        assertNotNull(testCtx);
        mActivity = getActivity();
        assertNotNull(mActivity);
        noteTextView = (EditText) mActivity.findViewById(R.id.edit_newNoteNote);
        assertNotNull(noteTextView);
        noteTextView.setText(noteContent);
    }

    @Test
    public void testSave(){
        NoteDataObject noteDataObject = new NoteDataObject(noteTitle,noteTextView);
        NoteFileHandler noteFileHandler = new NoteFileHandler(testCtx);
        noteFileHandler.saveDataFile(noteDataObject);
        String savedText = noteFileHandler.loadContent(noteTitle);
        assertEquals(noteContent,savedText);
    }
}
