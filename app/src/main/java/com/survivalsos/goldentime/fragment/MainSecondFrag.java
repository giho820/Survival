package com.survivalsos.goldentime.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
//        mainImages.add(0, new MainImageItemInfo());
        mainImages.addAll(DatabaseCRUD.getMainImageItemInfoFromAssetFolder(Definitions.SECTION_TYPE.SURVIVAL_PRINCIPLE));
//        mainImages.add(mainImages.size(), new MainImageItemInfo());
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
                    DebugUtil.showDebug("mainImage :: " + mainImages.get(position).toString() + " 클릭 됨...");

                if(mainImages.get(position).doesLocked > 0) {
                    android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle("알림");
                    alertDialog.setMessage("구매하셔야 보실 수 있는 항목입니다. \n한 번의 구매로 모든 컨텐츠를 다운로드 받을 수 있습니다. ");
                    alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE, "취소",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE, "구매",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();

                                    Intent intentDownLoad = new Intent(Intent.ACTION_VIEW);
//                intentDownLoad.setData(Uri.parse("market://details?id=" + "com.google.android.tts"));
                                    intentDownLoad.setData(Uri.parse("market://details?id=" + guideAddedMainAct.getPackageName()));
                                    if (this == null) {
                                        DebugUtil.showDebug("parentAct is null");
                                    } else {
                                        DebugUtil.showDebug("parentAct is not null ");
                                        MoveActUtil.moveActivity(guideAddedMainAct, intentDownLoad, -1, -1, false, false);
                                    }

                                }
                            });

                    alertDialog.show();
                } else {
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
