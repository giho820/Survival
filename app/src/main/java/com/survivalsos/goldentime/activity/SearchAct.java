package com.survivalsos.goldentime.activity;

import android.os.Bundle;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ParentAct;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.common.view.NanumGothicEditView;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.util.TextUtil;

import java.util.ArrayList;

public class SearchAct extends ParentAct implements View.OnClickListener, TextView.OnEditorActionListener {


    NanumGothicEditView nanumGothicEditView;
    private TextWatcher textWatcher;
    LinearLayout linearLayoutBackBtn;
    TextView tvSearchTest;

    ArrayList<Article> searchResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchResult = new ArrayList<>();

        linearLayoutBackBtn = (LinearLayout) findViewById(R.id.linearlayout_exit_search_act);
        linearLayoutBackBtn.setOnClickListener(this);
        tvSearchTest = (TextView) findViewById(R.id.tv_search_test);

        nanumGothicEditView = (NanumGothicEditView) findViewById(R.id.edittext_input_search);
        nanumGothicEditView.setOnEditorActionListener(this);
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
        if (!TextUtil.isNull(v.getText().toString())) {
            String inputText = v.getText().toString();
            searchResult = DatabaseCRUD.getSearchList(inputText);

            String result = "";
            for (Article article : searchResult) {
                result += article.articleId + ", " + article.title;
            }
            tvSearchTest.setText(searchResult.size() + "//" + result);
        }
        return false;
    }
}
