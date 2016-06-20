package com.survivalsos.goldentime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.util.ImageUtil;

import java.util.ArrayList;

/**
 * Created by kiho on 2016. 6. 20..
 */
public class MainImageRecyclerAdapter extends RecyclerView.Adapter {

    //Todo mainItem(이미지 파일이름, 텍스트, 버튼, 유료여부)로 포함하는 자료형 만들어서 구성할 것
    private Integer item;
    private ArrayList<Integer> mainImageNames;
    private static Context context;
    private AdapterItemClickListener adapterItemClickListener;

    public static class MainImgViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mainImg;
        //private TextView label;
        //private Button arrowBtnAtRightSide;
        //private ImageView ImgLocker;

        private AdapterItemClickListener itemClickListener;

        public MainImgViewHolder(View v) {
            super(v);

            mainImg = (ImageView) v.findViewById(R.id.item_iv_main);
            mainImg.setOnClickListener(this);
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
    public MainImageRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setAdapterItemClickListener(AdapterItemClickListener adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    // Todo Integer -> Item
    public void setItem(Integer item) {
        this.item = item;
    }


    public void setAdapterArrayList(ArrayList<Integer> adapterArrayList) {
        this.mainImageNames = adapterArrayList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_image, parent, false);
        RecyclerView.ViewHolder vh = new MainImgViewHolder(v);
        ((MainImgViewHolder) vh).setClickListener(adapterItemClickListener);//Todo 아직도 이해가 부족하네  그냥 넘어가지 말자
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        item = mainImageNames.get(position);

        if (holder instanceof MainImgViewHolder) {
            ((MainImgViewHolder) holder).mainImg.setImageBitmap(null);

            if (item != null) {
//                if (!ImageLoader.getInstance().isInited()) {
//                    ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
//                }
                ((MainImgViewHolder) holder).mainImg.setImageDrawable(ImageUtil.loadDrawableFromAssets(context, "image/" + item + ".png"));
//                ImageLoader.getInstance().displayImage(item.getPath(), ((MatchImgViewHolder) holder).matchingImg);
            }

        }
    }

    @Override
    public int getItemCount() {
        int itemCout = 0;
        if(mainImageNames != null) {
            itemCout = mainImageNames.size();
        }
        return itemCout;
    }
}
