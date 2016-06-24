package com.survivalsos.goldentime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.ParentAct;
import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.adapter.AdditionalArticleListAdapter;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.MoveActUtil;

import java.util.ArrayList;

public class ArticleDetailAct extends ParentAct
        implements NavigationView.OnNavigationItemSelectedListener, AdapterItemClickListener, View.OnClickListener {

    private Article currentArticle;
    private Article additionalArticle;

    private LinearLayout linearLayoutOpenDrawer;
    private ScrollView scrollViewArticleDetail;
    private WebView wv;
    private ArrayList<Article> additionalArticles;
    private ListView listViewContainingAdditionalArticle;
    private AdditionalArticleListAdapter additionalArticleListAdapter;
    private LinearLayout linearLayoutIconHome;
    private LinearLayout linearLayoutIconBack;
    private LinearLayout linearLayoutIconGuide;
    private LinearLayout linearLayoutIconTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail_with_navigation);

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

        linearLayoutOpenDrawer = (LinearLayout) findViewById(R.id.linearlayout_open_drawer_article_detail);
        linearLayoutOpenDrawer.setOnClickListener(this);

        //스크롤뷰 영역

        scrollViewArticleDetail = (ScrollView) findViewById(R.id.scrollViewArticleDetail);
        wv = (WebView) findViewById(R.id.webView_article_detail);
        listViewContainingAdditionalArticle = (ListView) findViewById(R.id.article_detail_listview);
        linearLayoutIconHome = (LinearLayout) findViewById(R.id.linearlayout_icon_home);
        linearLayoutIconBack = (LinearLayout) findViewById(R.id.linearlayout_icon_back);
        linearLayoutIconGuide = (LinearLayout) findViewById(R.id.linearlayout_icon_list);
        linearLayoutIconTop = (LinearLayout) findViewById(R.id.linearlayout_icon_top);


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
                DebugUtil.showDebug("Article ID :: " + currentArticle.articleId);
                String filePath = "file:///android_asset/html/" + currentArticle.articleId + ".html";
                wv.loadUrl(filePath);

                if (currentArticle.relatedArticleId > 0) {
                    Article additionalArticle = new Article();
                    DebugUtil.showDebug("해당 아티클과 연관된 아티클이 아이디가 존재함 :: " + currentArticle.relatedArticleId);
                    additionalArticle.articleId = currentArticle.relatedArticleId;
//                    additionalArticle.title = DatabaseCRUD.getArticleTitle(currentArticle.relatedArticleId);
                    additionalArticle = DatabaseCRUD.getArticleInfo(currentArticle.relatedArticleId);
                    additionalArticle.type = Definitions.ARTICLE_TYPE.RELATED;
                    additionalArticles.add(additionalArticle);
                }

                if (currentArticle.nextArticleId > 0) {
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
            }
        }
        additionalArticleListAdapter.setAdapterArrayList(additionalArticles);
        listViewContainingAdditionalArticle.setAdapter(additionalArticleListAdapter);
        setListViewHeightBasedOnChildren(listViewContainingAdditionalArticle);

        linearLayoutIconHome.setOnClickListener(this);
        linearLayoutIconBack.setOnClickListener(this);
        linearLayoutIconGuide.setOnClickListener(this);
        linearLayoutIconTop.setOnClickListener(this);
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
                Intent moveToArticleDetailAct = new Intent(this, ArticleDetailAct.class);
                moveToArticleDetailAct.putExtra("articleId ArticleListAct To DetailAct", additionalArticle);
                MoveActUtil.moveActivity(this, moveToArticleDetailAct, R.anim.right_in, R.anim.right_out, false, true);
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
        }
    }


}
