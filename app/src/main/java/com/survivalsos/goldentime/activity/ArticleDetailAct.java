package com.survivalsos.goldentime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListAdapter;
import android.widget.ListView;

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

public class ArticleDetailAct extends ParentAct implements AdapterItemClickListener {

    private WebView wv;
    private Article currentArticle;
    private Article additionalArticle;
    private ArrayList<Article> additionalArticles;
    private ListView listViewContainingAdditionalArticle;
    private AdditionalArticleListAdapter additionalArticleListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        wv = (WebView) findViewById(R.id.webView_article_detail);
        listViewContainingAdditionalArticle = (ListView) findViewById(R.id.article_detail_listview);

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
                    DebugUtil.showDebug("집에가기전에 마지막 쳌 :: 어댑터에 있는 항목의 개수 ::: " + additionalArticleListAdapter.getCount());
                }
            }
        }
        additionalArticleListAdapter.setAdapterArrayList(additionalArticles);
        listViewContainingAdditionalArticle.setAdapter(additionalArticleListAdapter);
        setListViewHeightBasedOnChildren(listViewContainingAdditionalArticle);
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    //Todo 스크롤뷰 안에 있는 리스트뷰의 항목이 모두 보이도록하는 방법 잘되면 ParentAct로 옮기도록 하자
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        DebugUtil.showDebug("setListViewHeightBasedOnChildren() :: " + params.height);
        listView.setLayoutParams(params);
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
}
