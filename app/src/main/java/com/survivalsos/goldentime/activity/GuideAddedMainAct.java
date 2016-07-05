package com.survivalsos.goldentime.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import com.survivalsos.goldentime.database.ChangeableDatabaseHelper;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.database.DatabaseHelper;
import com.survivalsos.goldentime.database.util.DatabaseConstantUtil;
import com.survivalsos.goldentime.fragment.MainFirstFrag;
import com.survivalsos.goldentime.fragment.MainSecondFrag;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.MoveActUtil;

public class GuideAddedMainAct extends ParentAct
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {


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



        try{
            //Table 복사
            if (!DatabaseCRUD.checkTable(ChangeableDatabaseHelper.changeableDatabaseHelper, DatabaseHelper.sqLiteDatabase, DatabaseConstantUtil.TABLE_CHECK_LIST)) {
                DebugUtil.showDebug("CheckedList Table was not exist");
                DebugUtil.showDebug("insert 실행");

                //Todo 이 과정에서 이미 존재하는 값이 있다면 insert를 하지 않고 업데이트를 수행한다
                //테이블의 내용을 채우는 과정
                //insert 쿼리를 리턴하는 함수를 만들고나서 동적으로 생성한 checkedlist db에 insert 쿼리를 날린다
                String insertQuery = DatabaseCRUD.getInsertQueryFromCheckListTable();
                DebugUtil.showDebug("insertQuery :: " + insertQuery);
                DatabaseCRUD.execRawQuery(insertQuery);

                DatabaseCRUD.selectCheckedListDBQuery(DatabaseConstantUtil.TABLE_USER_CHECKED_LIST);
                DebugUtil.showDebug("에셋에서 정상적으로 테이블 복사됨 ");
            } else {
                DebugUtil.showDebug("테이블 테스트 ::" + DatabaseCRUD.getArticleInfo(11));
            }
        } catch (Exception e) {

        } finally {
            initMainUi();
        }

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

        //뷰페이져
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(mainFragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(2);


        final PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabStrip.setIndicatorHeight(getResources().getDimensionPixelOffset(R.dimen.dp_3));
        tabStrip.setIndicatorColor(getResources().getColor(R.color.c_fff92e14));
        tabStrip.setTabSelectTextColor(getResources().getColor(R.color.c_fff92e14));
        tabStrip.setTextColorResource(R.color.text_color);
        tabStrip.setShouldExpand(true);
        tabStrip.setDividerColor(getResources().getColor(android.R.color.transparent));
        if(Definitions.NanumBarunGothic != null) tabStrip.setTypeface(Definitions.NanumBarunGothic, Typeface.NORMAL);
        tabStrip.setViewPager(viewPager);

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
//                intentDownLoad.setData(Uri.parse("market://details?id=" + "com.google.android.tts"));
                intentDownLoad.setData(Uri.parse("market://details?id=" + this.getPackageName()));
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
//                Uri uri = Uri.parse("market://details?id=" + "com.google.android.tts");
                Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
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

    public class MainFragmentPagerAdapter extends FragmentPagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = {
                "재난 대처", "서바이벌"
        };

        public MainFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return tabTitles.length;//Todo 이거 0으로 하면 onmeasure 어쩌고 하면서 널포인트 에러난다
        }

        @Override
        public Fragment getItem(int position) {
            Fragment returnFrag = new Fragment();
            switch (position) {
                case 1:
                    return new MainSecondFrag();
                default:
                    return new MainFirstFrag();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // Generate title based on item position
            return tabTitles[position];
        }

        @Override
        public int getItemPosition(Object object) {
            int position;
            if (object instanceof MainFirstFrag) {
                position = 0;
            } else {
                position = 1;
            }
            return position;
        }
    }
}
