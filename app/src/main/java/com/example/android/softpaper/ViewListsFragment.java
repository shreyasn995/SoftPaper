package com.example.android.softpaper;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewListsFragment extends Fragment {
    Boolean listsContentFragmentActive = Boolean.FALSE;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_view_lists, container, false);

        //Set the ListsTitleFragment to show all saved lists
        Fragment listsTitlesFragment = new ListsTitlesFragment();
        listsTitlesFragment.setTargetFragment(this, 0);
        android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.child_fragment_list, listsTitlesFragment);
        transaction.commit();

        return rootView;
    }

    /**
     *When a list title is selected in the ListsTitleFragment, this fragment sends a message to its parent fragment (this one).
     *Intercept the message and set the ListsContentFragment to show the content of the selected list.
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 0 && resultCode == Activity.RESULT_OK) {
            if(data != null) {
                String value = data.getStringExtra("message");
                if(value != null) {
                    Fragment listsContentFragment = new ListsContentFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("listTitle", value);
                    listsContentFragment.setArguments(bundle);
                    android.support.v4.app.FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                    if (listsContentFragmentActive)transaction.replace(R.id.child_fragment_list2, listsContentFragment);
                    else {
                        transaction.add(R.id.child_fragment_list2, listsContentFragment);
                        listsContentFragmentActive = Boolean.TRUE;
                    }
                    transaction.commit();
                }
            }
        }
    }
}
