package com.wolasoft.fiatlux.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.wolasoft.fiatlux.R;
import com.wolasoft.fiatlux.activities.SettingsActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {

    protected String CURRENT_SECTION;
    private SharedPreferences sharedPreferences;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_base, container, false);
        //setting up prefenrences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        CURRENT_SECTION = sharedPreferences.getString(SettingsActivity.CURRENT_SECTION_KEY, "ALL");
        //Toast.makeText(getContext(), CURRENT_SECTION, Toast.LENGTH_SHORT).show();
        return null;
    }

}
