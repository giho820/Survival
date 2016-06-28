package com.survivalsos.goldentime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.common.view.NanumGothicTextView;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.CheckList;
import com.survivalsos.goldentime.util.TextUtil;

import java.util.ArrayList;

/**
 * Created by kiho on 2016. 6. 20..
 */
public class CheckListAllRecyclerAdapter extends RecyclerView.Adapter {

    private CheckList item;
    private ArrayList<CheckList> items;
    private static Context context;
    private AdapterItemClickListener adapterItemClickListener;

//    //체크리스트 카테고리명이 나오는 헤더영역
//    public static class CheckListAllHeaderViewHolder extends RecyclerView.ViewHolder {
//
//        public NanumGothicTextView tvHeader;
//
//        public CheckListAllHeaderViewHolder(View v) {
//            super(v);
//            tvHeader = (NanumGothicTextView) v.findViewById(R.id.tv_checklist_all_header);
//        }
//    }

    //체크리스트 내용
    public static class CheckListAllContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout linearLayoutCheckListAllHeader;
        private NanumGothicTextView nanumGothicTextViewCheckListHeader;
        private LinearLayout itemLineChecklistHeader;
        private LinearLayout clickArea;
        private TextView titleOfItem;
        private LinearLayout checklistAllTrashClickArea;
        //private TextView label;
        //private ImageView ImgLocker;

        private AdapterItemClickListener itemClickListener;

        public CheckListAllContentViewHolder(View v) {
            super(v);

            linearLayoutCheckListAllHeader = (LinearLayout) v.findViewById(R.id.linearlayout_checklist_all_header);
            nanumGothicTextViewCheckListHeader = (NanumGothicTextView) v.findViewById(R.id.tv_checklist_title_header);
            itemLineChecklistHeader = (LinearLayout) v.findViewById(R.id.item_line_checklist_header);

            clickArea = (LinearLayout) v.findViewById(R.id.item_checklist_all_checkbox_click_area);
            titleOfItem = (TextView) v.findViewById(R.id.tv_checklist_title);
            checklistAllTrashClickArea = (LinearLayout) v.findViewById(R.id.linearlayout_checklist_all_trash);
            clickArea.setOnClickListener(this);
            checklistAllTrashClickArea.setOnClickListener(this);
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
    public CheckListAllRecyclerAdapter(Context context) {
        this.context = context;
    }

    public void setAdapterItemClickListener(AdapterItemClickListener adapterItemClickListener) {
        this.adapterItemClickListener = adapterItemClickListener;
    }

    // Todo Integer -> Item
    public void setItem(CheckList item) {
        this.item = item;
    }


    public void setAdapterArrayList(ArrayList<CheckList> adapterArrayList) {
        this.items = adapterArrayList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checklist_all_list, parent, false);
        RecyclerView.ViewHolder vh = new CheckListAllContentViewHolder(v);
        ((CheckListAllContentViewHolder) vh).setClickListener(adapterItemClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        item = items.get(position);

        //이상한 값이 뷰에서 재사용되지 않도록 초기화하기
        ((CheckListAllContentViewHolder) holder).linearLayoutCheckListAllHeader.setVisibility(View.GONE);
        ((CheckListAllContentViewHolder) holder).nanumGothicTextViewCheckListHeader.setText("");
        ((CheckListAllContentViewHolder) holder).clickArea.setVisibility(View.INVISIBLE);
        ((CheckListAllContentViewHolder) holder).titleOfItem.setText("");

        if (holder instanceof CheckListAllContentViewHolder) {
            if (item != null) {

                ((CheckListAllContentViewHolder) holder).titleOfItem.setText(item.title);

                if (item.isInMyList == Definitions.CHECK_BOX_CHECKED.CHECKED)
                    ((CheckListAllContentViewHolder) holder).clickArea.setVisibility(View.VISIBLE);
                else
                    ((CheckListAllContentViewHolder) holder).clickArea.setVisibility(View.INVISIBLE);


                if (item.isHeader) {
                    ((CheckListAllContentViewHolder) holder).itemLineChecklistHeader.setVisibility(View.VISIBLE);
                    ((CheckListAllContentViewHolder) holder).linearLayoutCheckListAllHeader.setVisibility(View.VISIBLE);

                    if (!TextUtil.isNull(item.categoryName)) {
                        ((CheckListAllContentViewHolder) holder).nanumGothicTextViewCheckListHeader.setText(item.categoryName);
                    }
                } else {
                    //Todo 체크 여부 디비에서 검사해서 표시해주기
                    ((CheckListAllContentViewHolder) holder).linearLayoutCheckListAllHeader.setVisibility(View.GONE);
                    ((CheckListAllContentViewHolder) holder).itemLineChecklistHeader.setVisibility(View.GONE);
                }
            }
        } else {
            //Todo 뷰홀더 2개 쓰지 말고 header visible/invisible로 변경하자
        }
    }

    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (items != null) {
            itemCount = items.size();
        }
        return itemCount;
    }
//
//    @Override
//    public int getItemViewType(int position) {
//        if (items == null) {
//            return VIEW_HEADER;
//        }
//        int viewType;
//
//        if (items.get(position).isHeader) {
//            viewType = VIEW_HEADER;
//        } else {//if (mainImageItems.get(position) != null) {
//            viewType = VIEW_ITEM;
//        }
//        return viewType;
//    }
}
