package com.survivalsos.goldentime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ParentAct;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.adapter.SearchResultActImageAdapter;
import com.survivalsos.goldentime.common.view.ExpandableHeightGridView;
import com.survivalsos.goldentime.common.view.textview.NanumBarunGothicEditView;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.MoveActUtil;
import com.survivalsos.goldentime.util.TextUtil;

import java.util.ArrayList;

public class SearchAct extends ParentAct implements View.OnClickListener, TextView.OnEditorActionListener {


    NanumBarunGothicEditView nanumGothicEditView;
    LinearLayout linearLayoutBackBtn;

    private Article clickedArticle;
    ArrayList<Article> searchResult;
    private SearchResultActImageAdapter keywordSearchResultimageAdapter;
    private ExpandableHeightGridView gridViewKeywordSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchResult = new ArrayList<>();

        linearLayoutBackBtn = (LinearLayout) findViewById(R.id.linearlayout_exit_search_act);
        linearLayoutBackBtn.setOnClickListener(this);

        nanumGothicEditView = (NanumBarunGothicEditView) findViewById(R.id.edittext_input_search);
        nanumGothicEditView.setOnEditorActionListener(this);

        gridViewKeywordSearch = (ExpandableHeightGridView) findViewById(R.id.gridViewKeywordSearch);
        gridViewKeywordSearch.setNumColumns(2);
        gridViewKeywordSearch.setExpanded(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout_exit_search_act:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //Todo 에니메이션 효과 물어볼 것
//        overridePendingTransition(R.anim.up, R.anim.down);
        super.onBackPressed();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        try{
            if (actionId == EditorInfo.IME_ACTION_SEARCH){

                hiddenKeyboard();

                if (!TextUtil.isNull(v.getText().toString())) {
                    String inputText = v.getText().toString();
                    searchResult = DatabaseCRUD.getSearchList(inputText);

                    String result = "";
                    for (Article article : searchResult) {
                        result += article.articleId + ", " + article.title;
                    }

                    if (searchResult != null && searchResult.size() > 0) {
                        keywordSearchResultimageAdapter = new SearchResultActImageAdapter(SearchAct.this, searchResult);
                        DebugUtil.showDebug("keywordSearchResultimageAdapter : " + keywordSearchResultimageAdapter.getCount());
                        keywordSearchResultimageAdapter.setAdapterItemClickListener(new AdapterItemClickListener() {
                            @Override
                            public void onAdapterItemClick(View view, int position) {
                                clickedArticle = keywordSearchResultimageAdapter.getItem(position);
//                        switch (view.getId()){
//                            case R.id.item_list_framelayout:
                                //Todo 이미지 클릭해서 아티클 보여주는 부분
                                DebugUtil.showDebug("item :: " + clickedArticle.articleId + "클릭 됨");
                                Intent moveToArticleDetailAct = new Intent(SearchAct.this, ArticleDetailAct.class);
                                moveToArticleDetailAct.putExtra("articleId ArticleListAct To DetailAct", clickedArticle);
                                MoveActUtil.moveActivity(SearchAct.this, moveToArticleDetailAct, R.anim.right_in, R.anim.right_out, false, true);
//                                break;
//                        }
                            }
                        });
                        gridViewKeywordSearch.setAdapter(keywordSearchResultimageAdapter);
                    }
                }
            }
        } catch (Exception e) {
            e.getMessage();
            e.getStackTrace();
        }

        return false;//Todo 이 리턴 값은 어떤 걸 의미할까?
    }
}
