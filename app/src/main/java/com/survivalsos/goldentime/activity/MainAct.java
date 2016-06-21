package com.survivalsos.goldentime.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.adapter.MainFragmentPagerAdapter;
import com.survivalsos.goldentime.adapter.MainImageRecyclerAdapter;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.database.DatabaseHelper;
import com.survivalsos.goldentime.database.util.DatabaseConstantUtil;
import com.survivalsos.goldentime.database.util.DatabaseUtil;
import com.survivalsos.goldentime.fragment.MainFirstFrag;
import com.survivalsos.goldentime.fragment.MainSecondFrag;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.MoveActUtil;

import java.io.IOException;
import java.util.ArrayList;

public class MainAct extends AppCompatActivity {

    //Todo introAct로 옮겨야함
    private DatabaseHelper databaseHelper;

    private MainFragmentPagerAdapter mainFragmentPagerAdapter;
    //메인화면 이미지 파일명
    private ArrayList<Integer> mainImages;
    private RecyclerView mainImageListRecyclerView;
    private MainImageRecyclerAdapter mainImageRecyclerAdapter;

    private MainFirstFrag mainFirstFrag;
    private MainSecondFrag mainSecondFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DebugUtil.showDebug("MainAct :: onCreate()");

        initMainUi();
        mainImages = new ArrayList<>();

    //Todo introAct로 옮겨야함2
        copyDatabaseOnIntroAct();

        mainImages = DatabaseCRUD.getMainImageFileNameFromAssetFolder(Definitions.SECTION_TYPE.NATURE_DISASTER);
        //Todo 뽑은 이미지에 대해서 동적으로 ImageView만 있는 리스트

        DebugUtil.showDebug("mainImages size :: " + mainImages.size());
//        testImage.setImageDrawable(ImageUtil.loadDrawableFromAssets(this, "image/11.png"));
        if(mainImages != null) {
            mainImageRecyclerAdapter.setAdapterArrayList(mainImages);
            mainImageRecyclerAdapter.notifyDataSetChanged();
            DebugUtil.showDebug("mainImageRecyclerAdapter.setAdapterArrayList 진행함");
        }

//        // about ViewPager
//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        mainFragmentPagerAdapter = new MainFragmentPagerAdapter(this.getSupportFragmentManager());
//        viewPager.setAdapter(mainFragmentPagerAdapter);
//        viewPager.setOffscreenPageLimit(2);
//
//        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
//        tabStrip.setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_3));
//        tabStrip.setIndicatorColor(getResources().getColor(R.color.c_ffffffff));
//        tabStrip.setTextColorResource(R.color.c_ffffffff);
//        tabStrip.setShouldExpand(true);
//        tabStrip.setDividerColor(getResources().getColor(android.R.color.transparent));
//        tabStrip.setViewPager(viewPager);
//        tabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                DebugUtil.showDebug("선택된 탭은 :: " + position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }


    private void initMainUi() {
        mainImageListRecyclerView = (RecyclerView) findViewById(R.id.main_listview_recycler);
        //Todo 아래 두 줄은 왜 한거지..;혹시 이걸 바꾸면 그리드뷰도 될 수 있고 그런 건가 <- 알아보도록 하자
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        mainImageListRecyclerView.setLayoutManager(linearLayoutManager);

        mainImageRecyclerAdapter = new MainImageRecyclerAdapter(this);
        mainImageRecyclerAdapter.setAdapterItemClickListener(new AdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, int position) {
                DebugUtil.showDebug("mainImage :: " + mainImages.get(position).toString() + " 클릭 됨..." );
                //Todo (완료)Article로 이동하는 부분
                Intent intent = new Intent(MainAct.this, ArticleListAct.class);
                intent.putExtra("mainImagesPosition", mainImages.get(position));
                MoveActUtil.moveActivity(MainAct.this, intent, -1, -1, false, false);
            }
        });
        mainImageListRecyclerView.setAdapter(mainImageRecyclerAdapter);
    }

    public void copyDatabaseOnIntroAct() {
        DebugUtil.showDebug("MainAct, copyDatabaseOnIntroAct() ::");
        databaseHelper = DatabaseHelper.getInstacnce(this);//Todo 모든 액티비티에서 실행해야하므로 상위 액티비티로 보내서 실행할 것
        copyDatabase(DatabaseHelper.sqLiteDatabase);
    }

    /**
     * to copy database
     *
     * @param sqLiteDatabase
     */
    private void copyDatabase(SQLiteDatabase sqLiteDatabase) {

        if (!DatabaseCRUD.checkTable(databaseHelper, sqLiteDatabase, DatabaseConstantUtil.TABLE_CATEGORY)) {
            DebugUtil.showDebug("CATEGORY table is not existed");
            try {
                DatabaseUtil.copyDataBase(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            DebugUtil.showDebug("CATEGORY table is existed");
        }
    }
}
