package com.survivalsos.goldentime.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

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
import com.survivalsos.goldentime.util.MoveActUtil;

import java.io.IOException;

public class GuideAddedMainAct extends ParentAct
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //Todo introAct로 옮겨야함
    private DatabaseHelper databaseHelper;
    private MainFragmentPagerAdapter mainFragmentPagerAdapter;
    private LinearLayout linearLayoutOpenDrawer;
    private LinearLayout linearLayoutSearch;
    //drawer 메뉴
    private LinearLayout linearLayoutDrawerNotice;
    private LinearLayout linearLayoutDrawerCopyRight;
    private LinearLayout linearLayoutDrawerContact;
    private LinearLayout linearLayoutDrawerDownload;
    private LinearLayout linearLayoutDrawerBookmark;
    private LinearLayout linearLayoutDrawerReview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initMainUi();

        //Todo introAct로 옮겨야함2
        copyDatabaseOnIntroAct();

    }

    private void initMainUi() {

        //guide 영역
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//invisible
        setSupportActionBar(toolbar);//invisible

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setOnClickListener(this);

        linearLayoutOpenDrawer = (LinearLayout) findViewById(R.id.linearlayout_open_drawer);
        linearLayoutOpenDrawer.setOnClickListener(this);

        linearLayoutSearch = (LinearLayout) findViewById(R.id.linearlayout_search);
        linearLayoutSearch.setOnClickListener(this);

        //drawer 메뉴
        linearLayoutDrawerNotice = (LinearLayout) findViewById(R.id.linearlayout_drawer_notificaiton);
        linearLayoutDrawerCopyRight = (LinearLayout) findViewById(R.id.linearlayout_drawer_copyright);
        linearLayoutDrawerContact = (LinearLayout) findViewById(R.id.linearlayout_drawer_contact);
        linearLayoutDrawerDownload = (LinearLayout) findViewById(R.id.linearlayout_drawer_download);
        linearLayoutDrawerBookmark = (LinearLayout) findViewById(R.id.linearlayout_drawer_bookmark);
        linearLayoutDrawerReview = (LinearLayout) findViewById(R.id.linearlayout_drawer_review);

        linearLayoutDrawerNotice.setOnClickListener(this);
        linearLayoutDrawerCopyRight.setOnClickListener(this);
        linearLayoutDrawerContact.setOnClickListener(this);
        linearLayoutDrawerDownload.setOnClickListener(this);
        linearLayoutDrawerBookmark.setOnClickListener(this);
        linearLayoutDrawerReview.setOnClickListener(this);

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
        databaseHelper = DatabaseHelper.getInstacnce(this);
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linearlayout_open_drawer:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
                break;

            case R.id.linearlayout_search:
                //Todo 서치 액티비티로 move
                MoveActUtil.chageActivity(this, SearchAct.class, R.anim.up, R.anim.down, false, false);
                break;

            case R.id.linearlayout_drawer_notificaiton:
                Intent moveToDrawerNotificationAct = new Intent(this, DrawerNotificationAct.class);
                moveToDrawerNotificationAct.putExtra("whichInfo", 0);
                MoveActUtil.moveActivity(this, moveToDrawerNotificationAct, R.anim.right_in, R.anim.right_out, false, false);
                break;

            case R.id.linearlayout_drawer_copyright:
                Intent moveToDrawerNotificationActAtCopy = new Intent(this, DrawerNotificationAct.class);
                moveToDrawerNotificationActAtCopy.putExtra("whichInfo", 1);
                MoveActUtil.moveActivity(this, moveToDrawerNotificationActAtCopy, R.anim.right_in, R.anim.right_out, false, false);
                break;

            case R.id.linearlayout_drawer_contact:
                Intent moveToDrawerNotificationActAtContact = new Intent(this, DrawerNotificationAct.class);
                moveToDrawerNotificationActAtContact.putExtra("whichInfo", 2);
                MoveActUtil.moveActivity(this, moveToDrawerNotificationActAtContact, R.anim.right_in, R.anim.right_out, false, false);
                break;

            case R.id.linearlayout_drawer_download:
                Intent intentDownLoad = new Intent(Intent.ACTION_VIEW);
                intentDownLoad.setData(Uri.parse("market://details?id=" + "com.google.android.tts"));
//                intentDownLoad.setData(Uri.parse("market://details?id=" + this.getPackageName()));
                if (this == null) {
                    DebugUtil.showDebug("parentAct is null");
                } else {
                    DebugUtil.showDebug("parentAct is not null ");
                    MoveActUtil.moveActivity(this, intentDownLoad, -1, -1, false, false);
                }
                break;

            case R.id.linearlayout_drawer_bookmark:
                Intent intentBookmark = new Intent(this, BookmarkAct.class);
                MoveActUtil.moveActivity(this, intentBookmark, R.anim.right_in, R.anim.right_out, false, false);
                break;

            case R.id.linearlayout_drawer_review:
                Uri uri = Uri.parse("market://details?id=" + "com.google.android.tts");
//                Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
                }
                break;
        }
    }
}
