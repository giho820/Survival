package com.survivalsos.goldentime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.adapter.ArticleListRecyclerAdapter;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.ImageUtil;
import com.survivalsos.goldentime.util.MoveActUtil;

import java.util.ArrayList;

public class ArticleListAct extends AppCompatActivity {

    private ArrayList<Article> articles;
    private RecyclerView articleListRecyclerView;
    private ArticleListRecyclerAdapter articleListRecyclerAdapter;

    private Integer clickedImagePosition;
    private ImageView imageViewRepresentImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
        DebugUtil.showDebug("ArticleListAct :: onCreate");

        clickedImagePosition = getIntent().getIntExtra("mainImagesPosition", 0);
        DebugUtil.showDebug("넘어온 아키틀 번호 :: " + clickedImagePosition);

        imageViewRepresentImage = (ImageView) findViewById(R.id.img_representing_article);

        articleListRecyclerView = (RecyclerView) findViewById(R.id.article_list_listview_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        articleListRecyclerView.setLayoutManager(linearLayoutManager);

        articleListRecyclerAdapter = new ArticleListRecyclerAdapter(this);
        articleListRecyclerAdapter.setAdapterItemClickListener(new AdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, int position) {
                DebugUtil.showDebug("ArticleListAct :: " + articles.get(position).title + " 클릭 됨 ");
                Intent moveToArticleDetailAct = new Intent(ArticleListAct.this, ArticleDetailAct.class);
                moveToArticleDetailAct.putExtra("articleId ArticleListAct To DetailAct", articles.get(position));
                MoveActUtil.moveActivity(ArticleListAct.this, moveToArticleDetailAct, R.anim.right_in, R.anim.right_out, false, true);
            }
        });
        articleListRecyclerView.setAdapter(articleListRecyclerAdapter);


        if (clickedImagePosition == 0) {
            DebugUtil.showDebug("넘어온 값 없음... clickedImagePosition :: 0");
            //Todo 값이 없는 경우 어떻게 처리할지 고민해볼 것, 널 객체를 리턴할지, null을 리턴할지, 내가 만든 exception을 throw할지
            return;
        } else if (clickedImagePosition != 0) {
            articles = DatabaseCRUD.getArticleList(clickedImagePosition);
            if (articles != null) {
                DebugUtil.showDebug("기사의 개수는 :: " + articles.size());

                articleListRecyclerAdapter.setAdapterArrayList(articles);
                articleListRecyclerAdapter.notifyDataSetChanged();
            }


            imageViewRepresentImage.setImageDrawable(ImageUtil.loadDrawableFromAssets(ArticleListAct.this, "image/ArticleListImages/" + clickedImagePosition + "00.png"));


        }

    }
}
