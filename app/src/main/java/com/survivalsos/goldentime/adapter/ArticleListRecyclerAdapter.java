package com.survivalsos.goldentime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.util.DebugUtil;

import java.util.ArrayList;

/**
 * Created by kiho on 2016. 6. 20..
 */
public class ArticleListRecyclerAdapter extends RecyclerView.Adapter {

    private Article item;
    private ArrayList<Article> items;
    private static Context context;
    private AdapterItemClickListener adapterItemClickListener;

    public static class ArticleListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout clickArea;
        private TextView titleOfArticle;
        private ImageView arrowBtnAtRightSide;
        //private TextView label;
        //private ImageView ImgLocker;

        private AdapterItemClickListener itemClickListener;

        public ArticleListViewHolder(View v) {
            super(v);

            clickArea = (LinearLayout) v.findViewById(R.id.item_article_list_click_area);
            titleOfArticle = (TextView) v.findViewById(R.id.textview_title);
            arrowBtnAtRightSide = (ImageView) v.findViewById(R.id.item_article_list_rightside_arrow);
            clickArea.setOnClickListener(this);
        }

        public void setClickListener(AdapterItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            if (itemClickListener != null)
                itemClickListener.onAdapterItemClick(v, getPosition());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ArticleListRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setAdapterItemClickListener(AdapterItemClickListener adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    // Todo Integer -> Item
    public void setItem(Article item) {
        this.item = item;
    }


    public void setAdapterArrayList(ArrayList<Article> adapterArrayList) {
        this.items = adapterArrayList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article_list, parent, false);
        RecyclerView.ViewHolder vh = new ArticleListViewHolder(v);
        ((ArticleListViewHolder) vh).setClickListener(adapterItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        item = items.get(position);

        if (holder instanceof ArticleListViewHolder) {
//            ((ArticleListViewHolder) holder).titleOfArticle.setText("");

            if (item != null) {
//                if (!ImageLoader.getInstance().isInited()) {
//                    ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
//                }
                ((ArticleListViewHolder) holder).titleOfArticle.setText(item.title);
                DebugUtil.showDebug("ArticleListRecyclerAdapter :: " + item.title);
//                ImageLoader.getInstance().displayImage(item.getPath(), ((MatchImgViewHolder) holder).matchingImg);
            }
        }
    }

    @Override
    public int getItemCount() {
        int itemCout = 0;
        if(items != null) {
            itemCout = items.size();
        }
        return itemCout;
    }
}
