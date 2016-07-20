package com.survivalsos.goldentime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.ParentAct;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.adapter.SearchResultActImageAdapter;
import com.survivalsos.goldentime.common.view.ExpandableHeightGridView;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.MoveActUtil;

import java.util.ArrayList;

public class BookmarkAct extends ParentAct implements View.OnClickListener {

    LinearLayout linearLayoutBackBtn;

    private Article clickedArticle;
    ArrayList<Article> searchResult;
    private SearchResultActImageAdapter keywordBookmarkResultimageAdapter;
    private ExpandableHeightGridView gridViewKeywordBookmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        linearLayoutBackBtn = (LinearLayout) findViewById(R.id.linearlayout_exit_bookmark_act);
        linearLayoutBackBtn.setOnClickListener(this);

        gridViewKeywordBookmark = (ExpandableHeightGridView) findViewById(R.id.gridViewBookmark);
        gridViewKeywordBookmark.setNumColumns(2);
        gridViewKeywordBookmark.setExpanded(true);

        searchResult = DatabaseCRUD.getBookmarkArticlesFromDB();
        searchResult = DatabaseCRUD.getCompletedArticlesUsingBookmarkTable(searchResult);

        if (searchResult != null && searchResult.size() >= 0) {
            keywordBookmarkResultimageAdapter = new SearchResultActImageAdapter(BookmarkAct.this, searchResult);
            DebugUtil.showDebug("keywordSearchResultimageAdapter : " + keywordBookmarkResultimageAdapter.getCount());
            keywordBookmarkResultimageAdapter.setAdapterItemClickListener(new AdapterItemClickListener() {
                @Override
                public void onAdapterItemClick(View view, int position) {
                    clickedArticle = keywordBookmarkResultimageAdapter.getItem(position);

                    //Todo 이미지 클릭해서 아티클 보여주는 부분
                    DebugUtil.showDebug("item :: " + clickedArticle.articleId + " 클릭 됨");
                    Intent moveToArticleDetailAct = new Intent(BookmarkAct.this, ArticleDetailAct.class);
                    moveToArticleDetailAct.putExtra("articleId ArticleListAct To DetailAct", clickedArticle);
                    MoveActUtil.moveActivity(BookmarkAct.this, moveToArticleDetailAct, R.anim.right_in, R.anim.right_out, false, true);
                }
            });
            gridViewKeywordBookmark.setAdapter(keywordBookmarkResultimageAdapter);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout_exit_bookmark_act:
                onBackPressed();
                break;


        }
    }
}
