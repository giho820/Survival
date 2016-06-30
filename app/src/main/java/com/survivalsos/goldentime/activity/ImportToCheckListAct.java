package com.survivalsos.goldentime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ParentAct;
import com.survivalsos.goldentime.Definitions;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.adapter.CheckListImportRecyclerAdapter;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.database.util.DatabaseConstantUtil;
import com.survivalsos.goldentime.listener.AdapterItemClickListener;
import com.survivalsos.goldentime.model.CheckList;
import com.survivalsos.goldentime.util.DebugUtil;

import java.util.ArrayList;

public class ImportToCheckListAct extends ParentAct implements View.OnClickListener {
    LinearLayout linearLayoutCheckListBack;
    LinearLayout linearLayoutCheckListAll;

    private RecyclerView checkListRecyclerView;
    private ArrayList<CheckList> checkLists;
    private CheckListImportRecyclerAdapter categoryAllRecyclerAdapter;

    boolean isAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_to_check_list);

        linearLayoutCheckListBack = (LinearLayout) findViewById(R.id.linearlayout_check_list_import_back);
        linearLayoutCheckListAll = (LinearLayout) findViewById(R.id.linearlayout_checklist_import_all);

        linearLayoutCheckListBack.setOnClickListener(this);
        linearLayoutCheckListAll.setOnClickListener(this);

        checkListRecyclerView = (RecyclerView) findViewById(R.id.checklist_import_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        checkListRecyclerView.setLayoutManager(linearLayoutManager);
        categoryAllRecyclerAdapter = new CheckListImportRecyclerAdapter(this);
        categoryAllRecyclerAdapter.setAdapterItemClickListener(new AdapterItemClickListener() {
            @Override
            public void onAdapterItemClick(View view, int position) {

                DebugUtil.showDebug("체크리스트랑 지우기가 눌려야해오 :: " + checkLists.get(position).title);
                switch (view.getId()) {
//                    case R.id.item_checklist_all_checkbox_click_area:
//
//                        if(checkLists != null) {
//                            if(checkLists.get(position).isChecked == Definitions.CHECK_BOX_CHECKED.CHECKED){
//                                checkLists.get(position).isChecked = Definitions.CHECK_BOX_CHECKED.UNCHECKED;
//
//                                String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
//                                        DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " +Definitions.CHECK_BOX_CHECKED.UNCHECKED +
//                                        " where " + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + "=" + checkLists.get(position).no;
//                                DebugUtil.showDebug("updateQuery :: " + updateQuery);
//                                DatabaseCRUD.execRawQuery(updateQuery);
//                            } else {
//                                checkLists.get(position).isChecked = Definitions.CHECK_BOX_CHECKED.CHECKED;
//
//                                String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
//                                        DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " +Definitions.CHECK_BOX_CHECKED.CHECKED +
//                                        " where " + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + "=" + checkLists.get(position).no;
//                                DebugUtil.showDebug("updateQuery :: " + updateQuery);
//                                DatabaseCRUD.execRawQuery(updateQuery);
//                            }
//                        }
//                        break;

                    case R.id.linearlayout_checklist_import_add:
                        if (checkLists.get(position).isInMyList == Definitions.CHECK_BOX_IMPORTED.IMPORTED) {
                            checkLists.get(position).isInMyList = Definitions.CHECK_BOX_IMPORTED.UNIMPORTED;

                            String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
                                    DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.UNIMPORTED
                                    + ", " + DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.UNCHECKED +
                                    " where " + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + "=" + checkLists.get(position).no;
                            DebugUtil.showDebug("updateQuery :: " + updateQuery);
                            DatabaseCRUD.execRawQuery(updateQuery);
                        } else {
                            checkLists.get(position).isInMyList = Definitions.CHECK_BOX_IMPORTED.IMPORTED;

                            String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
                                    DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.IMPORTED
                                    + ", " + DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.UNCHECKED
                                    + " where " + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + "=" + checkLists.get(position).no;
                            DebugUtil.showDebug("updateQuery :: " + updateQuery);
                            DatabaseCRUD.execRawQuery(updateQuery);
                        }

                        break;
                }
                checkLists = DatabaseCRUD.getImportCheckedListFromDb(); //Todo 디비 refresh하는 부분
                categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
                categoryAllRecyclerAdapter.notifyDataSetChanged();
            }
        });

        checkListRecyclerView.setAdapter(categoryAllRecyclerAdapter);
        checkLists = DatabaseCRUD.getImportCheckedListFromDb(); //Todo 디비 refresh하는 부분

        if (checkLists != null) {
            categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
            categoryAllRecyclerAdapter.notifyDataSetChanged();
            DebugUtil.showDebug("categoryAllRecyclerAdapter.setAdapterArrayList() 진행 함 :: " + categoryAllRecyclerAdapter.getItemCount());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout_check_list_import_back:
                Intent intent = new Intent(this, CheckList.class);
                intent.putExtra("sizeOfCheckList", checkLists.size());
                this.setResult(RESULT_OK, intent);
                onBackPressed();
                break;

            case R.id.linearlayout_checklist_import_all:
                //Todo 뷰에 있는 것들 토글할 것
//                if (DatabaseCRUD.getImportCheckedListFromDb().size() == DatabaseCRUD.getUserCheckedListFromDb().size())
//                    isAll = false;
//                else if (DatabaseCRUD.getUserCheckedListFromDb().size() == 0)
//                    isAll = true;
//                else
                    isAll = !isAll;

                if (isAll) {
                    String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
                            DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.IMPORTED +", "
                            + DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.UNCHECKED
                            + " where "+ DatabaseConstantUtil.COLUMN_IS_CHECKED +" == " + Definitions.CHECK_BOX_CHECKED.UNCHECKED;
                    DebugUtil.showDebug("updateQuery :: " + updateQuery);
                    DatabaseCRUD.execRawQuery(updateQuery);
                } else {
                    String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
                            DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.UNIMPORTED +", "
                            + DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.UNCHECKED;
                    DebugUtil.showDebug("updateQuery :: " + updateQuery);
                    DatabaseCRUD.execRawQuery(updateQuery);
                }
                checkLists = DatabaseCRUD.getImportCheckedListFromDb(); //Todo 디비 refresh하는 부분
                categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
                categoryAllRecyclerAdapter.notifyDataSetChanged();

                break;
        }
    }
}
