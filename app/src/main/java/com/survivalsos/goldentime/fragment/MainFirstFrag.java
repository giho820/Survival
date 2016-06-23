package com.survivalsos.goldentime.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.ParentFrag;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.activity.ArticleListAct;
import com.survivalsos.goldentime.activity.MainAct;
import com.survivalsos.goldentime.adapter.MainImageRecyclerAdapter;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.MainImageItemInfo;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.MoveActUtil;

import java.util.ArrayList;

// 메인화면 - 1. 재난 대처 탭
public class MainFirstFrag extends ParentFrag{

    public static Context mContext;
    public static MainAct mainAct;

    public static View view;
    private RecyclerView mainImageListRecyclerView;
    private MainImageRecyclerAdapter mainImageRecyclerAdapter;

    private int mPage;
    public static final String ARG_PAGE = "MainFirstFrag";
    //메인화면 이미지 파일명
    private ArrayList<MainImageItemInfo> mainImages;

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
        mContext = getActivity();
        mainAct = (MainAct) getActivity();

        mainImages = new ArrayList<>();

        mainImages.add(0, new MainImageItemInfo()); //idx 0 , 1개
        mainImages.addAll(DatabaseCRUD.getMainImageItemInfoFromAssetFolder(Definitions.SECTION_TYPE.NATURE_DISASTER)); //idx 1 ~ 6, 6개 / 총 7개
//        mainImages = DatabaseCRUD.getMainImageItemInfoFromAssetFolder(Definitions.SECTION_TYPE.NATURE_DISASTER, Definitions.SECTION_TYPE.ACCIDENT_FIRE);
        mainImages.add(mainImages.size(), new MainImageItemInfo()); //idx 7 // 총 8개
        mainImages.addAll(DatabaseCRUD.getMainImageItemInfoFromAssetFolder(Definitions.SECTION_TYPE.ACCIDENT_FIRE));
        DebugUtil.showDebug("mainImages size :: " + mainImages.size());
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DebugUtil.showDebug("MainFirstFrag onCreateView;;;;;;;;;;");
        view = inflater.inflate(R.layout.fragment_main_viewpager_ready_disaster, null);

        mainImageListRecyclerView = (RecyclerView) view.findViewById(R.id.main_listview_recycler);
        //Todo 아래 두 줄은 왜 한거지..;혹시 이걸 바꾸면 그리드뷰도 될 수 있고 그런 건가 <- 알아보도록 하자
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mainImageListRecyclerView.setLayoutManager(linearLayoutManager);
        mainImageRecyclerAdapter = new MainImageRecyclerAdapter(getActivity());
        mainImageRecyclerAdapter.setAdapterItemClickListener(new AdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, int position) {
                DebugUtil.showDebug("mainImage :: " + mainImages.get(position).toString() + " 클릭 됨...");
                //Todo (완료)Article로 이동하는 부분
                Intent intent = new Intent(mContext, ArticleListAct.class);
                intent.putExtra("mainImagesPosition", mainImages.get(position).mainImageCode);
                MoveActUtil.moveActivity(mainAct, intent, -1, -1, false, false);
            }
        });
        mainImageListRecyclerView.setAdapter(mainImageRecyclerAdapter);


        if (mainImages != null) {
            mainImageRecyclerAdapter.setAdapterArrayList(mainImages);
            mainImageRecyclerAdapter.notifyDataSetChanged();
            DebugUtil.showDebug("mainImageRecyclerAdapter.setAdapterArrayList 진행함");
        }

        return view;
    }
}
