package com.survivalsos.goldentime.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.database.DatabaseHelper;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.ImageUtil;

import java.util.ArrayList;

public class ArticleListAct extends AppCompatActivity {

    private DatabaseHelper databaseHelper;
    private ArrayList<Article> articles;
    private Integer clickedImagePosition;

    private ImageView imageViewRepresentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        DebugUtil.showDebug("ArticleListAct :: onCreate");
        imageViewRepresentImage = (ImageView) findViewById(R.id.img_representing_article);

        clickedImagePosition = getIntent().getIntExtra("mainImagesPosition", 0);
        DebugUtil.showDebug("넘어온 아키틀 번호 :: " + clickedImagePosition);
        if (clickedImagePosition == 0) {
            DebugUtil.showDebug("넘어온 값 없음... clickedImagePosition :: 0");
            //Todo 값이 없는 경우 어떻게 처리할지 고민해볼 것, 널 객체를 리턴할지, null을 리턴할지, 내가 만든 exception을 throw할지
            return;
        }

        articles = DatabaseCRUD.getArticleList(clickedImagePosition);
        if (articles != null) {
            DebugUtil.showDebug("기사의 개수는 :: " + articles.size());
        }

        imageViewRepresentImage.setImageDrawable(ImageUtil.loadDrawableFromAssets(ArticleListAct.this, "image/ArticleListImages/" + clickedImagePosition + "00.png"));
    }
}
