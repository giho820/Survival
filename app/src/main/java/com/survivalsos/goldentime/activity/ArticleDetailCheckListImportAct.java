package com.survivalsos.goldentime.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.ParentAct;
import com.survivalsos.goldentime.R;
import com.survivalsos.goldentime.adapter.CheckListImportRecyclerAdapter;
import com.survivalsos.goldentime.database.DatabaseCRUD;
import com.survivalsos.goldentime.model.Article;
import com.survivalsos.goldentime.model.CheckList;
import com.survivalsos.goldentime.util.DebugUtil;

import java.util.ArrayList;

public class ArticleDetailCheckListImportAct extends ParentAct implements View.OnClickListener {

    Article currentArticle;
    LinearLayout linearLayoutCheckListBack;
    LinearLayout linearLayoutCheckListAll;

    private RecyclerView checkListRecyclerView;
    private ArrayList<CheckList> checkLists;
    private CheckListImportRecyclerAdapter categoryAllRecyclerAdapter;

    boolean isAll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail_check_list_import);

        linearLayoutCheckListBack = (LinearLayout) findViewById(R.id.linearlayout_check_list_import_back);
        linearLayoutCheckListAll = (LinearLayout) findViewById(R.id.linearlayout_checklist_import_all);

        linearLayoutCheckListBack.setOnClickListener(this);
        linearLayoutCheckListAll.setOnClickListener(this);

        currentArticle = (Article) getIntent().getSerializableExtra("article Infos");
        if (currentArticle == null) {
            DebugUtil.showDebug("ArticleDetailCheckListImportAct.java, onCreate(),,, -> currentArticle is null");
            return;
        }
        DebugUtil.showDebug("현재 넘어온 아티클의 정보입니다 " + currentArticle.toString());

        //Todo 1. 디비 복사할 것(어플 실행 후 최초 1회만 이루어져야함)
        //insert 쿼리를 리턴하는 함수를 만들고나서 동적으로 생성한 checkedlist db에 insert 쿼리를 날린다
        if (checkLists == null) {
            DebugUtil.showDebug("User CheckedList Table 생성중 ");
            String insertQuery = DatabaseCRUD.getInsertQueryFromCheckListTable();
            DebugUtil.showDebug("insertQuery :: " + insertQuery);
            DatabaseCRUD.execRawQuery(insertQuery);
            checkLists = DatabaseCRUD.getCheckListOfIndivisualArticle(currentArticle.articleId); //Todo 디비 refresh하는 부분
        }

        DebugUtil.showDebug("checkLists :: " + checkLists.size());
// else if (checkLists != null) {
//            categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
//            categoryAllRecyclerAdapter.notifyDataSetChanged();
//
//
//            checkListRecyclerView = (RecyclerView) findViewById(R.id.checklist_import_recycler);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//            linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
//            checkListRecyclerView.setLayoutManager(linearLayoutManager);
//            categoryAllRecyclerAdapter = new CheckListImportRecyclerAdapter(this);
//            categoryAllRecyclerAdapter.setAdapterItemClickListener(new AdapterItemClickListener() {
//                @Override
//                public void onAdapterItemClick(View view, int position) {
//
//                    DebugUtil.showDebug("체크리스트랑 지우기가 눌려야해오 :: " + checkLists.get(position).title);
//                    switch (view.getId()) {
//
//                        case R.id.linearlayout_checklist_import_add:
//                            if (checkLists.get(position).isInMyList == Definitions.CHECK_BOX_IMPORTED.IMPORTED) {
//                                checkLists.get(position).isInMyList = Definitions.CHECK_BOX_IMPORTED.UNIMPORTED;
//
//                                String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
//                                        DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.UNIMPORTED
//                                        + ", " + DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.UNCHECKED +
//                                        " where " + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + "=" + checkLists.get(position).no;
//                                DebugUtil.showDebug("updateQuery :: " + updateQuery);
//                                DatabaseCRUD.execRawQuery(updateQuery);
//                            } else {
//                                checkLists.get(position).isInMyList = Definitions.CHECK_BOX_IMPORTED.IMPORTED;
//
//                                String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
//                                        DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.IMPORTED
//                                        + " where " + DatabaseConstantUtil.COLUMN_NO_USER_CHECKED_LIST + "=" + checkLists.get(position).no;
//                                DebugUtil.showDebug("updateQuery :: " + updateQuery);
//                                DatabaseCRUD.execRawQuery(updateQuery);
//                            }
//
//                            break;
//                    }
//                    checkLists = DatabaseCRUD.getCheckListOfIndivisualArticle(currentArticle.articleId); //Todo 디비 refresh하는 부분
//                    categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
//                    categoryAllRecyclerAdapter.notifyDataSetChanged();
//                }
//            });
//
//            checkListRecyclerView.setAdapter(categoryAllRecyclerAdapter);
//            checkLists = DatabaseCRUD.getCheckListOfIndivisualArticle(currentArticle.articleId); //Todo 디비 refresh하는 부분
//
//            if (checkLists != null) {
//                categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
//                categoryAllRecyclerAdapter.notifyDataSetChanged();
//                DebugUtil.showDebug("categoryAllRecyclerAdapter.setAdapterArrayList() 진행 함 :: " + categoryAllRecyclerAdapter.getItemCount());
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearlayout_check_list_import_back:
                onBackPressed();
                break;

            case R.id.linearlayout_checklist_import_all:
                //Todo 뷰에 있는 것들 토글할 것
//                if (DatabaseCRUD.getCheckListOfIndivisualArticle(currentArticle.articleId).size() == DatabaseCRUD.getCheckListOfOriginalDb(currentArticle.articleId).size()) {
//                    isAll = false;
//                } else if (DatabaseCRUD.getCheckListOfIndivisualArticle(currentArticle.articleId).size() == 0)
//                    isAll = true;
//                else
//                    isAll = !isAll;
//
//                DebugUtil.showDebug("isAll " + isAll);
//
//                if (isAll) {
//                    String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
//                            DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.IMPORTED + ", "
//                            + DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.UNCHECKED + " where "
////                            + DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.UNCHECKED + " and "
//                            + DatabaseConstantUtil.COLUMN_ARTICLE_ID_CHECKED_LIST + "==" + currentArticle.articleId;
//                    DebugUtil.showDebug("updateQuery :: " + updateQuery);
//                    DatabaseCRUD.execRawQuery(updateQuery);
//                } else {
//                    String updateQuery = "update " + DatabaseConstantUtil.TABLE_USER_CHECKED_LIST + " set " +
//                            DatabaseConstantUtil.COLUMN_IS_IN_MY_LIST_CHECKED_LIST + " = " + Definitions.CHECK_BOX_IMPORTED.UNIMPORTED + ", "
//                            + DatabaseConstantUtil.COLUMN_IS_CHECKED + " = " + Definitions.CHECK_BOX_CHECKED.UNCHECKED + " where "
//                            + DatabaseConstantUtil.COLUMN_ARTICLE_ID_CHECKED_LIST + "==" + currentArticle.articleId;
//                    DebugUtil.showDebug("updateQuery :: " + updateQuery);
//                    DatabaseCRUD.execRawQuery(updateQuery);
//                }
//                checkLists = DatabaseCRUD.getCheckListOfIndivisualArticle(currentArticle.articleId); //Todo 디비 refresh하는 부분
//                if (checkLists != null) {
//                    categoryAllRecyclerAdapter.setAdapterArrayList(checkLists);
//                    categoryAllRecyclerAdapter.notifyDataSetChanged();
//                }

                break;
        }
    }
}
