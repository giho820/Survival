package com.survivalsos.goldentime.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.util.DebugUtil;

// 메인화면 - 1. 재난 대처 탭
public class MainFirstFrag extends Fragment{

    public static final String ARG_PAGE = "MainFirstFrag";
    private static View view;

    private TextView test;
    private int mPage;

    public static MainFirstFrag newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MainFirstFrag fragment = new MainFirstFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPage = getArguments().getInt(ARG_PAGE);
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_viewpager_ready_disaster, null);
        test = (TextView) view.findViewById(R.id.testtest);
        test.setText("gggggggg");
        //Todo main에 소스 여기로 옮길 것

        DebugUtil.showDebug("MainFirstFrag onCreateView;;;;;;;;;;");
        return view;
    }
}
