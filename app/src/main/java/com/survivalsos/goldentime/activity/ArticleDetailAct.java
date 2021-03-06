package com.survivalsos.goldentime.activity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.ParentAct;
import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.adapter.AdditionalArticleListAdapter;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.database.util.DatabaseConstantUtil;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.model.CheckList;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.MoveActUtil;
import com.survivalsos.goldentime.util.TextUtil;

import java.io.IOException;
import java.util.ArrayList;

public class ArticleDetailAct extends ParentAct
        implements NavigationView.OnNavigationItemSelectedListener, AdapterItemClickListener, View.OnClickListener {

    private Article currentArticle;
    private Article additionalArticle;

    private LinearLayout linearLayoutOpenDrawer;
    private LinearLayout linearLayoutBookmarkInArticleDetailAct;
    private LinearLayout linearLayoutSearchInArticleDetailAct;
    private ScrollView scrollViewArticleDetail;
    private WebView wv;
    private ArrayList<Article> additionalArticles;
    private ArrayList<CheckList> checkLists;

    private ListView listViewContainingAdditionalArticle;
    private AdditionalArticleListAdapter additionalArticleListAdapter;
    private LinearLayout linearLayoutIconHome;
    private LinearLayout linearLayoutIconBack;
    private LinearLayout linearLayoutIconGuide;
    private LinearLayout linearLayoutIconTop;

    private ImageView ivBookmarkInDetailAct;

    //drawer 메뉴
    private LinearLayout linearLayoutDrawerNotice;
    private LinearLayout linearLayoutDrawerCopyRight;
    private LinearLayout linearLayoutDrawerContact;
    private LinearLayout linearLayoutDrawerDownload;
    private LinearLayout linearLayoutDrawerBookmark;
    private LinearLayout linearLayoutDrawerReview;

    private LinearLayout linearLayoutSpeaker;
    private LinearLayout linearLayoutCheckList;

    private MediaPlayer mPlayer;
    private boolean canPlay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail_with_navigation);

        initUI();

        currentArticle = (Article) getIntent().getSerializableExtra("articleId ArticleListAct To DetailAct");
        additionalArticles = new ArrayList<>();

        if (additionalArticleListAdapter == null) {
            additionalArticleListAdapter = new AdditionalArticleListAdapter(this);
            additionalArticleListAdapter.setAdapterItemClickListener(this);
        }

        if (currentArticle == null) {
            DebugUtil.showDebug("ArticleDetailAct이 넘어오지 않음");
        } else {
            if (currentArticle.articleId != null) {

                DebugUtil.showDebug("Article ID :: " + currentArticle.articleId + " \n " + "sound :: " + currentArticle.isSoundFile);
                String filePath = "file:///android_asset/html/" + currentArticle.articleId + ".html";
                wv.loadUrl(filePath);

                if (currentArticle.relatedArticleId != null && currentArticle.relatedArticleId > 0) {
                    Article additionalArticle = new Article();
                    DebugUtil.showDebug("해당 아티클과 연관된 아티클이 아이디가 존재함 :: " + currentArticle.relatedArticleId);
                    additionalArticle.articleId = currentArticle.relatedArticleId;
//                    additionalArticle.title = DatabaseCRUD.getArticleTitle(currentArticle.relatedArticleId);
                    additionalArticle = DatabaseCRUD.getArticleInfo(currentArticle.relatedArticleId);
                    additionalArticle.type = Definitions.ARTICLE_TYPE.RELATED;
                    additionalArticles.add(additionalArticle);
                }

                if (currentArticle.nextArticleId != null && currentArticle.nextArticleId > 0) {
                    Article additionalArticle = new Article();
                    DebugUtil.showDebug("해당 아티클의 다음 아티클의 아이디가 존재함  ::  " + currentArticle.nextArticleId);
                    additionalArticle.articleId = currentArticle.nextArticleId;
//                    additionalArticle.title = DatabaseCRUD.getArticleTitle(currentArticle.nextArticleId);
                    additionalArticle = DatabaseCRUD.getArticleInfo(currentArticle.nextArticleId);
                    additionalArticle.type = Definitions.ARTICLE_TYPE.NEXT;
                    additionalArticles.add(additionalArticle);
                }

                if (additionalArticles != null) {
                    additionalArticleListAdapter.addItems(additionalArticles);
                }

                if (DatabaseCRUD.isBookmarked(currentArticle.articleId)) {
                    ivBookmarkInDetailAct.setImageResource(R.drawable.sub_icon_bookmark_01);
                }

                if (currentArticle != null && !TextUtil.isNull(currentArticle.isSoundFile)) {
                    if (currentArticle.isSoundFile.equalsIgnoreCase("Y")) {
                        linearLayoutSpeaker.setVisibility(View.VISIBLE);
                    }
                }

                checkLists = new ArrayList<>();
                //Todo 3. insert 하고 업데이트 하는 방법 말고 별도로 조인해서 할 수 있도록 설계해보고 그에 따라 코드 변경할 것

                String selectUserByArticleQuery = "select * from " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST
                        + " where " + DatabaseConstantUtil.COLUMN_ARTICLE_ID_CHECKED_LIST + " = " + currentArticle.articleId + " order by " + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST;
                checkLists = DatabaseCRUD.selectUserCheckList(selectUserByArticleQuery);

                if (checkLists != null && checkLists.size() > 0) {
                    linearLayoutCheckList.setVisibility(View.VISIBLE);
                    DebugUtil.showDebug("보여야하는데 ");
                } else {
                    DebugUtil.showDebug("보이지 않아~");
                }

            }
        }
        additionalArticleListAdapter.setAdapterArrayList(additionalArticles);
        listViewContainingAdditionalArticle.setAdapter(additionalArticleListAdapter);

        ParentAct.setListViewHeightBasedOnChildren(listViewContainingAdditionalArticle);


        linearLayoutBookmarkInArticleDetailAct.setOnClickListener(this);
        linearLayoutSearchInArticleDetailAct.setOnClickListener(this);
        linearLayoutIconHome.setOnClickListener(this);
        linearLayoutIconBack.setOnClickListener(this);
        linearLayoutIconGuide.setOnClickListener(this);
        linearLayoutIconTop.setOnClickListener(this);
    }

    public void initUI() {
        //guide 영역
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);//invisible
        setSupportActionBar(toolbar);//invisible

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleDetailAct.this, CheckListAct.class);
                MoveActUtil.moveActivity(ArticleDetailAct.this, intent, -1, -1, false, false);
            }
        });

        //custom toolbar 영역
        linearLayoutOpenDrawer = (LinearLayout) findViewById(R.id.linearlayout_open_drawer_article_detail);
        linearLayoutOpenDrawer.setOnClickListener(this);
        linearLayoutBookmarkInArticleDetailAct = (LinearLayout) findViewById(R.id.linearlayout_bookmark_in_article_detail_act);
        ivBookmarkInDetailAct = (ImageView) findViewById(R.id.img_bookmark_detail_act);
        linearLayoutSearchInArticleDetailAct = (LinearLayout) findViewById(R.id.linearlayout_search_in_article_detail_act);

        //스크롤뷰 영역
        scrollViewArticleDetail = (ScrollView) findViewById(R.id.scrollViewArticleDetail);
        wv = (WebView) findViewById(R.id.webView_article_detail);
        listViewContainingAdditionalArticle = (ListView) findViewById(R.id.article_detail_listview);

        linearLayoutIconHome = (LinearLayout) findViewById(R.id.linearlayout_icon_home);
        linearLayoutIconBack = (LinearLayout) findViewById(R.id.linearlayout_icon_back);
        linearLayoutIconGuide = (LinearLayout) findViewById(R.id.linearlayout_icon_list);
        linearLayoutIconTop = (LinearLayout) findViewById(R.id.linearlayout_icon_top);

        //스피커
        linearLayoutSpeaker = (LinearLayout) findViewById(R.id.speaker);
        linearLayoutSpeaker.setOnClickListener(this);

        //체크리스트
        linearLayoutCheckList = (LinearLayout) findViewById(R.id.img_having_checklist);
        linearLayoutCheckList.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            finish();
            this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
        }
    }

    @Override
    public void onAdapterItemClick(View view, int position) {
        additionalArticle = additionalArticleListAdapter.getItem(position);
        switch (view.getId()) {
            case R.id.item_additional_click_section:

                if (DatabaseCRUD.checkRelatedArticleAvailable(Integer.parseInt(additionalArticle.articleId.toString().substring(0, 2))) == 1) {

                    /*
                    if( 인앱 결제가 통과 되었으면) {
                            Intent moveToArticleDetailAct = new Intent(this, ArticleDetailAct.class);
                           moveToArticleDetailAct.putExtra("articleId ArticleListAct To DetailAct", additionalArticle);
                           MoveActUtil.moveActivity(this, moveToArticleDetailAct, R.anim.right_in, R.anim.right_out, false, true);
                    } else {

                    다이얼로그 부분 여기로 옮길 것
                    }
                    */

                    //Todo 인앱 결제 인증 들어갈 부분!!! 잠금항목이 아닌 경우에만 통과되도록 한다 . 만일 구매를 안했다면
                    android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(this).create();
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
                                    intentDownLoad.setData(Uri.parse("market://details?id=" + getPackageName()));
                                    if (this == null) {
                                        DebugUtil.showDebug("parentAct is null");
                                    } else {
                                        DebugUtil.showDebug("parentAct is not null ");
                                        MoveActUtil.moveActivity(ArticleDetailAct.this, intentDownLoad, -1, -1, false, false);
                                    }
                                }
                            });

                    alertDialog.show();

                } else {
                    Intent moveToArticleDetailAct = new Intent(this, ArticleDetailAct.class);
                    moveToArticleDetailAct.putExtra("articleId ArticleListAct To DetailAct", additionalArticle);
                    MoveActUtil.moveActivity(this, moveToArticleDetailAct, R.anim.right_in, R.anim.right_out, false, true);
                }


                break;

        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.linearlayout_open_drawer_article_detail:
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
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
                android.support.v7.app.AlertDialog alertDialog = new android.support.v7.app.AlertDialog.Builder(this).create();
                alertDialog.setTitle("알림");
                alertDialog.setMessage("한번의 구매로 모든 컨텐츠를 다운로드 받을 수 있습니다.");
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
                                intentDownLoad.setData(Uri.parse("market://details?id=" + ArticleDetailAct.this.getPackageName()));
                                if (this == null) {
                                    DebugUtil.showDebug("parentAct is null");
                                } else {
                                    DebugUtil.showDebug("parentAct is not null ");
                                    MoveActUtil.moveActivity(ArticleDetailAct.this, intentDownLoad, -1, -1, false, false);
                                }

                            }
                        });

                alertDialog.show();

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

            case R.id.linearlayout_bookmark_in_article_detail_act:
                if (DatabaseCRUD.isBookmarked(currentArticle.articleId)) {
                    ivBookmarkInDetailAct.setImageResource(R.drawable.sub_icon_bookmark_00);
                    String deleteQuery = "delete from " + DatabaseConstantUtil.TABLE_USER_BOOKMARK + " where " +
                            DatabaseConstantUtil.COLUMN_ARTICLE_ID + "=" + currentArticle.articleId + ";";
                    DatabaseCRUD.execRawQuery(deleteQuery);

                } else {
                    ivBookmarkInDetailAct.setImageResource(R.drawable.sub_icon_bookmark_01);
                    String insertQuery = "insert or ignore into " + DatabaseConstantUtil.TABLE_USER_BOOKMARK + "(" + DatabaseConstantUtil.COLUMN_ARTICLE_ID + ", " +
                            DatabaseConstantUtil.COLUMN_TITLE + ") values (" + currentArticle.articleId + ", '" + currentArticle.title + "')";
                    DatabaseCRUD.execRawQuery(insertQuery);
                }
                break;

            case R.id.linearlayout_search_in_article_detail_act:
                MoveActUtil.chageActivity(this, SearchAct.class, R.anim.up, R.anim.down, false, false);
                break;

            case R.id.linearlayout_icon_home:
                Intent intent = new Intent(this, GuideAddedMainAct.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MoveActUtil.moveActivity(this, intent, R.anim.left_in, R.anim.left_out, true, true);
                break;

            case R.id.linearlayout_icon_back:
                onBackPressed();
                break;

            case R.id.linearlayout_icon_list:
                Intent intentToList = new Intent(this, ArticleListAct.class);
                Integer categoryCode = Integer.parseInt(currentArticle.articleId.toString().substring(0, 2));
                intentToList.putExtra("mainImagesPosition", categoryCode);
                intentToList.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentToList.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MoveActUtil.moveActivity(this, intentToList, R.anim.left_in, R.anim.left_out, true, true);
                break;

            case R.id.linearlayout_icon_top:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DebugUtil.showDebug("wv :: " + wv.getHeight());
                        scrollViewArticleDetail.smoothScrollTo(0, 0);
                    }
                }, 300);
                break;

            case R.id.speaker:
                DebugUtil.showDebug("재생합니다 ");
                //Todo ArticleDetailAct -> 스피커 재생시 반복해서 눌렀을 때 눌리지 않도록 재생이 완료될 때에 눌릴 수 있도록 할 것


                if (canPlay) {
                    canPlay = false;

                    mPlayer = new MediaPlayer();

                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    linearLayoutSpeaker.setBackgroundResource(R.drawable.speaker12);

                    try {
                        AssetFileDescriptor afd = this.getAssets().openFd("SOS_morse_code.mp3");
                        mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                DebugUtil.showDebug("ggggg");
                                new Handler().post(new Runnable() {
                                    @Override
                                    public void run() {
                                        linearLayoutSpeaker.setBackgroundResource(R.drawable.speaker11);
                                        canPlay = true;
                                    }
                                });
                            }
                        });
                        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                if (!mp.isPlaying())
                                    mp.start();
                            }
                        });
                        mPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;

            case R.id.img_having_checklist:
                DebugUtil.showDebug("ArticleDetailAct -> 체크리스트 import 화면 ");
                DebugUtil.showDebug("currentArticle.size :: " + currentArticle.articleId);
                Intent moveToArticleDetailCheckListImportAct = new Intent(this, ArticleDetailCheckListImportAct.class);
                moveToArticleDetailCheckListImportAct.putExtra("article Infos", currentArticle);
                MoveActUtil.moveActivity(this, moveToArticleDetailCheckListImportAct, -1, -1, false, false);
                break;
        }
    }


}
