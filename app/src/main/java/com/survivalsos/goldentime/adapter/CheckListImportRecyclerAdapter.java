package com.survivalsos.goldentime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.common.view.textview.NanumBarunGothicTextView;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.CheckList;
import com.survivalsos.goldentime.util.TextUtil;

import java.util.ArrayList;

/**
 * Created by kiho on 2016. 6. 20..
 */
public class CheckListImportRecyclerAdapter extends RecyclerView.Adapter {

    private CheckList item;
    private ArrayList<CheckList> items;
    private static Context context;
    private AdapterItemClickListener adapterItemClickListener;

    //체크리스트 내용
    public static class CheckListAllContentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private LinearLayout linearLayoutCheckListAllHeader;
        private NanumBarunGothicTextView nanumGothicTextViewCheckListHeader;

        private LinearLayout itemChecklistIncludingItem;
        private LinearLayout clickArea;
        private ImageView imgCheck;
        private TextView titleOfItem;
        private LinearLayout checklistAllTrashClickArea;
        private ImageView imgTrash;

        private AdapterItemClickListener itemClickListener;

        public CheckListAllContentViewHolder(View v) {
            super(v);

            linearLayoutCheckListAllHeader = (LinearLayout) v.findViewById(R.id.linearlayout_checklist_all_header);
            nanumGothicTextViewCheckListHeader = (NanumBarunGothicTextView) v.findViewById(R.id.tv_checklist_title_header);

            itemChecklistIncludingItem = (LinearLayout) v.findViewById(R.id.item_checklist_import_list_click_area);
//            clickArea = (LinearLayout) v.findViewById(R.id.item_checklist_all_checkbox_click_area);
//            imgCheck = (ImageView) v.findViewById(R.id.item_checklist_img_check);
            titleOfItem = (TextView) v.findViewById(R.id.tv_checklist_title);

            checklistAllTrashClickArea = (LinearLayout) v.findViewById(R.id.linearlayout_checklist_import_add);
            imgTrash = (ImageView) v.findViewById(R.id.iv_checklist_trash_icon);

//            itemChecklistIncludingItem.setOnClickListener(this);
//            clickArea.setOnClickListener(this);
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
    public CheckListImportRecyclerAdapter(Context context) {
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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_checklist_import_list, parent, false);
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
//        ((CheckListAllContentViewHolder) holder).clickArea.setVisibility(View.INVISIBLE);
        ((CheckListAllContentViewHolder) holder).itemChecklistIncludingItem.setVisibility(View.GONE);
        ((CheckListAllContentViewHolder) holder).titleOfItem.setText("");

        if (holder instanceof CheckListAllContentViewHolder) {
            if (item != null) {


                ((CheckListAllContentViewHolder) holder).titleOfItem.setText(item.title);//공통적인 부분
                ((CheckListAllContentViewHolder) holder).itemChecklistIncludingItem.setVisibility(View.VISIBLE);
                if (item.isHeader) {
                    ((CheckListAllContentViewHolder) holder).linearLayoutCheckListAllHeader.setVisibility(View.VISIBLE);
                    if (!TextUtil.isNull(item.categoryName))
                        ((CheckListAllContentViewHolder) holder).nanumGothicTextViewCheckListHeader.setText(item.categoryName);
                } else {
                    //Todo 체크 여부 디비에서 검사해서 표시해주기
                    ((CheckListAllContentViewHolder) holder).linearLayoutCheckListAllHeader.setVisibility(View.GONE);
                }

                //import 여부
                if (item.isInMyList == Definitions.CHECK_BOX_IMPORTED.IMPORTED) {
//                    ((CheckListAllContentViewHolder) holder).imgTrash.setVisibility(View.VISIBLE);
                    ((CheckListAllContentViewHolder) holder).imgTrash.setImageResource(R.drawable.check_list_import_check_all_01);

                } else {
//                    ((CheckListAllContentViewHolder) holder).imgTrash.setVisibility(View.INVISIBLE);
                    ((CheckListAllContentViewHolder) holder).imgTrash.setImageResource(R.drawable.check_list_import_check_all_00);

                }

//                //체크박스 체크 여부
//                if (item.isChecked == Definitions.CHECK_BOX_CHECKED.CHECKED)
//                    ((CheckListAllContentViewHolder) holder).imgCheck.setVisibility(View.VISIBLE);
//                else
//                    ((CheckListAllContentViewHolder) holder).imgCheck.setVisibility(View.INVISIBLE);
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
