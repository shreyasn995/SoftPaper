/**
 * Shreyas Nagarajappa 2016, The Australian National University
 */
package com.example.android.softpaper;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Toast;

/**
 * This activity handles the View part of the app.
 * It is a tabbed activity. One tab for Notes and another for Checked Lists.
 * Each tab displays the previously saved contents of either notes or lists.
 */

public class ViewNotesActivity extends AppCompatActivity {

    FloatingActionButton fab1;
    FloatingActionButton fab2;
    private int backButtonCount = 0;
    private int currentTabPosition = 0;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        backButtonCount = 0;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the two
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        fab1 = (FloatingActionButton) findViewById(R.id.fab_Tab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab_Tab2);

        /**
         * Change Floating Action Buttons on switching between tabs.
         * fab1 launches the CreateNote Activity to create a new note
         * fab2 launches the CreateList Activity to create a new checked list
         *
         * Code for switching tabs sourced from:
         * Toro, J (2013) onTabSelected Selected Not Called [Computer Code Snippet].
         * http://stackoverflow.com/questions/30904265/ontabselected-selected-not-called.
         * Accessed 13 March 2016.
         */
        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        super.onTabSelected(tab);
                        int tabPosition = tab.getPosition();
                        switch (tabPosition){
                            case 0:
                                fab2.hide();
                                fab1.show();
                                currentTabPosition = tabPosition;
                                break;
                            case 1:
                                fab1.hide();
                                fab2.show();
                                currentTabPosition = tabPosition;
                                break;

                            default:
                                fab2.hide();
                                fab2.show();
                                break;
                        }
                    }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_notes, menu);
        if (currentTabPosition == 0) {
            menu.findItem(R.id.action_edit_list).setVisible(Boolean.FALSE).setEnabled(Boolean.FALSE);
            menu.findItem(R.id.action_delete_list).setVisible(Boolean.FALSE).setEnabled(Boolean.FALSE);
            menu.findItem(R.id.action_edit_note).setVisible(Boolean.TRUE).setEnabled(Boolean.TRUE);
            menu.findItem(R.id.action_delete_note).setVisible(Boolean.TRUE).setEnabled(Boolean.TRUE);
        }
        else if (currentTabPosition == 1) {
            menu.findItem(R.id.action_edit_note).setVisible(Boolean.FALSE).setEnabled(Boolean.FALSE);
            menu.findItem(R.id.action_delete_note).setVisible(Boolean.FALSE).setEnabled(Boolean.FALSE);
            menu.findItem(R.id.action_edit_list).setVisible(Boolean.TRUE).setEnabled(Boolean.TRUE);
            menu.findItem(R.id.action_delete_list).setVisible(Boolean.TRUE).setEnabled(Boolean.TRUE);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //Launch AboutActivity
        if (id == R.id.action_about) {
            Intent launchAboutActivityIntent = new Intent(this, AboutActivity.class);
            startActivity(launchAboutActivityIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the tabs
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            // return PlaceholderFragment.newInstance(position + 1);
            switch (position){
                case 0: return new ViewNotesFragment();
                case 1: return new ViewListsFragment();
                default: return new ViewNotesFragment();
            }
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Notes";
                case 1:
                    return "Check Lists";
            }
            return null;
        }
    }

    /**
     * The Floating Action Buttons (FAB) within each tab are used to create a new list and a new note.
     * Launch the respective activity when the respective FAB is clicked.
     */
    public void launchNewNoteActivity (View view){
        Intent launchNewNoteIntent = new Intent(this, NewNoteActivity.class);
        startActivity(launchNewNoteIntent);
    }

    public void launchNewListActivity (View view){
        Intent launchNewListIntent = new Intent(this, NewListActivity.class);
        startActivity(launchNewListIntent);
    }

    /**
     * Back button listener.
     * Will close the application if the back button pressed twice.
     * App will not got to the login screen if back button pressed. Instead the user will exit the app.
     *
     * Code sourced from:
     * Spreys, V (2013) android pressing back button should exit the app [Computer Code Snippet].
     * Available at http://stackoverflow.com/questions/2354336/android-pressing-back-button-should-exit-the-app.
     * Accessed 13 March 2016.
     */
    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Press back again to close the app", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
}
