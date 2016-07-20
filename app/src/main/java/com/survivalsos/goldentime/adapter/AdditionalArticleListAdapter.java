package com.survivalsos.goldentime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.TextUtil;

import java.util.ArrayList;

/**
 * Created by preparkha on 15. 6. 24..
 */
public class AdditionalArticleListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<Article> adapterArrayList;
    private AdapterItemClickListener adapterItemClickListener;

    public AdditionalArticleListAdapter(Context context) {
        this.context = context;
    }

    public void setAdapterItemClickListener(AdapterItemClickListener listener) {
        this.adapterItemClickListener = listener;
    }

    public ArrayList<Article> getAdapterArrayList() {
        return adapterArrayList;
    }

    public void setAdapterArrayList(ArrayList<Article> adapterArrayList) {
        this.adapterArrayList = adapterArrayList;
        this.notifyDataSetChanged();
    }

    public void addItem(Article article) {
        if (article == null)
            return;
        if (this.adapterArrayList != null)
            this.adapterArrayList.add(article);
    }

    public void addItems(ArrayList<Article> items) {
        if (this.adapterArrayList == null)
            this.adapterArrayList = new ArrayList<>();
        this.adapterArrayList.removeAll(this.adapterArrayList);
        this.adapterArrayList.addAll(items);
        notifyDataSetChanged();
    }

    public void clearAll() {
        if (adapterArrayList != null)
            this.adapterArrayList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (this.adapterArrayList != null && this.adapterArrayList.size() > 0)
            return this.adapterArrayList.size();
        return 0;
    }

    @Override
    public Article getItem(int position) {
        if (this.adapterArrayList != null && this.adapterArrayList.size() > 0)
            return this.adapterArrayList.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        Article item;

        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_additional_article_list, null);
            viewHolder = new ViewHolder();
            viewHolder.linearLayoutClickSection = (LinearLayout) convertView.findViewById(R.id.item_additional_click_section);
            viewHolder.linearLayoutClickSection.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DebugUtil.showDebug("clicked......;;;;;;");
                    if (adapterItemClickListener != null) {
                        adapterItemClickListener.onAdapterItemClick(v, position);
                    }
                }
            });
            viewHolder.tvLabelNextOrRelated = (ImageView) convertView.findViewById(R.id.item_tv_nextOrRelated);
            viewHolder.tvTitleOfAdditionalItem = (TextView) convertView.findViewById(R.id.titleOfAdditionalArticle);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }



        viewHolder.linearLayoutClickSection.setVisibility(View.GONE);

        item = adapterArrayList.get(position);
        if (item != null) {
            if (item.articleId != null && item.articleId > 0) {
                if (item.type == Definitions.ARTICLE_TYPE.NEXT){
                    viewHolder.linearLayoutClickSection.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(R.drawable.icon_additional_next).resizeDimen(R.dimen.dp_36, R.dimen.dp_10).into(viewHolder.tvLabelNextOrRelated);
                }

                else if (item.type == Definitions.ARTICLE_TYPE.RELATED) {
                    viewHolder.linearLayoutClickSection.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(R.drawable.icon_additional_related).resizeDimen(R.dimen.dp_62, R.dimen.dp_10).into(viewHolder.tvLabelNextOrRelated);
                }
            }
        } else {
            DebugUtil.showDebug("AdditionalArticleListAdapter, item is null");
            return null;
        }

        if (!TextUtil.isNull(item.title)) {
            viewHolder.tvTitleOfAdditionalItem.setText(item.title);
        }

        return convertView;
    }

    public class ViewHolder {
        LinearLayout linearLayoutClickSection;
        ImageView tvLabelNextOrRelated;
        TextView tvTitleOfAdditionalItem;
    }
}
