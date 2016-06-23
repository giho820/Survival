package com.survivalsos.goldentime.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.activity.MainAct;
import com.survivalsos.goldentime.adapter.MainImageRecyclerAdapter;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.model.MainImageItemInfo;
import com.survivalsos.goldentime.util.DebugUtil;

import java.util.ArrayList;

// 메인화면 - 2.서바이벌 탭
public class MainSecondFrag extends Fragment{

    public static Context mContext;
    public static MainAct mainAct;

    private static View view;
    private RecyclerView mainImageListRecyclerView;
    private MainImageRecyclerAdapter mainImageRecyclerAdapter;

    private int mPage;
    public static final String ARG_PAGE = "MainSecondFrag";
    //메인화면 이미지 파일명
    private ArrayList<MainImageItemInfo> mainImages;

    public static MainSecondFrag newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        MainSecondFrag fragment = new MainSecondFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mPage = getArguments().getInt(ARG_PAGE);
        mainImages = new ArrayList<>();
//        mainImages = DatabaseCRUD.getMainImageFileNameFromAssetFolder(Definitions.SECTION_TYPE.NATURE_DISASTER);
        mainImages = DatabaseCRUD.getMainImageItemInfoFromAssetFolder(Definitions.SECTION_TYPE.NATURE_DISASTER, Definitions.SECTION_TYPE.ACCIDENT_FIRE);
        DebugUtil.showDebug("mainImages size :: " + mainImages.size());
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main_viewpager_survival, container, false);

        //Todo main에 소스 여기로 옮길 것
        DebugUtil.showDebug("MainSecondFrag onCreateView;;;;;;;;;;");
        return view;
    }
}
