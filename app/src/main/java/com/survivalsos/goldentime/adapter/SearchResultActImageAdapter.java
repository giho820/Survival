package com.survivalsos.goldentime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.common.view.NanumGothicBoldTextView;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.util.DebugUtil;
import com.survivalsos.goldentime.util.ImageUtil;
import com.survivalsos.goldentime.util.TextUtil;

import java.util.ArrayList;


/**
 * 이미지 어댑터
 */
public class SearchResultActImageAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<Article> items;
    public LayoutInflater inflater;
    private AdapterItemClickListener adapterItemClickListener;

    public SearchResultActImageAdapter(Context context, ArrayList<Article> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);

        if (!ImageLoader.getInstance().isInited())
            ImageLoader.getInstance().init(ImageUtil.GlobalImageLoaderConfiguration(context));
    }

    public void setAdapterItemClickListener(AdapterItemClickListener adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }


    public void setItems(ArrayList<Article> items) {
        DebugUtil.showDebug("setItems() in imageAdapter");
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Article getItem(int position) {
        if (items != null && items.size() > position) {
            return items.get(position);
        } else
            return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new ViewHolder();

            DebugUtil.showDebug("FolderFrag, getView(), converView == null");
            convertView = inflater.inflate(R.layout.item_list_search_result, null);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView_article_thumbnail);
            holder.image.setBackgroundColor(context.getResources().getColor(R.color.c_ff222222));

            holder.articleName = (NanumGothicBoldTextView) convertView.findViewById(R.id.item_list_article_name);

            int w = ImageUtil.getDeviceWidth(context);
            int widthBetweenImages = context.getResources().getDimensionPixelSize(R.dimen.dp_2);

            convertView.setLayoutParams(new AbsListView.LayoutParams(w / 2 - widthBetweenImages, w / 2 - widthBetweenImages));
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        //개별 이미지 클릭 리스너
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DebugUtil.showDebug("CategoryFrag, ImageAdapter, getView, onClick() selected position : " + position);
                if(adapterItemClickListener != null)
                    adapterItemClickListener.onAdapterItemClick(v, position);

            }
        });



        final Article item = getItem(position);
//        ImageLoader.getInstance().displayImage("drawable://" + R.drawable.act_main_image_default, holder.image);
        convertView.setVisibility(View.GONE);
        holder.articleName.setText("");

        if (item != null) {
            if (!TextUtil.isNull(item.title)) {

                if (item.articleId > 0){
//                    ((MainImgViewHolder) holder).mainImg.setImageDrawable(ImageUtil.loadDrawableFromAssets(context, "image/" + item.mainImageCode + ".png"));
                    String filePath = "file:///android_asset/html/img/"  + item.articleId + "1.jpg";

                    convertView.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(filePath).fit().into(holder.image, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            String newfilePath = "file:///android_asset/html/img/"  + item.articleId + "1.png";
                            Picasso.with(context).load(newfilePath).into(holder.image);
                        }
                    });

//                    if(ImageLoader.getInstance().getDiskCache().get(filePath) != null) {
//                        ImageLoader.getInstance().displayImage(filePath, holder.image);
//                    } else {
//                        ImageLoader.getInstance().displayImage(ImageLoader.getInstance().getDiskCache().get(filePath).getAbsolutePath(), holder.image);
//                    }


                    holder.articleName.setText(item.title);

                }
            }
        }

        return convertView;
    }

    class ViewHolder {
        ImageView image;
        NanumGothicBoldTextView articleName;
    }
}

