package com.survivalsos.goldentime.activity;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ParentAct;
import com.astuetz.PagerSlidingTabStrip;
import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.adapter.MainFragmentPagerAdapter;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.database.DatabaseHelper;
import com.survivalsos.goldentime.database.util.DatabaseConstantUtil;
import com.survivalsos.goldentime.database.util.DatabaseUtil;
import com.survivalsos.goldentime.util.DebugUtil;

import java.io.IOException;

//Todo 잠깐 안 씀
public class MainAct extends ParentAct {

    //Todo introAct로 옮겨야함
    private DatabaseHelper databaseHelper;
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DebugUtil.showDebug("MainAct :: onCreate()");

        initMainUi();

        //Todo introAct로 옮겨야함2
        copyDatabaseOnIntroAct();

    }

    private void initMainUi() {
        //페이져 어댑터
        mainFragmentPagerAdapter = new MainFragmentPagerAdapter(getSupportFragmentManager());

        // about ViewPager
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(mainFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(2);

        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_3));
        tabStrip.setIndicatorColor(getResources().getColor(R.color.c_fff92e14));
        tabStrip.setTextColorResource(R.color.text_color);
        tabStrip.setShouldExpand(true);
        tabStrip.setDividerColor(getResources().getColor(android.R.color.transparent));

        if(Definitions.LatoBlack != null) tabStrip.setTypeface(Definitions.LatoBlack, Typeface.NORMAL);
        tabStrip.setViewPager(viewPager);

    }

    public void copyDatabaseOnIntroAct() {
        DebugUtil.showDebug("MainAct, copyDatabaseOnIntroAct() ::");
        databaseHelper = DatabaseHelper.getInstacnce(this);//Todo 모든 액티비티에서 실행해야하므로 상위 액티비티로 보내서 실행할 것
        copyDatabase(DatabaseHelper.sqLiteDatabase);
    }

    // to copy database
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
