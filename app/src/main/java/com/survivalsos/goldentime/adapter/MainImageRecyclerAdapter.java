package com.survivalsos.goldentime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.common.view.LatoBlackTextView;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.MainImageItemInfo;
import com.survivalsos.goldentime.util.TextUtil;

import java.util.ArrayList;

/**
 * Created by kiho on 2016. 6. 20..
 */
public class MainImageRecyclerAdapter extends RecyclerView.Adapter {

    private final int VIEW_HEADER = 0;//자연 재해인지 사고 ・ 화재인지 알려주는 헤더
    private final int VIEW_ITEM = 1; //그에 따른 이미지 영역

    private MainImageItemInfo item;
    private ArrayList<MainImageItemInfo> mainImageItems;
    private static Context context;
    private Integer argPage;
    private AdapterItemClickListener adapterItemClickListener;


    //헤더 영역
    public static class MainHeaderViewHolder extends RecyclerView.ViewHolder {

        public LatoBlackTextView tvHeader;

        public MainHeaderViewHolder(View v) {
            super(v);
            tvHeader = (LatoBlackTextView) v.findViewById(R.id.tv_main_header);
        }
    }

    //이미지 영역 뷰홀더
    public static class MainImgViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mainImg;
        private ImageView imgLocker;
        private LatoBlackTextView label;

        private AdapterItemClickListener itemClickListener;

        public MainImgViewHolder(View v) {
            super(v);

            mainImg = (ImageView) v.findViewById(R.id.item_iv_main);
            mainImg.setOnClickListener(this);
            imgLocker = (ImageView) v.findViewById(R.id.item_iv_locked_image);
            label = (LatoBlackTextView) v.findViewById(R.id.item_tv_image_title);

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
    public MainImageRecyclerAdapter(Context context, Integer argPage) {
        this.context = context;
        this.argPage = argPage;
    }

    public void setAdapterItemClickListener(AdapterItemClickListener adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    public void setItem(MainImageItemInfo item) {
        this.item = item;
    }


    public void setAdapterArrayList(ArrayList<MainImageItemInfo> adapterArrayList) {
        this.mainImageItems = adapterArrayList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh;
        View v;

        switch (viewType) {
//            case VIEW_HEADER:
//                v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_header, parent, false);
//                vh = new  MainHeaderViewHolder(v);
//                break;

            case VIEW_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_image, parent, false);
                vh = new MainImgViewHolder(v);
                ((MainImgViewHolder) vh).setClickListener(adapterItemClickListener);
                break;

            default:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_header, parent, false);
                vh = new MainHeaderViewHolder(v);
                break;
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MainImgViewHolder) {
            item = mainImageItems.get(position);

            if (item != null && holder instanceof MainImgViewHolder) {
                ((MainImgViewHolder) holder).mainImg.setImageBitmap(null);
                if (item.mainImageCode != null){
//                    ((MainImgViewHolder) holder).mainImg.setImageDrawable(ImageUtil.loadDrawableFromAssets(context, "image/" + item.mainImageCode + ".png"));
                    Picasso.with(context).load( "file:///android_asset/image/"  + item.mainImageCode + ".png").into(((MainImgViewHolder) holder).mainImg);
                }

                if (item.doesLocked != null) {
                    if (item.doesLocked > 0) {
                        ((MainImgViewHolder) holder).imgLocker.setVisibility(View.VISIBLE);
                    } else {
                        ((MainImgViewHolder) holder).imgLocker.setVisibility(View.INVISIBLE);
                    }
                }

                ((MainImgViewHolder) holder).label.setText("");
                if (!TextUtil.isNull(item.mainImageName)) {
                    ((MainImgViewHolder) holder).label.setText(item.mainImageName);
                }

            }
        } else { //if (holder instance of MainHeaderViewHolder)

            //Todo 여기만 다른데 fragment만 구분해서하나의 어댑터로 처리하는 방법으로 해야할 것 같은데..
            if(argPage == 0) {
                if (position == 0)
                    ((MainHeaderViewHolder) holder).tvHeader.setText("자연 재해");

                int secondHeaderPos = DatabaseCRUD.getMainImageItemInfoFromAssetFolder(Definitions.SECTION_TYPE.NATURE_DISASTER).size() + 1;
                if (position == secondHeaderPos)
                    ((MainHeaderViewHolder) holder).tvHeader.setText("사고 ・ 화재");
            } else {
                if (position == 0)
                    ((MainHeaderViewHolder) holder).tvHeader.setText("생존 원칙과 방법");

                int secondHeaderPos = DatabaseCRUD.getMainImageItemInfoFromAssetFolder(Definitions.SECTION_TYPE.SURVIVAL_PRINCIPLE).size() + 1;
                if (position == secondHeaderPos)
                    ((MainHeaderViewHolder) holder).tvHeader.setText("응급상황 대비");
            }
        }

    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (mainImageItems != null) {
            itemCount = mainImageItems.size();
        }
        return itemCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mainImageItems == null) {
            return VIEW_HEADER;
        }

        int viewType;

        if (mainImageItems.get(position).mainImageName == null) {
            viewType = VIEW_HEADER;
        } else {//if (mainImageItems.get(position) != null) {
            viewType = VIEW_ITEM;
        }

        return viewType;

    }
}
