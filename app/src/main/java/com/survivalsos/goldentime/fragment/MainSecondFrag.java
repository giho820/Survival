package com.survivalsos.goldentime.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.activity.ArticleListAct;
import com.survivalsos.goldentime.activity.CheckListAct;
import com.survivalsos.goldentime.activity.GuideAddedMainAct;
import com.survivalsos.goldentime.adapter.MainImageRecyclerAdapter;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.MainImageItemInfo;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.MoveActUtil;

import java.util.ArrayList;

// 메인화면 - 2.서바이벌 탭
public class MainSecondFrag extends Fragment{

    public static Context mContext;
    public static GuideAddedMainAct guideAddedMainAct;

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
        mContext = getActivity();
        guideAddedMainAct = (GuideAddedMainAct) getActivity();

        //헤더 섹션부분이랑 구분해서 데이터를 만드는 부분
        mainImages = new ArrayList<>();
        mainImages.add(0, new MainImageItemInfo());
        mainImages.addAll(DatabaseCRUD.getMainImageItemInfoFromAssetFolder(Definitions.SECTION_TYPE.SURVIVAL_PRINCIPLE));
        mainImages.add(mainImages.size(), new MainImageItemInfo());
        mainImages.addAll(DatabaseCRUD.getMainImageItemInfoFromAssetFolder(Definitions.SECTION_TYPE.READY_FOR_EMERGENCY));
        DebugUtil.showDebug("mainImages size :: " + mainImages.size());
    }

    // Inflate the fragment layout we defined above for this fragment
    // Set the associated text for the title
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DebugUtil.showDebug("MainSecondFrag onCreateView;;;;;;;;;;");
        view = inflater.inflate(R.layout.fragment_main_viewpager_survival, container, false);

        mainImageListRecyclerView = (RecyclerView) view.findViewById(R.id.main_listview_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mainImageListRecyclerView.setLayoutManager(linearLayoutManager);
        mainImageRecyclerAdapter = new MainImageRecyclerAdapter(getActivity(), 1);
        mainImageRecyclerAdapter.setAdapterItemClickListener(new AdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, int position) {
                //체크리스트 항목인 경우에
                if(mainImages.get(position).mainImageCode == 43){
                    Intent intent = new Intent(mContext, CheckListAct.class);
                    MoveActUtil.moveActivity(guideAddedMainAct, intent, -1, -1, false, false);

                } else {
                    DebugUtil.showDebug("mainImage :: " + mainImages.get(position).toString() + " 클릭 됨...");
                    //Todo (완료)Article로 이동하는 부분
                    Intent intent = new Intent(mContext, ArticleListAct.class);
                    intent.putExtra("mainImagesPosition", mainImages.get(position).mainImageCode);
                    MoveActUtil.moveActivity(guideAddedMainAct, intent, -1, -1, false, false);
                }
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
