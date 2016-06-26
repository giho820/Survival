package com.survivalsos.goldentime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.survivalsos.goldentime.R;

/**
 * Created by kiho on 2016. 6. 26..
 */

/**
 * A placeholder fragment containing a simple view.
 */
public class CheckListAllFrag extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public CheckListAllFrag() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static CheckListAllFrag newInstance(int sectionNumber) {
        CheckListAllFrag fragment;
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment = new CheckListAllFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_check_list, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
        return rootView;
    }
}